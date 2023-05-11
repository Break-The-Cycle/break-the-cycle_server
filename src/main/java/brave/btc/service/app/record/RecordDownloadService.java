package brave.btc.service.app.record;

import brave.btc.dto.app.record.DiaryDto;

public interface RecordDownloadService {

	/**
	 * 사진을 다운로드한다.
	 * @param objectUrl 저장된 url
	 * @param encodePassword 암호화 비밀번호.
	 * @return 다운로드 된 사진
	 */
	byte[] downloadPicture(String objectUrl, String encodePassword);

	/**
	 * 일기를  다운로드 한다.
	 * @param objectUrl 저장된 url
	 * @param encodePassword 암호화 비밀번호
	 * @return 다운로드 된 일기
	 */
	DiaryDto.Response downloadDiary(String objectUrl, String encodePassword);
}
