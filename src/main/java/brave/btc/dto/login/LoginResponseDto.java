package brave.btc.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private boolean correct;

    public LoginResponseDto() {
    }
}
