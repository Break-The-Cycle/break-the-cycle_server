package brave.btc.dto.register;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponseDto {

    private boolean id_error;
    private boolean pwd_error;
    private boolean phone_error;
    private boolean name_error;
    private boolean id_dup_error;
    private boolean success;

    public RegisterResponseDto() {
    }
}
