package brave.btc.exception.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import brave.btc.exception.ErrorResponseDto;
import brave.btc.exception.menstruation.InvalidMenstruationInfoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class MenstruationErrorController {


	@ExceptionHandler(InvalidMenstruationInfoException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleInvalidMenstruationDateException(InvalidMenstruationInfoException e) {

		log.error("[handleInvalidMenstruationDateException] 생리 정보 이상");
		ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
			.message(e.getMessage())
			.build();
		return ResponseEntity.badRequest()
			.body(errorResponseDto);
	}


}
