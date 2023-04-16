package brave.btc.service.record;

import org.springframework.web.multipart.MultipartFile;

import brave.btc.dto.record.DiaryDto;

public interface AWSS3Service {

	/**
	 * 사진을 S3에 업로드한다.
	 * @param multipartFile 업로드할 사진 파일.
	 * @param encodePassword 암호화 비밀번호.
	 * @return 업로드된 주소
	 */
	String uploadPicture(MultipartFile multipartFile, String encodePassword);

	/**
	 *  손글씨를 S3에 업로드한다.
	 * @param handWriting 손글씨 byte array;
	 * @param encodePassword 암호화 비밀번호
	 * @return 업로드된 주소
	 */
	String uploadPicture(byte[] handWriting, String encodePassword);

	/**
	 * 일기를 S3에 업로드한다.
	 * @param diaryDto Diary dto
	 * @param encodePassword 암호화 비밀번호
	 * @return 업로드된 주소
	 */
	String uploadDiary(DiaryDto diaryDto, String encodePassword);

}
