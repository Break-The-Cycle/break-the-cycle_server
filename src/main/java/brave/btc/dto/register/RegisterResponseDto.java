package brave.btc.dto.register;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponseDto {

    private boolean idError;
    private boolean pwdError;
    private boolean phoneError;
    private boolean nameError;
    private boolean idDupError;
    private boolean pwdMatchError;
    public RegisterResponseDto() {
    }
}
