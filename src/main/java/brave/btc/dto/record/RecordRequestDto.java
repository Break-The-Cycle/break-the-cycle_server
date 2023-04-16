package brave.btc.dto.record;

import java.time.LocalDateTime;
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
@Schema(title = "기록 다이어리", description = "가정 폭력 피해 사실을 기록하기 위한 다이어리")
public class RecordRequestDto {

	
	@Schema(title = "일기")
	private DiaryDto diary;

	@Schema(title = "등록 시간")
	private LocalDateTime date;

	@Schema(title = "사용자 로그인 id")
	private String loginId;
	
	@Schema(title = "로그인, 암호화 password")
	private String password;

	@Schema(title = "감정 이모티콘")
	private String emotion;

	@Schema(title = "구분")
	private String division;

	@Schema(title = "첨부 이미지 리스트", implementation = MultipartFile.class)
	private List<MultipartFile> pictureList;


}
