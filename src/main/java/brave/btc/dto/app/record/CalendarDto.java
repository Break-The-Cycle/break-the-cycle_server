package brave.btc.dto.app.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@Schema(title = "달력", description = "달력 하루에 대한 정보")
public class CalendarDto {
	
}
