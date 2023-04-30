package brave.btc.service.record;

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

import brave.btc.dto.record.DiaryDto;
import io.awspring.cloud.s3.S3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.utils.BinaryUtils;
import software.amazon.awssdk.utils.Md5Utils;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AWSS3ServiceImpl implements AWSS3Service {

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	private final String S3_BUCKET_PREFIX_URL = "s3://break-the-cycle-use-person-record";
	private final S3Client s3Client;

	@Override
	public String uploadPicture(MultipartFile multipartFile, String objectPath, String encodePassword) {

		try {
			log.debug("[uploadPicture] multipartFile: {}, objectPath: {}, encodePassword: {}", multipartFile, objectPath, encodePassword);


			s3Client.putObject(
				getPutObjectRequest(objectPath, encodePassword),
				RequestBody.fromInputStream(multipartFile.getInputStream(),multipartFile.getSize()));
		} catch (Exception e) {
			throw new S3Exception("S3 업로드 도중 에러 발생: "+e.getMessage(),e);
		}
		return S3_BUCKET_PREFIX_URL+objectPath;
	}

	@Override
	public String uploadDiary(DiaryDto diaryDto, String objectPath, String encodePassword) {

		try {
			s3Client.putObject(
				getPutObjectRequest(objectPath, encodePassword),
				RequestBody.fromBytes(
					Objects.requireNonNull(SerializationUtils.serialize(diaryDto))
				));
		} catch (Exception e) {
			throw new S3Exception("S3 업로드 도중 에러 발생: "+e.getMessage(), e);
		}
		return S3_BUCKET_PREFIX_URL+objectPath;
	}

	private PutObjectRequest getPutObjectRequest(String objectPath, String encodePassword) throws NoSuchAlgorithmException {
		String encodeAlgorithm = "AES256";
		String fullPath = bucketName + objectPath;
		byte[] keyBytes = convertAES256ValidKey(encodePassword);
		return PutObjectRequest.builder()
			.bucket(bucketName)
			.key(fullPath)
			.sseCustomerKey(BinaryUtils.toBase64(keyBytes))
			.sseCustomerKeyMD5(Md5Utils.md5AsBase64(keyBytes))
			.sseCustomerAlgorithm(encodeAlgorithm)
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
