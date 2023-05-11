package brave.btc.dto.app.record;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "폭력 피해 기록", description = "폭력 피해 정보")
public class ViolentRecordDto {

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "폭력 피해 기록 등록 dto",description = "Violent record create request dto")
	public static class Create {
		@Schema(title = "제목", example = "제목을 입력해주세요.")
		private String title;

		@Schema(title = "내용", example = "내용을 입력해주세요.")
		private String contents;

		@Schema(title = "사용자 로그인 id", example = "login123")
		private String loginId;

		@Schema(title = "로그인, 암호화 password", example = "qwer1234")
		private String password;

		@Schema(title = "첨부 사진 리스트")
		private List<MultipartFile> pictureList;

		public DiaryDto toDiaryDto() {
			return DiaryDto.builder()
				.title(this.title)
				.contents(this.contents)
				.build();
		}
	}
}
