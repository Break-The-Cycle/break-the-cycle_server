package brave.btc.dto.app.menstruation;

import java.time.LocalDate;
import java.time.Period;

import brave.btc.constant.enums.MenstruationDivision;
import brave.btc.domain.app.record.Menstruation;
import brave.btc.domain.app.user.UsePerson;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@Schema(name = "생리 기록", description = "사용자의 생리 기록에 대한 정보")
public class MenstruationDto {


	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "생리 기록 응답 dto",description = "Menstruation response dto")
	public static class Response {

		@Schema(description = "pk")
		private Integer id;

		@Schema(description = "생리를 시작한 날짜")
		private LocalDate startDate;

		@Schema(description = "생리가 끝난 날짜")
		private LocalDate endDate;

		@Schema(description = "날짜 구분 (실제 과거 데이터 /예상 생리일 / 예상 배란일 / 가임기)")
		private MenstruationDivision division;

	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "생리 기록 등록 dto",description = "Menstruation create request dto")
	public static class Create {
		@Schema(description = "생리를 시작한 날짜")
		@NotBlank(message = "생리 시작일은 필수 값입니다.")
		private LocalDate startDate;

		@Schema(description = "생리가 끝난 날짜")
		@NotBlank(message = "생리 종료일은 필수 값입니다.")
		private LocalDate endDate;

		public Menstruation toEntity(UsePerson usePerson) {
			Period period = Period.between(startDate, endDate);
			return Menstruation.builder()
				.startDate(startDate)
				.endDate(endDate)
				.period(period)
				.usePerson(usePerson)
				.build();
		}


	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "(온보딩) 생리 초기 정보 등록 ", description = "첫 로그인 후 온보딩 관련 데이터르 등록하는 dto입니다.")
	public static class OnBoardCreate {

		@Schema(description = "가장 최근에생리를 시작한 날짜")
		private LocalDate startDate;

		@Schema(description = "가장 최근에 생리가 끝난 날짜")
		private LocalDate endDate;

		@Positive
		@Schema(description = "유저의 생리 주기", example = "28")
		private Integer period;

		public MenstruationDto.Create toCreateDto() {
			return Create.builder()
				.startDate(this.startDate)
				.endDate(this.endDate)
				.build();
		}
	}
}
