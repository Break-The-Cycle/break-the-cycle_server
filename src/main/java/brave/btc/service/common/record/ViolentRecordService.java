package brave.btc.service.common.record;

import java.time.LocalDate;
import java.util.List;

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
	 * 특정 날짜에 업로드 되어 있는 가정 폭력 기록 리스트를 가져온다
	 * @param usePersonId 사용자 pk
	 * @param targetDate 조회할 날짜
	 * @return 특정 날짜의 폭력 기록 list
	 */
	List<ViolentRecordDto.Response> findViolentRecordList(Integer usePersonId, LocalDate targetDate, ViolentRecordDto.Credential credential);


	/**
	 * 데이터 내보내기에 대한 가정 폭력 기록 리스트를 가져온다.
	 *
	 * @param managePersonId
	 * @param submissionToken 가정 폭력 기록 데이터 내보내기 후 발행된 제출 토큰
	 * @return 폭력 기록 list
	 */
	List<ViolentRecordDto.Response> findViolentRecordList(Integer managePersonId, String submissionToken);

	/**
	 * 가정폭력에 대한 기록을 업로드한다.
	 * @param requestDto 제목, 내용, 사진을 포함한 req dto
	 * @return 응답 메세지
	 */
	String uploadViolentRecord(ViolentRecordDto.Create requestDto);

	/**
	 * 기정폭력에 대해 데이터 내보내기를 수행한다.
	 * @param requestDto 가정폭력 내보내기를 위한 req dto (날짜 범위, 로그인 아이디, 암호화 비밀번호)
	 * @return 내보내기에 대해 발행된 토큰
	 */
	ViolentRecordDto.OutResponse outViolentRecord(ViolentRecordDto.OutRequest requestDto);
	
	
	
}
