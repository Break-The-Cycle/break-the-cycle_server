package brave.btc.dto.register;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(title = "회원가입 오류 응답", description = "회원가입 오류 발생 시 응답")
public class RegisterResponseDto {

    @Schema(title = "공백X, 영어, 숫자, 7자 이상 체크")
    private boolean idError;

    @Schema(title = "공백X, 8자 ~ 12자 숫자,영어,특수문자 무조건 한번씩은 포함")
    private boolean pwdError;

    @Schema(title = "공백X")
    private boolean phoneError;

    @Schema(title = "공백X")
    private boolean nameError;

    @Schema(title = "아이디 중복 확인 여부")
    private boolean idDupError;

    @Schema(title = "비밀번호 매칭 확인")
    private boolean pwdMatchError;
    public RegisterResponseDto() {
    }
}
