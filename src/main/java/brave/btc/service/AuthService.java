package brave.btc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brave.btc.domain.persistence.user.UsePerson;

import brave.btc.domain.temporary.SmsCertification;
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.auth.login.LoginRequestDto;
import brave.btc.dto.auth.register.RegisterRequestDto;
import brave.btc.exception.auth.AuthenticationInvalidException;
import brave.btc.exception.auth.SmsCertificationNumberExpiredException;
import brave.btc.exception.auth.SmsCertificationNumberNotSameException;
import brave.btc.exception.auth.UserPrincipalNotFoundException;

import brave.btc.repository.persistence.UsePersonRepository;

import brave.btc.repository.temporary.SmsCertificationRepository;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;


import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.apache.commons.lang3.RandomStringUtils;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsePersonRepository usePersonRepository;

    private final SmsCertificationRepository smsCertificationRepository;

    public CommonResponseDto<Object> login(LoginRequestDto loginRequestDto) {

        log.info("[login] 로그인 시도");
        String loginId = loginRequestDto.getLoginId();
        String rawPassword = loginRequestDto.getPassword();

        checkIsPasswordEqual(loginId, rawPassword);

        log.info("[login] 비밀번호 일치");
        return CommonResponseDto.builder()
            .message("로그인이 완료되었습니다.")
            .build();
    }

    public UsePerson checkIsPasswordEqual(String loginId, String rawPassword) {
        UsePerson usePerson = usePersonRepository.findByLoginId(loginId)
            .orElseThrow(() -> new UserPrincipalNotFoundException("해당하는 유저를 찾을 수 없습니다."));

        //비밀번호 확인 로직
        //보통 DB에는 해시된 값이 들어가 있기 때문에 rawPassword를 해싱한 후에 비교해봄
        String encPassword = usePerson.getPassword();

        if (rawPassword.equals(encPassword)) {
            log.info("[login] 비밀번호 일치");
            return usePerson;
        }
        log.error("[login] 비밀번호 불일치 에러");
        throw new AuthenticationInvalidException("비밀번호가 일치하지 않습니다.");
    }

    @Transactional(readOnly = true)
    public CommonResponseDto<Object> loginIdIdDuplicateCheck(String loginId) {

        UsePerson user = usePersonRepository.findByLoginId(loginId)
            .orElse(null);

        if (user == null) {
            return CommonResponseDto.builder()
                    .message("사용 가능한 아이디입니다.")
                    .build();
        }
        return CommonResponseDto.builder()
                .message("이미 사용중인 아이디입니다.")
                .build();
    }


    public CommonResponseDto<Object> register(RegisterRequestDto request) {

        log.debug("[register] request: {}", request);
        //비밀번호 매칭 확인
        String password = request.getPassword();
        String password2 = request.getPassword2();
        if (password.equals(password2)) {
            //TODO : MapStruct 도입하기 ... (나중에 필드 많아지면 훨씬 편함) DTO <-> Entity Mapper

            UsePerson newUsePerson = UsePerson.builder()
                .loginId(request.getLoginId())
                .password(password)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();
            usePersonRepository.save(newUsePerson);

            log.info("[register] 회원 가입 완료");
            return CommonResponseDto.builder()
                    .message("회원 가입이 완료되었습니다.")
                    .build();
        }
        log.error("[register] 회원 가입 실패");
        throw new AuthenticationInvalidException("비밀번호와 확인 비밀번호가 일치하지 않습니다.");
    }


    public CommonResponseDto<Object> sendAuthNumber(String apiKey, String secretKey, String smsDomain, String phoneNumber) {

        log.info("[register] 인증번호 요청");
        String authNumber = RandomStringUtils.randomNumeric(6);

        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, smsDomain);

        Message message = new Message();
        message.setFrom("01099236825");
        message.setTo(phoneNumber);
        message.setText("[로즈 데이즈] 인증번호는 " + authNumber + "입니다.");

        //인증 번호 저장하고 check에서 비교할 때 사용

        try {
            messageService.send(message);
            saveCertificationNumber(phoneNumber, authNumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return CommonResponseDto.builder()
                .message("인증번호 전송이 완료되었습니다.")
                .build();
    }

    public void saveCertificationNumber(String phoneNumber, String authNumber) {

        log.info("[register] 인증번호 저장");
        SmsCertification smsCertification = smsCertificationRepository.findByPhoneNumber(phoneNumber)
                .orElse(null);
        if (smsCertification != null) {
            smsCertificationRepository.updateCertificationNum(authNumber, getNowTimeStamp(), phoneNumber);
        } else {
            SmsCertification requestDto = SmsCertification.builder()
                    .phoneNumber(phoneNumber)
                    .certificationNumber(authNumber)
                    .build();
            smsCertificationRepository.save(requestDto);
        }

    }

    public CommonResponseDto<Object> checkAuthNumber(String authNumber, String phoneNumber) {

        log.info("[register] 인증번호 확인 요청");
        SmsCertification smsCertification = smsCertificationRepository.findByPhoneNumber(phoneNumber)
                .orElse(null);
        if (smsCertification != null) {
            checkCertificationTime(smsCertification.getCreated(), 3);
            if (smsCertification.getCertificationNumber().equals(authNumber)) {
                System.out.println("smsCertification.getCertificationNumber() = " + smsCertification.getCertificationNumber());
                System.out.println("authNumber = " + authNumber);
                return CommonResponseDto.builder()
                        .message("인증번호 인증이 완료되었습니다.")
                        .build();
            }
        }

        throw new SmsCertificationNumberNotSameException("인증번호가 일치하지 않습니다.");

    }

    public Timestamp getNowTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public void checkCertificationTime(Timestamp timestamp, int validationTime) {

        LocalDateTime localDateTime = timestamp.toLocalDateTime()
                .plusMinutes(validationTime);
        LocalDateTime now = LocalDateTime.now();
        if (localDateTime.isBefore(now)) {
            throw new SmsCertificationNumberExpiredException("인증번호가 만료되었습니다.");
        }
    }
}
