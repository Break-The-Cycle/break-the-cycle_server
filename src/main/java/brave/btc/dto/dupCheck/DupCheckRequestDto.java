package brave.btc.dto.dupCheck;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DupCheckRequestDto {

    private String id;

    public DupCheckRequestDto() {
    }
}
