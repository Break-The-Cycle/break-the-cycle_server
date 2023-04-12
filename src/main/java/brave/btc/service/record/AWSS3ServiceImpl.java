package brave.btc.service.record;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import brave.btc.dto.record.DiaryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AWSS3ServiceImpl implements AWSS3Service {

	private final S3Client s3Client;

	@Override
	public String uploadPicture(MultipartFile multipartFile, String encodePassword) {

		return "https://s3.object.aws";
	}

	@Override
	public String uploadDiary(DiaryDto diaryDto, String encodePassword) {

		return "https://s3.object.aws";
	}

}
