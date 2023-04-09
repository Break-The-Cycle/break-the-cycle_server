package brave.btc.service;

import brave.btc.domain.User;
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.auth.login.LoginRequestDto;
import brave.btc.dto.auth.register.RegisterRequestDto;
import brave.btc.exception.auth.AuthenticationInvalidException;
import brave.btc.exception.auth.UserPrincipalNotFoundException;
import brave.btc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;


    public CommonResponseDto<Object> login(LoginRequestDto loginRequestDto)  {

        log.info("[login] 로그인 시도");
        String loginId = loginRequestDto.getLoginId();
        String rawPassword = loginRequestDto.getPassword();
        User user = userRepository.findByLoginId(loginId)
            .orElseThrow(() -> new UserPrincipalNotFoundException("해당하는 유저를 찾을 수 없습니다."));

        //비밀번호 확인 로직
        //보통 DB에는 해시된 값이 들어가 있기 때문에 rawPassword를 해싱한 후에 비교해봄
        String encPassword = user.getPassword();

        if (rawPassword.equals(encPassword)) {
            log.info("[login] 비밀번호 일치");
            return CommonResponseDto.builder()
                .message("로그인이 완료되었습니다.")
                .build();
        }
        log.error("[login] 비밀번호 불일치 에러");
        throw new AuthenticationInvalidException("비밀번호가 일치하지 않습니다.");
    }

    @Transactional(readOnly = true)
    public CommonResponseDto<Object> loginIdIdDuplicateCheck(String loginId) {
        User user = userRepository.findByLoginId(loginId)
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
            User newUser = User.builder()
                .loginId(request.getLoginId())
                .password(password)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();
            userRepository.save(newUser);
            log.info("[register] 회원 가입 완료");
            return CommonResponseDto.builder()
                .message("회원 가입이 완료되었습니다.")
                .build();
        }
        log.error("[register] 회원 가입 실패");
        throw new AuthenticationInvalidException("비밀번호와 확인 비밀번호가 일치하지 않습니다.");
    }
}
