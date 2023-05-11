package brave.btc.dto.app.record;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@Schema(title = "달력", description = "달력 하루에 대한 정보")
public class CalendarDto {

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "달력 하루 response dto",
		description = "달력에서 특정 하루에 포함되는 기록들에 대한 response dto. "
			+ "ex) 그 예로 만약 5/5일이라면 5/5에 대한 피해 기록들이 이 객체 안에 전부 list 형태로 있음")
	public static class DateResponse {

		@Schema(title = "달력에서의 날짜, 시간")
		private LocalDate targetDate;

	}
}
