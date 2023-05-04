package brave.btc.service.app.record;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.record.RecordRequestDto;

public interface RecordService {

	/**
	 * 가정폭력에 대한 기록을 업로드한다.
	 * @param requestDto 제목, 내용, 사진을 포함한 req dto
	 * @return 응답 메세지
	 */
	CommonResponseDto<Object> uploadRecord(RecordRequestDto requestDto);
}
