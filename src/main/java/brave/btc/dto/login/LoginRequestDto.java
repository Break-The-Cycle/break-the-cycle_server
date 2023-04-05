package brave.btc.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "로그인 ID/PW", description = "로그인 ID와 PASSWORD를 입력한다.")
public class LoginRequestDto {

    @Schema(title = "로그인 ID", example = "kang123")
    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-z]+[a-zA-Z1-9]{6,20}$",
        message = "아이디는 영문 소문자로 시작하고 숫자를 포함하여 7~20자로 구성되어야 합니다.")
    private String loginId;

    @Schema(title = "로그인 PASSWORD", example = "kang123!")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;


}
