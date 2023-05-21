package brave.btc.service.app.record;

import java.time.LocalDate;
import java.util.List;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.record.ViolentRecordDto;

public interface ViolentRecordService {

	/**
	 * 폭력 기록에 대한 날짜 리스트를 가져온다.
	 * @param usePersonId 사용자 pk
	 * @param fromDate 시작 날짜
	 * @param toDate 끝 날짜
	 * @return 폭력 기록 날짜 list
	 */
	List<LocalDate> findViolentRecordDateList(Integer usePersonId, LocalDate fromDate, LocalDate toDate);

	/**
	 * 특정 날짜에 업로드 되어 있는 폭력 기록 리스트를 가져온다
	 * @param usePersonId 사용자 pk
	 * @param targetDate 조회할 날짜
	 * @return 특정 날짜의 폭력 기록 list
	 */
	List<ViolentRecordDto.Response> findViolentRecordList(Integer usePersonId, LocalDate targetDate, ViolentRecordDto.Credential credential);

	/**
	 * 가정폭력에 대한 기록을 업로드한다.
	 * @param requestDto 제목, 내용, 사진을 포함한 req dto
	 * @return 응답 메세지
	 */
	CommonResponseDto<Object> uploadViolentRecord(ViolentRecordDto.Create requestDto);

	ViolentRecordDto.OutResponse outViolentRecord(ViolentRecordDto.OutRequest requestDto);
}
