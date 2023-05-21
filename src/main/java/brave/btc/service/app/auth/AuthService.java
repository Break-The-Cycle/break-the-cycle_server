package brave.btc.service.app.auth;

import brave.btc.domain.app.user.UsePerson;
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.common.auth.register.RegisterDto;

public interface AuthService {

    /**
     * 아이디 비밀번호를 확인한다.
     * @param loginId 확인할 아이디
     * @param rawPassword 확인할 비밀번호
     * @return 로그인 성공한 유저 객체
     */
    UsePerson checkIsCredentialValid(String loginId, String rawPassword);

    /**
     * 아이디 중복을 확인한다
     * @param loginId 확인할 아이디
     * @return 응답 메세지
     */
    CommonResponseDto<Object> loginIdIdDuplicateCheck(String loginId);

    /**
     * USE_PERSON 회원가입을 한다
     * @param request 회원가입에 필요한 데이터를 담은 dto
     * @return 응답 메세지
     */
    CommonResponseDto<Object> registerUsePerson(RegisterDto.UsePersonCreate request);

    /**
     * MANAGE_PERSON 회원가입을 한다
     * @param request 회원가입에 필요한 데이터를 담은 dto
     * @return 응답 메세지
     */
    CommonResponseDto<Object> registerManagePerson(RegisterDto.ManagePersonCreate request);
}

