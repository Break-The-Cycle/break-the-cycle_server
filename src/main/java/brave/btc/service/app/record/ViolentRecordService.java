package brave.btc.service.app.record;

import java.time.LocalDate;
import java.util.List;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.record.ViolentRecordDto;

public interface ViolentRecordService {

	/**
	 * 폭력 기록에 대한 간단한 정보를 가져온다.
	 * @param usePersonId 사용자 pk
	 * @param fromDate 시작 날짜
	 * @param toDate 끝 날짜
	 * @return 폭력 기록 날짜 list
	 */
	List<LocalDate> findSimpleViolentRecordList(int usePersonId, LocalDate fromDate, LocalDate toDate);

	/**
	 * 가정폭력에 대한 기록을 업로드한다.
	 * @param requestDto 제목, 내용, 사진을 포함한 req dto
	 * @return 응답 메세지
	 */
	CommonResponseDto<Object> uploadViolentRecord(ViolentRecordDto.Create requestDto);

}
