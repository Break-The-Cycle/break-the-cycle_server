package brave.btc.service.app.record;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import brave.btc.config.jwt.JwtProperties;
import brave.btc.config.security.CustomPasswordEncoder;
import brave.btc.constant.enums.FolderDivision;
import brave.btc.constant.enums.RawPasswordDivision;
import brave.btc.constant.enums.RecordDivision;
import brave.btc.domain.app.record.Diary;
import brave.btc.domain.app.record.Picture;
import brave.btc.domain.app.record.Record;
import brave.btc.domain.app.submission_record.SubmissionDiary;
import brave.btc.domain.app.submission_record.SubmissionPicture;
import brave.btc.domain.app.submission_record.SubmissionRecord;
import brave.btc.domain.app.submission_record.UsePersonSubmissionRecord;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.dto.app.record.DiaryDto;
import brave.btc.dto.app.record.ViolentRecordDto;
import brave.btc.exception.auth.AuthenticationInvalidException;
import brave.btc.exception.auth.UserPrincipalNotFoundException;
import brave.btc.repository.app.UsePersonRepository;
import brave.btc.repository.app.record.RecordRepository;
import brave.btc.repository.app.record.SubmissionRecordRepository;
import brave.btc.service.app.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ViolentRecordServiceImpl implements ViolentRecordService {
	private final UsePersonRepository usePersonRepository;
	private final RecordRepository recordRepository;
	private final SubmissionRecordRepository submissionRecordRepository;
	private final AuthService authService;
	private final RecordUploadService recordUploadService;
	private final RecordDownloadService recordDownloadService;
	private final CustomPasswordEncoder customPasswordEncoder;


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
			).toList();
	}

	@Override
	public String uploadViolentRecord(ViolentRecordDto.Create requestDto) {

		String loginId = requestDto.getLoginId();
		String sha256EncodedPassword = requestDto.getPassword();
		UsePerson usePerson = authService.checkIsCredentialValid(loginId, sha256EncodedPassword, RawPasswordDivision.SHA256);
		log.debug("[uploadRecord] usePerson: {}", usePerson);

		List<Record> newRecordList = new ArrayList<>();
		makeNewPictureRecordList(requestDto, sha256EncodedPassword, usePerson, newRecordList);
		makeNewDiaryRecord(requestDto, sha256EncodedPassword, usePerson, newRecordList);
		recordRepository.saveAll(newRecordList);

		log.info("[uploadRecord] 업로드 완료");
		return "일기가 성공적으로 업로드 되었습니다.";
	}

	@Override
	public ViolentRecordDto.OutResponse outViolentRecord(ViolentRecordDto.OutRequest requestDto) {

		//유저의 record 전부 조회 하기 (diary, picture, recording)
		String loginId = requestDto.getLoginId();
		String sha256EncodedPassword = requestDto.getPassword();

		UsePerson usePerson = usePersonRepository.findByLoginId(loginId)
			.orElseThrow(() -> new UserPrincipalNotFoundException("해당 사용 개인을 찾을 수 없습니다."));
		Integer usePersonId = usePerson.getId();
		String bcryptEncodedPassword = usePerson.getPassword();
		if (!customPasswordEncoder.matchesSHA256(sha256EncodedPassword, bcryptEncodedPassword)) {
			throw new AuthenticationInvalidException("비밀번호가 일치하지 않습니다.");
		}

		LocalDateTime nowDateTime = LocalDateTime.now();
		LocalDateTime expireDateTime = nowDateTime.plusDays(7);
		SubmissionRecord newSubmissionRecord = SubmissionRecord.builder()
			.usePerson(usePerson)
			.submissionDatetime(nowDateTime)
			.effectiveDatetime(expireDateTime)
			.submissionDivision(requestDto.getSubmissionDivision())
			.build();
		List<UsePersonSubmissionRecord> newUsePersonSubmissionRecordList =
			downloadSecretRecordAndUploadOpen(requestDto, newSubmissionRecord, sha256EncodedPassword, usePersonId);
		newSubmissionRecord.changeUsePersonSubmissionRecordList(newUsePersonSubmissionRecordList);

		log.info("[ViolentRecordOut] requestDto: {}", requestDto);
		Integer savedSubmissionRecordId = submissionRecordRepository.save(newSubmissionRecord).getId();

		String submissionToken = publishSubmissionToken(expireDateTime, savedSubmissionRecordId);

		return ViolentRecordDto.OutResponse.builder()
			.submissionToken(submissionToken)
			.expireDateTime(expireDateTime)
			.build();
	}

	private  String publishSubmissionToken(LocalDateTime expireDateTime, Integer savedSubmissionRecordId) {
		//토큰 생성 (유효 기간 : 일주일, submission_record를 생성하고 그 pk를 담아야 함)
		return JWT.create()
			.withSubject("violent record submission token")
			.withExpiresAt(new Date(expireDateTime.toEpochSecond(ZoneOffset.UTC)))
			.withClaim("submissionRecordId", savedSubmissionRecordId)
			.sign(Algorithm.HMAC512(JwtProperties.SECRET));
	}

	private List<UsePersonSubmissionRecord> downloadSecretRecordAndUploadOpen(ViolentRecordDto.OutRequest requestDto,
		SubmissionRecord submissionRecord, String sha256EncodedPassword, Integer usePersonId) {
		List<Record> recordList = recordRepository.searchAllViolentRecordList(usePersonId, requestDto.getFromDate(), requestDto.getToDate());
		return recordList.stream()
			.map(record -> {
					if (record.getRecordDivision() == RecordDivision.PICTURE) {
						Picture picture = (Picture)record;
						String secretPictureS3Url = picture.getContent();
						byte[] imageByteArrayResponse = recordDownloadService.downloadPicture(secretPictureS3Url, sha256EncodedPassword);
						log.debug("[downloadSecretRecordAndUploadOpen] imageByteArrayResponse");
						String objectPath = makeObjectPath(FolderDivision.OPEN, requestDto.getLoginId(), RecordDivision.PICTURE);
						log.debug("[downloadSecretRecordAndUploadOpen] objectPath: {}", objectPath);
						String openPictureS3Url = recordUploadService.uploadPicture(imageByteArrayResponse, objectPath);
						log.debug("[downloadSecretRecordAndUploadOpen] openPictureS3Url: {}", openPictureS3Url);

						return SubmissionPicture.builder()
							.recordDate(picture.getReportDate())
							.content(openPictureS3Url)
							.submissionRecord(submissionRecord)
							.build();

					} else if (record.getRecordDivision() == RecordDivision.DIARY) {
						Diary diary = (Diary)record;
						String secretDiaryS3Url = diary.getContent();
						log.debug("[findViolentRecordList] secretDiaryS3Url: {}", secretDiaryS3Url);
						DiaryDto.Response diaryResponse = recordDownloadService.downloadDiary(secretDiaryS3Url, sha256EncodedPassword);
						String objectPath = makeObjectPath(FolderDivision.OPEN, requestDto.getLoginId(), RecordDivision.DIARY);
						DiaryDto.Create diaryCreateDto = diaryResponse.toCreateDto();
						String openDiaryS3Url = recordUploadService.uploadDiary(diaryCreateDto, objectPath);

						return SubmissionDiary.builder()
							.recordDate(diary.getReportDate())
							.content(openDiaryS3Url)
							.submissionRecord(submissionRecord)
							.build();
					}
					throw new IllegalStateException("상태 이상 에러. 다른 종류가 들어올 수 없음");
				}
			).toList();
	}

	private void makeNewDiaryRecord(ViolentRecordDto.Create requestDto, String sha256EncodedPassword, UsePerson usePerson, List<Record> newRecordList) {
		DiaryDto.Create diaryDto = requestDto.toDiaryDto();
		String objectPath = makeObjectPath(FolderDivision.SECRET, requestDto.getLoginId(), RecordDivision.DIARY);
		String diaryS3Url = recordUploadService.uploadDiary(diaryDto, objectPath, sha256EncodedPassword);
		Record newDiary = Diary.builder()
			.usePerson(usePerson)
			.content(diaryS3Url)
			.reportDate(requestDto.getReportDate())
			.build();
		newRecordList.add(newDiary);
	}

	private void makeNewPictureRecordList(ViolentRecordDto.Create requestDto, String sha256EncodedPassword, UsePerson usePerson, List<Record> newRecordList) {
		requestDto.getPictureList()
			.stream()
			.map(multipartFile -> {
					String objectPath = makeObjectPath(FolderDivision.SECRET, requestDto.getLoginId(),  RecordDivision.PICTURE);
					return recordUploadService.uploadPicture(multipartFile, objectPath, sha256EncodedPassword);})
			.map(pictureS3Url -> (Record) Picture.builder()
					.usePerson(usePerson)
					.content(pictureS3Url)
					.reportDate(requestDto.getReportDate())
					.build())
			.forEach(newRecordList::add);
	}

	private  String makeObjectPath(FolderDivision folderDivision, String loginId,  RecordDivision division) {
		LocalDate today = LocalDate.now();
		UUID uuid = UUID.randomUUID();
		return folderDivision + "/" + loginId + "/" +  today + "/" + division.getCode() + "/" + uuid;
	}
}
