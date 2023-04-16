package brave.btc.service.record;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brave.btc.domain.record.Diary;
import brave.btc.domain.record.Picture;
import brave.btc.domain.record.Record;
import brave.btc.domain.user.UsePerson;
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.record.DiaryDto;
import brave.btc.dto.record.RecordRequestDto;
import brave.btc.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RecordServiceImpl implements RecordService {

	private final AuthService authService;
	private final AWSS3Service awss3Service;

	@Override
	public CommonResponseDto<Object> uploadRecord(RecordRequestDto requestDto) {

		String loginId = requestDto.getLoginId();
		String password = requestDto.getPassword();
		UsePerson usePerson = authService.checkIsPasswordEqual(loginId, password);

		Record newRecord = Record.builder()
			.usePerson(usePerson)
			.date(requestDto.getDate())
			.emotion(requestDto.getEmotion())
			.division(requestDto.getDivision())
			.build();

		List<Picture> newPictureList = requestDto.getPictureList()
			.stream()
			.map(multipartFile -> awss3Service.uploadPicture(multipartFile, password))
			.map(pictureS3Url -> Picture.builder()
				.record(newRecord)
				.content(pictureS3Url)
				.build())
			.toList();

		DiaryDto diaryDto = requestDto.getDiary();
		String diaryS3Url = awss3Service.uploadDiary(diaryDto, password);
		Diary newDiary = Diary.builder().record(newRecord).content(diaryS3Url).build();

		newRecord.allocatePictureListAndDiary(newPictureList,newDiary);
		return CommonResponseDto.builder()
			.message("일기가 성공적으로 업로드 되었습니다.")
			.build();
	}
}
