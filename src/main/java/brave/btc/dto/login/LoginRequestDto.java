package brave.btc.dto.login;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String id;
    private String password;

    public LoginRequestDto() {
    }
}
