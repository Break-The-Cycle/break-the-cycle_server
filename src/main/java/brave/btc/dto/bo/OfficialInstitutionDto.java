package brave.btc.dto.bo;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import brave.btc.constant.enums.OfficialInstitutionDivision;
import brave.btc.domain.bo.user.OfficialInstitution;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@Schema(title = "공식 기관", description = "공식 기관 관련 DTO ")
public class OfficialInstitutionDto {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Schema(title = "공식 기관 응답", description = "공식 기관 응답하는 dto")
	public static class Response {

		@Schema(description="공식 기관 이름")
		private Integer id;

		@Schema(description="공식 기관 이름")
		private String name;

		@Schema(description = "공식 기관 전화 번호")
		private String phoneNumber;

		@Schema(description = "공식 기관 코드")
		private OfficialInstitutionDivision code;

		@Schema(description = "기관 운영 시작 시간" ,example = "09:00:00", pattern = "HH:mm:ss")
		private LocalTime startTime;

		@Schema(description = "기관 운영 종료 시간",example = "19:00:00", pattern = "HH:mm:ss")
		private LocalTime endTime;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Schema(title = "공식 기관 생성 요청", description = "공식 기관 생성을 요청하는 dto")
	public static class Create {

		@Schema(description = "공식 기관 이름", example = "일산 동부 경찰서")
		private String name;

		@Schema(description = "공식 기관 전화 번호", example = "031-123-1234")
		private String phoneNumber;

		@Schema(description = "공식 기관 코드", example = "NATIONAL_POLICE_STATION")
		private OfficialInstitutionDivision code;

		@Schema(description = "기관 운영 시작 시간" ,example = "09:00:00", pattern = "HH:mm:ss")
		private LocalTime startTime;

		@Schema(description = "기관 운영 종료 시간",example = "19:00:00", pattern = "HH:mm:ss")
		private LocalTime endTime;

		@Schema(description = "공식 기관 주소", implementation = AddressDto.class)
		private AddressDto address;

		public OfficialInstitution toEntity() {
			return OfficialInstitution.builder()
				.name(name)
				.phoneNumber(phoneNumber)
				.code(code)
				.startTime(startTime)
				.endTime(endTime)
				.address(address.toEntity())
				.build();
		}

	}
}
