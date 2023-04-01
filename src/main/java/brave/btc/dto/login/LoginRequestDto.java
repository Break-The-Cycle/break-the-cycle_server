package brave.btc.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(title = "로그인 ID/PW", description = "로그인 ID와 PASSWORD를 입력한다.")
public class LoginRequestDto {

    @Schema(title = "입력한 ID", example = "kang123")
    private String id;

    @Schema(title = "입력한 PASSWORD", example = "kang123!")
    private String password;

    public LoginRequestDto() {
    }
}
