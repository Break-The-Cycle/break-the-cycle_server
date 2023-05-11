package brave.btc.service.app.record;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brave.btc.constant.enums.RecordDivision;
import brave.btc.domain.app.record.Diary;
import brave.btc.domain.app.record.Picture;
import brave.btc.domain.app.record.Record;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.record.DiaryDto;
import brave.btc.dto.app.record.ViolentRecordDto;
import brave.btc.repository.app.record.RecordRepository;
import brave.btc.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ViolentRecordServiceImpl implements ViolentRecordService {

	private final RecordRepository recordRepository;
	private final AuthService authService;
	private final RecordUploadService recordUploadService;


	@Override
	public List<LocalDate>
	findSimpleViolentRecordList(int usePersonId, LocalDate fromDate, LocalDate toDate) {
		List<String> dateStringList = recordRepository.searchSimpleViolentRecordList(usePersonId, fromDate, toDate);
		List<LocalDate> dateList = new ArrayList<>();
		for (String dateString : dateStringList) {
			LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			dateList.add(localDate);
		}
		return dateList;
	}

	@Override
	public CommonResponseDto<Object> uploadViolentRecord(ViolentRecordDto.Create requestDto) {

		String loginId = requestDto.getLoginId();
		String rawPassword = requestDto.getPassword();
		UsePerson usePerson = authService.checkIsPasswordEqual(loginId, rawPassword);
		log.debug("[uploadRecord] usePerson: {}", usePerson);

		List<Record> newRecordList = new ArrayList<>();
		makeNewPictureRecordList(requestDto, rawPassword, usePerson, newRecordList);
		makeNewDiaryRecord(requestDto, rawPassword, usePerson, newRecordList);
		recordRepository.saveAll(newRecordList);

		log.info("[uploadRecord] 업로드 완료");
		return CommonResponseDto.builder()
			.message("일기가 성공적으로 업로드 되었습니다.")
			.build();
	}

	private void makeNewDiaryRecord(ViolentRecordDto.Create requestDto, String password, UsePerson usePerson, List<Record> newRecordList) {
		DiaryDto diaryDto = requestDto.toDiaryDto();
		String objectPath = makeObjectPath(requestDto.getLoginId(), RecordDivision.DIARY);
		String diaryS3Url = recordUploadService.uploadDiary(diaryDto, objectPath, password);
		Record newDiary = Diary.builder()
			.usePerson(usePerson)
			.content(diaryS3Url)
			.datetime(requestDto.getTargetDateTime())
			.build();
		newRecordList.add(newDiary);
	}

	private void makeNewPictureRecordList(ViolentRecordDto.Create requestDto, String password, UsePerson usePerson, List<Record> newRecordList) {
		requestDto.getPictureList()
			.stream()
			.map(multipartFile -> {
					String objectPath = makeObjectPath(requestDto.getLoginId(), RecordDivision.PICTURE);
					return recordUploadService.uploadPicture(multipartFile, objectPath, password);})
			.map(pictureS3Url -> (Record) Picture.builder()
					.usePerson(usePerson)
					.content(pictureS3Url)
					.datetime(requestDto.getTargetDateTime())
					.build())
			.forEach(newRecordList::add);
	}

	private  String makeObjectPath(String loginId, RecordDivision division) {
		LocalDate today = LocalDate.now();
		UUID uuid = UUID.randomUUID();
		return  "/" + loginId + "/" + today.toString() + "/" + division.getCode() + "/" + uuid;
	}
}
