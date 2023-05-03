package brave.btc.service.record;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brave.btc.constant.enums.RecordDivision;
import brave.btc.domain.persistence.record.Diary;
import brave.btc.domain.persistence.record.Picture;
import brave.btc.domain.persistence.record.Record;
import brave.btc.domain.persistence.user.UsePerson;
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.record.DiaryDto;
import brave.btc.dto.record.RecordRequestDto;
import brave.btc.repository.persistence.record.RecordRepository;
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
	private final RecordUploadService recordUploadService;

	@Override
	public CommonResponseDto<Object> uploadRecord(RecordRequestDto requestDto) {

		String loginId = requestDto.getLoginId();
		String password = requestDto.getPassword();
		UsePerson usePerson = authService.checkIsPasswordEqual(loginId, password);
		log.debug("[uploadRecord] usePerson: {}", usePerson);

		List<Record> newRecordList = new ArrayList<>();
		makeNewPictureRecordList(requestDto, password, usePerson, newRecordList);
		makeNewDiaryRecord(requestDto, password, usePerson, newRecordList);
		recordRepository.saveAll(newRecordList);

		log.info("[uploadRecord] 업로드 완료");
		return CommonResponseDto.builder()
			.message("일기가 성공적으로 업로드 되었습니다.")
			.build();
	}

	private void makeNewDiaryRecord(RecordRequestDto requestDto, String password, UsePerson usePerson, List<Record> newRecordList) {
		DiaryDto diaryDto = requestDto.toDiaryDto();
		String objectPath = makeObjectPath(requestDto.getLoginId(), RecordDivision.DIARY);
		String diaryS3Url = recordUploadService.uploadDiary(diaryDto, objectPath, password);
		Record newDiary = Diary.builder()
			.usePerson(usePerson)
			.date(requestDto.getDate())
			.division(RecordDivision.DIARY)
			.content(diaryS3Url).build();
		newRecordList.add(newDiary);
	}

	private void makeNewPictureRecordList(RecordRequestDto requestDto, String password, UsePerson usePerson, List<Record> newRecordList) {
		requestDto.getPictureList()
			.stream()
			.map(multipartFile -> {
					String objectPath = makeObjectPath(requestDto.getLoginId(), RecordDivision.PICTURE);
					return recordUploadService.uploadPicture(multipartFile, objectPath, password);})
			.map(pictureS3Url -> (Record) Picture.builder()
					.usePerson(usePerson)
					.date(requestDto.getDate())
					.division(RecordDivision.PICTURE)
					.content(pictureS3Url)
					.build())
			.forEach(newRecordList::add);
	}

	private  String makeObjectPath(String loginId, RecordDivision division) {
		LocalDate today = LocalDate.now();
		UUID uuid = UUID.randomUUID();
		return  "/" + loginId + "/" + today.toString() + "/" + division.getCode() + "/" + uuid;
	}
}
