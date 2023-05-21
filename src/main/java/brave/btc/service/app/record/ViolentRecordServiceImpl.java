package brave.btc.service.app.record;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
import brave.btc.service.app.auth.AuthService;
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
	private final RecordDownloadService recordDownloadService;


	@Override
	public List<LocalDate> findViolentRecordDateList(Integer usePersonId, LocalDate fromDate, LocalDate toDate) {
		return recordRepository.searchViolentRecordDateList(usePersonId, fromDate, toDate);
	}

	@Override
	public List<ViolentRecordDto.Response> findViolentRecordList(Integer usePersonId, LocalDate targetDate, ViolentRecordDto.Credential credential) {

		List<Record> recordList = recordRepository.searchViolentRecordList(usePersonId, targetDate);
		String password = credential.getPassword();
		return recordList.stream()
			.filter(record -> (record.getRecordDivision() == RecordDivision.PICTURE) || (record.getRecordDivision() == RecordDivision.DIARY))
			.map(record -> {
					if (record.getRecordDivision() == RecordDivision.PICTURE) {
						Picture picture = (Picture)record;
						String pictureS3Url = picture.getContent();
						log.debug("[findViolentRecordList] pictureS3Url: {}", pictureS3Url);
						byte[] imageByteArrayResponse = recordDownloadService.downloadPicture(pictureS3Url, password);
						return ViolentRecordDto.Response.builder()
							.id(record.getId())
							.image(imageByteArrayResponse)
							.division(RecordDivision.PICTURE)
							.reportDate(record.getReportDate())
							.build();

					} else if (record.getRecordDivision() == RecordDivision.DIARY) {
						Diary diary = (Diary)record;
						String diaryS3Url = diary.getContent();
						log.debug("[findViolentRecordList] diaryS3Url: {}", diaryS3Url);
						DiaryDto.Response diaryResponse = recordDownloadService.downloadDiary(diaryS3Url, password);
						return ViolentRecordDto.Response.builder()
							.id(record.getId())
							.diary(diaryResponse)
							.division(RecordDivision.DIARY)
							.reportDate(record.getReportDate())
							.build();
					}
					throw new IllegalStateException("상태 이상 에러. 다른 종류가 들어올 수 없음");
				}
			).collect(Collectors.toList());
	}

	@Override
	public CommonResponseDto<Object> uploadViolentRecord(ViolentRecordDto.Create requestDto) {

		String loginId = requestDto.getLoginId();
		String rawPassword = requestDto.getPassword();
		UsePerson usePerson = authService.checkIsCredentialValid(loginId, rawPassword);
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

	@Override
	public ViolentRecordDto.OutResponse outViolentRecord(ViolentRecordDto.OutRequest requestDto) {

		//유저의 record 전부 조회하기

		return null;
	}

	private void makeNewDiaryRecord(ViolentRecordDto.Create requestDto, String password, UsePerson usePerson, List<Record> newRecordList) {
		DiaryDto.Create diaryDto = requestDto.toDiaryDto();
		String objectPath = makeObjectPath(requestDto.getLoginId(), RecordDivision.DIARY);
		String diaryS3Url = recordUploadService.uploadDiary(diaryDto, objectPath, password);
		Record newDiary = Diary.builder()
			.usePerson(usePerson)
			.content(diaryS3Url)
			.reportDate(requestDto.getReportDate())
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
					.reportDate(requestDto.getReportDate())
					.build())
			.forEach(newRecordList::add);
	}

	private  String makeObjectPath(String loginId, RecordDivision division) {
		LocalDate today = LocalDate.now();
		UUID uuid = UUID.randomUUID();
		return  "/" + loginId + "/" + today.toString() + "/" + division.getCode() + "/" + uuid;
	}
}
