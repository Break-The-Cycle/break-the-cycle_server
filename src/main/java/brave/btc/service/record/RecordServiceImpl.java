package brave.btc.service.record;

import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brave.btc.constant.enums.RecordDivision;
import brave.btc.domain.record.Diary;
import brave.btc.domain.record.Picture;
import brave.btc.domain.record.Record;
import brave.btc.domain.user.UsePerson;
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.record.DiaryDto;
import brave.btc.dto.record.RecordRequestDto;
import brave.btc.repository.record.RecordRepository;
import brave.btc.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RecordServiceImpl implements RecordService {

	private final RecordRepository recordRepository;
	private final AuthService authService;
	private final AWSS3Service awsS3Service;

	@Override
	public CommonResponseDto<Object> uploadRecord(RecordRequestDto requestDto) {

		String loginId = requestDto.getLoginId();
		String password = requestDto.getPassword();
		UsePerson usePerson = authService.checkIsPasswordEqual(loginId, password);
		log.debug("[uploadRecord] usePerson: {}", usePerson);

		List<Record> newRecordList = makeNewPictureRecordList(requestDto, password, usePerson);
		makeNewDiaryRecord(requestDto, password, usePerson, newRecordList);
		makeNewHandWritingRecord(requestDto, password, usePerson, newRecordList);

		recordRepository.saveAll(newRecordList);

		log.info("[uploadRecord] 업로드 완료");
		return CommonResponseDto.builder()
			.message("일기가 성공적으로 업로드 되었습니다.")
			.build();
	}

	private void makeNewHandWritingRecord(RecordRequestDto requestDto, String password, UsePerson usePerson, List<Record> newRecordList) {
		byte[] handWriting = requestDto.getHandWriting();
		String handWritingS3Url = awsS3Service.uploadPicture(handWriting, password);
		Record newHandWritingPicture = Picture.builder()
			.usePerson(usePerson)
			.date(requestDto.getDate())
			.division(RecordDivision.PICTURE)
			.content(handWritingS3Url)
			.build();
		newRecordList.add(newHandWritingPicture);
	}

	private void makeNewDiaryRecord(RecordRequestDto requestDto, String password, UsePerson usePerson, List<Record> newRecordList) {
		DiaryDto diaryDto = requestDto.toDiaryDto();
		String diaryS3Url = awsS3Service.uploadDiary(diaryDto, password);
		Record newDiary = Diary.builder()
			.usePerson(usePerson)
			.date(requestDto.getDate())
			.division(RecordDivision.DIARY)
			.content(diaryS3Url).build();
		newRecordList.add(newDiary);
	}

	@NotNull
	private List<Record> makeNewPictureRecordList(RecordRequestDto requestDto, String password, UsePerson usePerson) {
		return requestDto.getPictureList()
			.stream()
			.map(multipartFile -> awsS3Service.uploadPicture(multipartFile, password))
			.map(pictureS3Url -> (Record) Picture.builder()
					.usePerson(usePerson)
					.date(requestDto.getDate())
					.division(RecordDivision.PICTURE)
					.content(pictureS3Url)
					.build())
			.collect(Collectors.toList());
	}
}
