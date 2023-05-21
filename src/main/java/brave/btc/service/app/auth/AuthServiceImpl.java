package brave.btc.service.app.auth;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brave.btc.constant.enums.ManageDivision;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.domain.bo.user.ManagePerson;
import brave.btc.domain.bo.user.OfficialInstitution;
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.common.auth.register.RegisterDto;
import brave.btc.exception.auth.AuthenticationInvalidException;
import brave.btc.exception.auth.UserPrincipalNotFoundException;
import brave.btc.exception.domain.EntityNotFoundException;
import brave.btc.repository.app.UsePersonRepository;
import brave.btc.repository.bo.ManagePersonRepository;
import brave.btc.repository.bo.OfficialInstitutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UsePersonRepository usePersonRepository;
    private final ManagePersonRepository managePersonRepository;
    private final OfficialInstitutionRepository officialInstitutionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UsePerson checkIsCredentialValid(String loginId, String rawPassword) {
        UsePerson usePerson = usePersonRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserPrincipalNotFoundException("해당하는 유저를 찾을 수 없습니다."));

        //비밀번호 확인 로직

        String orgPassword = usePerson.getPassword();
        boolean isMatches = bCryptPasswordEncoder.matches(rawPassword, orgPassword);
        log.debug("[checkIsPasswordEqual] isMatches: {}", isMatches);

        if (isMatches) {
            log.info("[checkIsPasswordEqual] 비밀번호 일치");
            return usePerson;
        }
        log.error("[checkIsPasswordEqual] 비밀번호 불일치 에러");
        throw new AuthenticationInvalidException("비밀번호가 일치하지 않습니다.");
    }

    @Override
    @Transactional(readOnly = true)
    public CommonResponseDto<Object> loginIdIdDuplicateCheck(String loginId) {

        boolean notExist = checkLoginIdNotExist(loginId);
        if (notExist) {
            return CommonResponseDto.builder()
                .message("사용 가능한 아이디입니다.")
                .statusCode(HttpStatus.OK.value())
                .build();

        }else {
            return CommonResponseDto.builder()
                .message("이미 사용중인 아이디입니다.")
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
        }
    }

    private boolean checkLoginIdNotExist(String loginId) {
        Optional<UsePerson> usePersonOptional = usePersonRepository.findByLoginId(loginId);
        Optional<ManagePerson> managePersonOptional = managePersonRepository.findByLoginId(loginId);

        return usePersonOptional.isEmpty() && managePersonOptional.isEmpty();
    }

    @Override
    public CommonResponseDto<Object> registerUsePerson(RegisterDto.UsePersonCreate request) {

        log.debug("[register] request: {}", request);
        //비밀번호 매칭 확인
        String password = request.getPassword();
        String password2 = request.getPassword2();
        checkIsPasswordEqual(password, password2);

        String loginId = request.getLoginId();
        if (!checkLoginIdNotExist(loginId)) {
            throw new IllegalStateException("이미 사용 중인 아이디 입니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(password);
        UsePerson newUsePerson = request.toUsePersonEntity(encodedPassword);
        usePersonRepository.save(newUsePerson);
        log.info("[register] 사용 개인 회원 가입 완료");
        return CommonResponseDto.builder()
            .message("사용 개인 회원 가입이 완료되었습니다.")
            .statusCode(HttpStatus.OK.value())
            .build();
    }

    private static void checkIsPasswordEqual(String password, String password2) {
        if (!password.equals(password2)) {
            log.error("[register] 사용 개인 회원 가입 실패 : 비밀번호, 확인 비밀번호 불일치");
            throw new AuthenticationInvalidException("비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public CommonResponseDto<Object> registerManagePerson(RegisterDto.ManagePersonCreate request) {
        log.debug("[register] request: {}", request);
        //비밀번호 매칭 확인
        String password = request.getPassword();
        String password2 = request.getPassword2();
        checkIsPasswordEqual(password, password2);

        String loginId = request.getLoginId();
        if (!checkLoginIdNotExist(loginId)) {
            throw new IllegalStateException("이미 사용 중인 아이디 입니다.");
        }

        Integer officialInstitutionId = request.getOfficialInstitutionId();
        OfficialInstitution officialInstitution = officialInstitutionRepository.findById(officialInstitutionId)
            .orElseThrow(() -> new EntityNotFoundException(OfficialInstitution.class.getName(), officialInstitutionId));

        ManageDivision manageDivision = request.getManageDivision();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        ManagePerson newManagePerson;

        if (manageDivision == ManageDivision.COUNSELOR) {
            newManagePerson = request.toCounselingPersonEntity(encodedPassword, officialInstitution);
        } else if (manageDivision == ManageDivision.POLICE_OFFICER) {
            newManagePerson = request.toPolicePersonEntity(encodedPassword, officialInstitution);
        }else{
            throw new IllegalStateException("비정상 상태");
        }
        managePersonRepository.save(newManagePerson);
        log.info("[register] 관리 개인 회원 가입 완료");
        return CommonResponseDto.builder()
            .message("관리 개인  회원 가입이 완료되었습니다.")
            .statusCode(HttpStatus.OK.value())
            .build();
    }

    @Override
    public CommonResponseDto<Object> registerBackOffIceManagePerson(RegisterDto.BackOfficeManagePersonCreate request) {
        log.debug("[register] request: {}", request);
        //비밀번호 매칭 확인
        String password = request.getPassword();
        String password2 = request.getPassword2();
        checkIsPasswordEqual(password, password2);

        String loginId = request.getLoginId();
        if (!checkLoginIdNotExist(loginId)) {
            throw new IllegalStateException("이미 사용 중인 아이디 입니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(password);
        ManagePerson newManagePerson = request.toBackOfficeManagePersonEntity(encodedPassword);
        managePersonRepository.save(newManagePerson);

        log.info("[register] 백오피스 관리 개인 회원 가입 완료");
        return CommonResponseDto.builder()
            .message("백오피스 관리 개인 회원 가입이 완료되었습니다.")
            .statusCode(HttpStatus.OK.value())
            .build();

    }
}
