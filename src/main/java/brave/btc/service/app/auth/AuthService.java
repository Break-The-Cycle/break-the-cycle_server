package brave.btc.service.app.auth;

import brave.btc.constant.enums.RawPasswordDivision;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.dto.common.auth.register.RegisterDto;

public interface AuthService {

    /**
     * 아이디 비밀번호를 확인한다.
     * @param loginId 확인할 아이디
     * @param rawPassword 확인할 비밀번호
     * @param rawPasswordDivision 확인할 비밀번호의 암호화 단계
     * @return 로그인 성공한 유저 객체
     */
    UsePerson checkIsCredentialValid(String loginId, String rawPassword, RawPasswordDivision rawPasswordDivision);



    /**
     * 아이디 중복을 확인한다
     * @param loginId 확인할 아이디
     * @return 응답 메세지
     */
    String loginIdIdDuplicateCheck(String loginId);

    /**
     * USE_PERSON 회원가입을 한다
     * @param request 회원가입에 필요한 데이터를 담은 dto
     * @return 응답 메세지, 유저 pk
     */
    RegisterDto.Response registerUsePerson(RegisterDto.UsePersonCreate request);

    /**
     * MANAGE_PERSON 회원가입을 한다
     * @param request 회원가입에 필요한 데이터를 담은 dto
     * @return 응답 메세지, 유저 pk
     */
    RegisterDto.Response registerManagePerson(RegisterDto.ManagePersonCreate request);

    /**
     * BACK OFFICE MANAGE_PERSON 회원가입을 한다
     * @param request 회원가입에 필요한 데이터를 담은 dto
     * @return 응답 메세지, 유저 pk
     */
    RegisterDto.Response registerBackOffIceManagePerson(RegisterDto.BackOfficeManagePersonCreate request);
}

