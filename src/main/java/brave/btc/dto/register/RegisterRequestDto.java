package brave.btc.dto.register;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(title = "회원가입 요청", description = "회원가입 폼을 작성하여 요청한다.")
public class RegisterRequestDto {

    @Schema(title = "이름", example = "홍길동")
    @NotBlank(message = "이름 오류")
    private String name;

    @Schema(title = "전화번호", example = "01012345678")
    @NotBlank(message = "전화번호 오류")
    private String phoneNumber;

//    private Address address;

    @Schema(title = "로그인 ID", example = "kang123")
    @NotBlank(message = "아이디 오류")
    @Pattern(regexp = "[a-zA-Z1-9]{7,}",
            message = "아이디 오류")
    private String loginId;

    @Schema(title = "아이디 중복확인 여부", example = "false")
    private boolean idDup;

    @Schema(title = "비밀번호", example = "kang123!")
    @NotBlank(message = "비밀번호 오류")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,12}",
            message = "비밀번호 오류")
    private String password;

    @Schema(title = "비밀번호 확인", example = "kang123!")
    private String password2;

    public RegisterRequestDto() {
    }
}
