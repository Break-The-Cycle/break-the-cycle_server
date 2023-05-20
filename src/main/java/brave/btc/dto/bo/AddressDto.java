package brave.btc.dto.bo;


import com.fasterxml.jackson.annotation.JsonUnwrapped;

import brave.btc.constant.enums.AddressDivision;
import brave.btc.domain.bo.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "주소 생성 요청", description = "주소 생성을 요청하는 dto")
public class AddressDto {


	@Schema(description="지역 구분", example = "KOREA_GYEONGGIDO")
	private AddressDivision division;

	@Schema(description="우편번호", example = "12345")
	private String postalNumber;

	@Schema(description="시도", example = "고양시")
	private String sido;

	@Schema(description="시군구", example = "일산동구")
	private String sigungu;

	@Schema(description="읍면동", example = "일동")
	private String eupmyeondong;

	@Schema(description="리")
	private String li;

	@Schema(description="도서")
	private String island;

	@Schema(description="번지")
	private String bungee;

	@Schema(description="상세지역",example = "일산동부 경찰서")
	private String detail;

	public Address toEntity() {
		return Address.builder()
			.division(division)
			.postalNumber(postalNumber)
			.sido(sido)
			.sigungu(sigungu)
			.eupmyeondong(eupmyeondong)
			.li(li)
			.island(island)
			.bungee(bungee)
			.detail(detail)
			.build();
	}
}

