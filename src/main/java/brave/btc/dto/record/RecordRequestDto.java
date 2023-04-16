package brave.btc.dto.record;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "기록 다이어리", description = "가정 폭력 피해 사실을 기록하기 위한 다이어리")
public class RecordRequestDto {

	@Schema(title = "제목", example = "제목을 입력해주세요.")
	private String title;

	@Schema(title = "내용", example = "내용을 입력해주세요.")
	private String contents;

	@Schema(title = "등록 시간")
	private LocalDateTime date;

	@Schema(title = "사용자 로그인 id", example = "login123")
	private String loginId;
	
	@Schema(title = "로그인, 암호화 password", example = "qwer1234")
	private String password;

	@Parameter(name = "손 글씨")
	private byte[] handWriting;

	@Schema(title = "첨부 사진 리스트")
	private List<MultipartFile> pictureList;

	public DiaryDto toDiaryDto() {
		return DiaryDto.builder()
			.title(this.title)
			.contents(this.contents)
			.build();
	}
}
