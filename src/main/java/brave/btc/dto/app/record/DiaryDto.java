package brave.btc.dto.app.record;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "일기 (글)", description = "가정 폭력 피해 사실에 대한 글 일기")
public class DiaryDto implements Serializable {

	@Schema(title = "제목", example = "제목을 입력해주세요.")
	private String title;

	@Schema(title = "내용", example = "내용을 입력해주세요.")
	private String contents;
}
