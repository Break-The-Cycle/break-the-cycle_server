package brave.btc.exception.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import brave.btc.exception.ErrorResponseDto;
import io.awspring.cloud.s3.S3Exception;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class FileUploadErrorController {


	@ExceptionHandler(S3Exception.class)
	public ResponseEntity<ErrorResponseDto<?>> handleS3Exception(S3Exception e) {

		log.error("[handleS3Exception] S3 업로드 도중 에러 발생");
		ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
			.message(e.getMessage())
			.build();
		return ResponseEntity.internalServerError()
			.body(errorResponseDto);
	}

}
