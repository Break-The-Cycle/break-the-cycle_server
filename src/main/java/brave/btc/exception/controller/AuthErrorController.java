package brave.btc.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import brave.btc.exception.ErrorResponseDto;
import brave.btc.exception.auth.LoginIdDuplicateException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class AuthErrorController {


	@ExceptionHandler(LoginIdDuplicateException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleInvalidLoginIdDuplicateException(LoginIdDuplicateException e) {

		log.error("[handleInvalidLoginIdDuplicateException] 로그인 아이디 중복 에러");
		ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.CONFLICT)
			.body(errorResponseDto);
	}


}
