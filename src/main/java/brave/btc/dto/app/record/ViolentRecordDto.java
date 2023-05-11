package brave.btc.dto.app.record;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import brave.btc.constant.enums.RecordDivision;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@Schema(title = "폭력 피해 기록", description = "폭력 피해 정보")
public class ViolentRecordDto {

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "폭력 피해 기록 등록 dto",description = "Violent record create request dto")
	public static class Create {
		@Schema(title = "제목", example = "제목을 입력해주세요.", requiredMode = Schema.RequiredMode.REQUIRED)
		private String title;
		@Schema(title = "내용", example = "내용을 입력해주세요.", requiredMode = Schema.RequiredMode.REQUIRED)
		private String contents;

		@Schema(title = "사용자 로그인 id", example = "kang123", requiredMode = Schema.RequiredMode.REQUIRED)
		private String loginId;

		@Schema(title = "로그인, 암호화 password", example = "kang123!", requiredMode = Schema.RequiredMode.REQUIRED )
		private String password;

		@Schema(title = "첨부 사진 리스트")
		private List<MultipartFile> pictureList;

		@Schema(title = "피해를 당한 날과 시간", requiredMode = Schema.RequiredMode.REQUIRED, implementation = LocalDateTime.class)
		private LocalDateTime targetDateTime;

		public DiaryDto toDiaryDto() {
			return DiaryDto.builder()
				.title(this.title)
				.contents(this.contents)
				.build();
		}
	}


	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "폭력 피해 기록 response dto",
		description = "폭력 기록에 대한 response dto 이다. diary, picture 하나 당 dto 하나 이다. ")
	public static class Response {

		@Schema(title = "record primary key")
		private Integer id;

		@Schema(title = "기록 구분자")
		private RecordDivision division;

		@Schema(title = "피해를 당한 날과 시간")
		private LocalDateTime targetDateTime;

	}
}
