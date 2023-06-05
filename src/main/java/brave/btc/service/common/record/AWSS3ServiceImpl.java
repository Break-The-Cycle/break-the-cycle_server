package brave.btc.service.common.record;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.SerializationUtils;
import org.springframework.web.multipart.MultipartFile;

import brave.btc.dto.app.record.DiaryDto;
import io.awspring.cloud.s3.S3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.utils.BinaryUtils;
import software.amazon.awssdk.utils.Md5Utils;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AWSS3ServiceImpl implements AWSS3Service {

	@Value("${cloud.aws.s3.bucket}")
	private String BUCKET_NAME;

	private final String S3_BUCKET_PREFIX_URL = "s3://break-the-cycle-use-person-record";
	private final S3Client s3Client;

	@Override
	public String uploadPicture(MultipartFile multipartFile, String objectPath, String encodePassword) {

		try {
			s3Client.putObject(
				getPutObjectRequest(objectPath, encodePassword),
				RequestBody.fromInputStream(multipartFile.getInputStream(),multipartFile.getSize()));
		} catch (Exception e) {
			throw new S3Exception("S3 업로드 도중 에러 발생: "+e.getMessage(),e);
		}
		return S3_BUCKET_PREFIX_URL+ "/" +objectPath;
	}

	@Override
	public String uploadPicture(byte[] multipartFile, String objectPath) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(multipartFile);

			s3Client.putObject(
				getPutObjectRequest(objectPath),
				RequestBody.fromInputStream(bais,multipartFile.length));
		} catch (Exception e) {
			throw new S3Exception("S3 업로드 도중 에러 발생: "+e.getMessage(),e);
		}
		return S3_BUCKET_PREFIX_URL+ "/" +objectPath;
	}

	@Override
	public String uploadDiary(DiaryDto.Create diaryDto, String objectPath, String encodePassword) {

		try {
			s3Client.putObject(
				getPutObjectRequest(objectPath, encodePassword),
				RequestBody.fromBytes(
					Objects.requireNonNull(SerializationUtils.serialize(diaryDto))
				));
		} catch (Exception e) {
			throw new S3Exception("S3 업로드 도중 에러 발생: "+e.getMessage(), e);
		}
		return S3_BUCKET_PREFIX_URL+ "/"  +objectPath;
	}

	@Override
	public String uploadDiary(DiaryDto.Create diaryDto, String objectPath) {

		try {
			s3Client.putObject(
				getPutObjectRequest(objectPath),
				RequestBody.fromBytes(
					Objects.requireNonNull(SerializationUtils.serialize(diaryDto))
				));
		} catch (Exception e) {
			throw new S3Exception("S3 업로드 도중 에러 발생: "+e.getMessage(), e);
		}
		return S3_BUCKET_PREFIX_URL+ "/" +objectPath;
	}

	@Override
	public byte[] downloadPicture(String objectUrl, String encodePassword) {

		try {
			ResponseInputStream<GetObjectResponse> pictureObject = s3Client.getObject(
				getGetObjectRequest(objectUrl, encodePassword)
			);
			return pictureObject.readAllBytes();
		} catch (Exception e) {
			throw new S3Exception("S3 다운로드 도중 에러 발생: "+e.getMessage(), e);
		}
	}

	@Override
	public byte[] downloadPicture(String objectUrl) {
		try {
			ResponseInputStream<GetObjectResponse> pictureObject = s3Client.getObject(
				getGetObjectRequest(objectUrl)
			);
			return pictureObject.readAllBytes();
		} catch (Exception e) {
			throw new S3Exception("S3 다운로드 도중 에러 발생: "+e.getMessage(), e);
		}
	}

	@Override
	public DiaryDto.Response downloadDiary(String objectUrl, String encodePassword) {
		try {
			ResponseInputStream<GetObjectResponse> diaryObject = s3Client.getObject(
				getGetObjectRequest(objectUrl, encodePassword)
			);
			byte[] bytes = diaryObject.readAllBytes();

			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			DiaryDto.Create diary = (DiaryDto.Create) ois.readObject();
			return diary.toDiaryResponseDto();

		} catch (Exception e) {
			log.error("cause : {}",e.getCause(),e);
			throw new S3Exception("S3 다운로드 도중 에러 발생: "+e.getMessage(), e);
		}
	}

	@Override
	public DiaryDto.Response downloadDiary(String objectUrl) {
		try {
			ResponseInputStream<GetObjectResponse> diaryObject = s3Client.getObject(
				getGetObjectRequest(objectUrl)
			);
			byte[] bytes = diaryObject.readAllBytes();

			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			DiaryDto.Create diary = (DiaryDto.Create) ois.readObject();
			return diary.toDiaryResponseDto();

		} catch (Exception e) {
			throw new S3Exception("S3 다운로드 도중 에러 발생: "+e.getMessage(), e);
		}
	}

	private GetObjectRequest getGetObjectRequest(String objectUrl, String encodePassword) throws NoSuchAlgorithmException {
		String encodeAlgorithm = "AES256";
		String[] strings = objectUrl.split(BUCKET_NAME+"/");
		String objectKey = strings[1];
		log.debug("[getGetObjectRequest] objectKey: {}", objectKey);

		byte[] keyBytes = convertAES256ValidKey(encodePassword);
		return GetObjectRequest.builder()
			.bucket(BUCKET_NAME)
			.key(objectKey)
			.sseCustomerKey(BinaryUtils.toBase64(keyBytes))
			.sseCustomerKeyMD5(Md5Utils.md5AsBase64(keyBytes))
			.sseCustomerAlgorithm(encodeAlgorithm)
			.build();
	}

	private GetObjectRequest getGetObjectRequest(String objectUrl){
		String[] strings = objectUrl.split(BUCKET_NAME+"/");
		String objectKey = strings[1];
		log.debug("[getGetObjectRequest] objectKey: {}", objectKey);
		return GetObjectRequest.builder()
			.bucket(BUCKET_NAME)
			.key(objectKey)
			.build();
	}

	private PutObjectRequest getPutObjectRequest(String objectPath, String encodePassword) throws NoSuchAlgorithmException {
		String encodeAlgorithm = "AES256";
		byte[] keyBytes = convertAES256ValidKey(encodePassword);
		return PutObjectRequest.builder()
			.bucket(BUCKET_NAME)
			.key(objectPath)
			.sseCustomerKey(BinaryUtils.toBase64(keyBytes))
			.sseCustomerKeyMD5(Md5Utils.md5AsBase64(keyBytes))
			.sseCustomerAlgorithm(encodeAlgorithm)
			.build();
	}

	private PutObjectRequest getPutObjectRequest(String objectPath) throws NoSuchAlgorithmException {
		return PutObjectRequest.builder()
			.bucket(BUCKET_NAME)
			.key(objectPath)
			.build();
	}

	@NotNull
	private static byte[] convertAES256ValidKey(String encodePassword) throws NoSuchAlgorithmException {
		byte[] keyBytes = encodePassword.getBytes(StandardCharsets.UTF_8);
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		keyBytes = sha.digest(keyBytes);
		keyBytes = Arrays.copyOf(keyBytes, 32);
		return keyBytes;
	}
}
