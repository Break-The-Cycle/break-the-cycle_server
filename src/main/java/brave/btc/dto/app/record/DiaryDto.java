package brave.btc.dto.app.record;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@Schema(title = "일기 (글)", description = "가정 폭력 피해 사실에 대한 글 일기")
public class DiaryDto {

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "글로 쓴 일기 (제목/내용)",
		description = "글 일기 create dto")
	public static class Create implements Serializable {
		@Schema(title = "제목", example = "제목을 입력해주세요.")
		private String title;

		@Schema(title = "내용", example = "내용을 입력해주세요.")
		private String contents;

		public DiaryDto.Response toDiaryResponseDto() {
			return Response.builder()
				.title(title)
				.contents(contents)
				.build();
		}
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "글로 쓴 일기 (제목/내용)",
		description = "글 일기 response dto")
	public static class Response implements Serializable {
		@Schema(title = "제목", example = "제목을 입력해주세요.")
		private String title;

		@Schema(title = "내용", example = "내용을 입력해주세요.")
		private String contents;

		public DiaryDto.Create toCreateDto() {
			return Create.builder()
				.title(title)
				.contents(contents)
				.build();
		}

	}
}
