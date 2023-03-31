package brave.btc.dto.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequestDto {

    @NotBlank(message = "이름 오류")
    private String name;

    @NotBlank(message = "전화번호 오류")
    private String phoneNumber;

//    private Address address;

    @NotBlank(message = "아이디 오류")
    @Pattern(regexp = "[a-zA-Z1-9]{7,}",
            message = "아이디 오류")
    private String loginId;

    private boolean idDup;

    @NotBlank(message = "비밀번호 오류")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,12}",
            message = "비밀번호 오류")
    private String password;

    private String password2;

    public RegisterRequestDto() {
    }
}
