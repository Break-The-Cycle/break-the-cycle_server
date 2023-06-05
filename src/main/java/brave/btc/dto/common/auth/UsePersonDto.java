package brave.btc.dto.common.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@Schema(title = "사용 개인", description = "사용 개인 관련 DTO ")
public class UsePersonDto {

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "사용 개인 응답 dto",
		description = "사용 개인에 응답에 사용되는 dto")
	public static class Response {
		private Integer id;
		private String name;
		private String phoneNumber;


	}
}
