package brave.btc.dto.dupCheck;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DupCheckResponseDto {

    private boolean exist;

    public DupCheckResponseDto() {
    }
}
