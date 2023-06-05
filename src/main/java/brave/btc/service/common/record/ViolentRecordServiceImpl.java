package brave.btc.service.common.record;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import brave.btc.config.jwt.JwtProperties;
import brave.btc.config.security.CustomPasswordEncoder;
import brave.btc.constant.enums.FolderDivision;
import brave.btc.constant.enums.ManageDivision;
import brave.btc.constant.enums.RawPasswordDivision;
import brave.btc.constant.enums.RecordDivision;
import brave.btc.constant.enums.SubmissionDivision;
import brave.btc.domain.app.record.Diary;
import brave.btc.domain.app.record.Picture;
import brave.btc.domain.app.record.Record;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.domain.bo.submissionrecord.CounselingPersonSubmission;
import brave.btc.domain.bo.submissionrecord.CounselingPersonSubmissionId;
import brave.btc.domain.bo.submissionrecord.PolicePersonSubmission;
import brave.btc.domain.bo.submissionrecord.PolicePersonSubmissionId;
import brave.btc.domain.bo.submissionrecord.SubmissionDiary;
import brave.btc.domain.bo.submissionrecord.SubmissionPicture;
import brave.btc.domain.bo.submissionrecord.SubmissionRecord;
import brave.btc.domain.bo.submissionrecord.UsePersonSubmissionRecord;
import brave.btc.domain.bo.user.ManagePerson;
import brave.btc.domain.bo.user.UsePersonView;
import brave.btc.dto.app.record.DiaryDto;
import brave.btc.dto.app.record.ViolentRecordDto;
import brave.btc.dto.common.auth.UsePersonDto;
import brave.btc.exception.auth.AuthenticationInvalidException;
import brave.btc.exception.auth.UserPrincipalNotFoundException;
import brave.btc.exception.domain.EntityNotFoundException;
import brave.btc.repository.app.UsePersonRepository;
import brave.btc.repository.app.record.RecordRepository;
import brave.btc.repository.bo.CounselingPersonSubmissionRepository;
import brave.btc.repository.bo.ManagePersonRepository;
import brave.btc.repository.bo.PolicePersonSubmissionRepository;
import brave.btc.repository.bo.SubmissionRecordRepository;
import brave.btc.repository.bo.UsePersonViewRepository;
import brave.btc.service.common.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ViolentRecordServiceImpl implements ViolentRecordService {
	private final ManagePersonRepository managePersonRepository;
	private final UsePersonRepository usePersonRepository;
	private final UsePersonViewRepository usePersonViewRepository;
	private final RecordRepository recordRepository;
	private final SubmissionRecordRepository submissionRecordRepository;
	private final PolicePersonSubmissionRepository policePersonSubmissionRepository;
	private final CounselingPersonSubmissionRepository counselingPersonSubmissionRepository;
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
	public UsePersonDto.Response findViolentRecordUsePerson(String submissionToken) {

		Integer submissionRecordId = extractSubmissionRecordIdFromSubmissionToken(submissionToken);

		SubmissionRecord submissionRecord = submissionRecordRepository.findById(submissionRecordId)
			.orElseThrow(() -> new EntityNotFoundException(SubmissionRecord.class.getName(), submissionRecordId));

		UsePersonView usePersonView = submissionRecord.getUsePersonView();
		return usePersonView.toDto();
	}

	@Override
	public List<ViolentRecordDto.Response> findViolentRecordList(Integer managePersonId, String submissionToken) {

		Integer submissionRecordId = extractSubmissionRecordIdFromSubmissionToken(submissionToken);

		SubmissionRecord submissionRecord = submissionRecordRepository.findById(submissionRecordId)
			.orElseThrow(() -> new EntityNotFoundException(SubmissionRecord.class.getName(), submissionRecordId));

		//TODO : 테스트 끝나면 토큰 재사용 불가 로직 활성화 시키기
		//임시로 토큰 재사용 허가
		// if (submissionRecord.getSubmissionDivision() != null) {
		// 	throw new IllegalStateException("이미 사용이 완료된 토큰입니다.");
		// }

		ManagePerson managePerson = managePersonRepository.findById(managePersonId)
			.orElseThrow(() -> new UserPrincipalNotFoundException("해당 관리 개인이 존재하지 않습니다."));
		ManageDivision division = managePerson.getDivision();

		if (division == ManageDivision.COUNSELOR) {
			submissionRecord.changeSubmissionDivision(SubmissionDivision.COUNSELOR);
			CounselingPersonSubmission counselingPersonSubmission = CounselingPersonSubmission.builder()
				.id(new CounselingPersonSubmissionId(managePersonId, submissionRecordId))
				.managePerson(managePerson)
				.submissionRecord(submissionRecord)
				.build();
			counselingPersonSubmissionRepository.save(counselingPersonSubmission);
		} else if(division ==ManageDivision.POLICE_OFFICER) {
			submissionRecord.changeSubmissionDivision(SubmissionDivision.POLICE_OFFICER);
			PolicePersonSubmission policePersonSubmission = PolicePersonSubmission.builder()
				.id(new PolicePersonSubmissionId(managePersonId, submissionRecordId))
				.managePerson(managePerson)
				.submissionRecord(submissionRecord)
				.build();
			policePersonSubmissionRepository.save(policePersonSubmission);
		}

		List<UsePersonSubmissionRecord> recordList =
			submissionRecord.getUsePersonSubmissionRecordList();
		return recordList.stream()
			.filter(record -> (record.getRecordDivision() == RecordDivision.PICTURE) || (record.getRecordDivision() == RecordDivision.DIARY))
			.map(record -> {
					if (record.getRecordDivision() == RecordDivision.PICTURE) {
						SubmissionPicture picture = (SubmissionPicture)record;
						String pictureS3Url = picture.getContent();
						byte[] imageByteArrayResponse = recordDownloadService.downloadPicture(pictureS3Url);

						return ViolentRecordDto.Response.builder()
							.id(record.getId())
							.image(imageByteArrayResponse)
							.division(RecordDivision.PICTURE)
							.reportDate(record.getRecordDate())
							.build();

					} else if (record.getRecordDivision() == RecordDivision.DIARY) {
						SubmissionDiary diary = (SubmissionDiary)record;
						String diaryS3Url = diary.getContent();
						log.debug("[findViolentRecordList] diaryS3Url: {}", diaryS3Url);
						DiaryDto.Response diaryResponse = recordDownloadService.downloadDiary(diaryS3Url);
						return ViolentRecordDto.Response.builder()
							.id(record.getId())
							.diary(diaryResponse)
							.division(RecordDivision.DIARY)
							.reportDate(record.getRecordDate())
							.build();
					}
					throw new IllegalStateException("상태 이상 에러. 다른 종류가 들어올 수 없음");
				}
			).toList();
	}

	private Integer extractSubmissionRecordIdFromSubmissionToken(String submissionToken) {
		DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build()
			.verify(submissionToken);
		return decodedJWT.getClaim("submissionRecordId").asInt();
	}

	@Override
	public String uploadViolentRecord(Integer usePersonId, ViolentRecordDto.Create requestDto) {

		String sha256EncodedPassword = requestDto.getPassword();
		UsePerson usePerson = authService.checkIsCredentialValid(usePersonId, sha256EncodedPassword, RawPasswordDivision.SHA256);
		String loginId = usePerson.getLoginId();
		log.debug("[uploadRecord] usePerson: {}", usePerson);

		List<Record> newRecordList = new ArrayList<>();
		makeNewPictureRecordList(requestDto,loginId, sha256EncodedPassword, usePerson, newRecordList);
		makeNewDiaryRecord(requestDto,loginId, sha256EncodedPassword, usePerson, newRecordList);
		recordRepository.saveAll(newRecordList);

		log.info("[uploadRecord] 업로드 완료");
		return "일기가 성공적으로 업로드 되었습니다.";
	}

	@Override
	public ViolentRecordDto.OutResponse outViolentRecord(Integer usePersonId, ViolentRecordDto.OutRequest requestDto) {

		//유저의 record 전부 조회 하기 (diary, picture, recording)
		String sha256EncodedPassword = requestDto.getPassword();

		UsePerson usePerson = usePersonRepository.findById(usePersonId)
			.orElseThrow(() -> new UserPrincipalNotFoundException("해당 사용 개인을 찾을 수 없습니다."));
		String loginId = usePerson.getLoginId();

		String bcryptEncodedPassword = usePerson.getPassword();
		if (!customPasswordEncoder.matchesSHA256(sha256EncodedPassword, bcryptEncodedPassword)) {
			throw new AuthenticationInvalidException("비밀번호가 일치하지 않습니다.");
		}

		UsePersonView usePersonView = usePersonViewRepository.findById(usePersonId)
			.orElseThrow(()->new IllegalStateException("사용 개인, 사용 개인 뷰 정합성 이상 상태"));
		LocalDateTime nowDateTime = LocalDateTime.now();
		LocalDateTime expireDateTime = nowDateTime.plusDays(7);
		SubmissionRecord newSubmissionRecord = SubmissionRecord.builder()
			.usePersonView(usePersonView)
			.submissionDatetime(nowDateTime)
			.effectiveDatetime(expireDateTime)
			.build();
		List<UsePersonSubmissionRecord> newUsePersonSubmissionRecordList =
			downloadSecretRecordAndUploadOpen(requestDto, newSubmissionRecord, sha256EncodedPassword, usePersonId,loginId);
		newSubmissionRecord.changeUsePersonSubmissionRecordList(newUsePersonSubmissionRecordList);

		log.info("[ViolentRecordOut] requestDto: {}", requestDto);
		Integer savedSubmissionRecordId = submissionRecordRepository.save(newSubmissionRecord).getId();

		String submissionToken = publishSubmissionToken(savedSubmissionRecordId);

		return ViolentRecordDto.OutResponse.builder()
			.submissionToken(submissionToken)
			.expireDateTime(expireDateTime)
			.build();
	}

	private  String publishSubmissionToken(Integer savedSubmissionRecordId) {
		//토큰 생성 (유효 기간 : 일주일, submission_record를 생성하고 그 pk를 담아야 함)
		return JWT.create()
			.withSubject("violent record submission token")
			.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.ST_EXP_TIME))
			.withClaim("submissionRecordId", savedSubmissionRecordId)
			.sign(Algorithm.HMAC512(JwtProperties.SECRET));
	}

	private List<UsePersonSubmissionRecord> downloadSecretRecordAndUploadOpen(ViolentRecordDto.OutRequest requestDto,
		SubmissionRecord submissionRecord, String sha256EncodedPassword, Integer usePersonId, String loginId) {
		List<Record> recordList = recordRepository.searchAllViolentRecordList(usePersonId, requestDto.getFromDate(), requestDto.getToDate());
		return recordList.stream()
			.map(record -> {
					if (record.getRecordDivision() == RecordDivision.PICTURE) {
						Picture picture = (Picture)record;
						String secretPictureS3Url = picture.getContent();
						byte[] imageByteArrayResponse = recordDownloadService.downloadPicture(secretPictureS3Url, sha256EncodedPassword);
						log.debug("[downloadSecretRecordAndUploadOpen] imageByteArrayResponse");
						String objectPath = makeObjectPath(FolderDivision.OPEN, loginId, RecordDivision.PICTURE);
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
						String objectPath = makeObjectPath(FolderDivision.OPEN, loginId, RecordDivision.DIARY);
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

	private void makeNewDiaryRecord(ViolentRecordDto.Create requestDto, String loginId, String sha256EncodedPassword, UsePerson usePerson, List<Record> newRecordList) {
		DiaryDto.Create diaryDto = requestDto.toDiaryDto();
		String objectPath = makeObjectPath(FolderDivision.SECRET, loginId, RecordDivision.DIARY);
		String diaryS3Url = recordUploadService.uploadDiary(diaryDto, objectPath, sha256EncodedPassword);
		Record newDiary = Diary.builder()
			.usePerson(usePerson)
			.content(diaryS3Url)
			.reportDate(requestDto.getReportDate())
			.build();
		newRecordList.add(newDiary);
	}

	private void makeNewPictureRecordList(ViolentRecordDto.Create requestDto, String loginId, String sha256EncodedPassword, UsePerson usePerson, List<Record> newRecordList) {
		requestDto.getPictureList()
			.stream()
			.map(multipartFile -> {
					String objectPath = makeObjectPath(FolderDivision.SECRET, loginId,  RecordDivision.PICTURE);
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
