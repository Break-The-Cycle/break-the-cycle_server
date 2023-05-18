package brave.btc.exception.controller;

import brave.btc.dto.common.auth.jwt.JwtResponseDto;
import brave.btc.exception.ErrorResponseDto;
import brave.btc.exception.auth.*;
import brave.btc.exception.sms.SmsCertificationNumberExpiredException;
import brave.btc.exception.sms.SmsCertificationNumberNotSameException;
import brave.btc.exception.sms.SmsSendFailedException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto<?>> handleException(Exception e) {

        log.error("[handleException] 특정 되지 않은 예외={}", e.getClass());

        ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
                .message(e.getMessage())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseEntity.internalServerError()
                .body(errorResponseDto);
    }

    @ExceptionHandler(AuthenticationInvalidException.class)
    public ResponseEntity<ErrorResponseDto<?>> handleAuthenticationInvalidException(AuthenticationInvalidException e) {

        log.error("[handleAuthenticationInvalidException] 로그인 실패 예외");
        ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
                .message(e.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponseDto);
    }

    @ExceptionHandler(UserPrincipalNotFoundException.class)
    public ResponseEntity<ErrorResponseDto<?>> handleUserPrincipalNotFoundException(UserPrincipalNotFoundException e) {

        log.error("[handleUserPrincipalNotFoundException] 회원 존재하지 않음");
        ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
                .message(e.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponseDto);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<JwtResponseDto> handleInvalidTokenException(JWTVerificationException e) {

        log.error("[handleJWTVerificationException] 유효하지 않은 토큰입니다.");
        JwtResponseDto errorResponseDto = JwtResponseDto.builder()
                .message(e.getMessage())
                .code(HttpStatus.UNAUTHORIZED.value())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorResponseDto);
    }

    @ExceptionHandler(SmsSendFailedException.class)
    public ResponseEntity<ErrorResponseDto<?>> SmsSendFailedException(SmsSendFailedException e) {

        log.error("[handleSmsSendFailedException] 인증 번호 전송 실패");
        ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
                .message(e.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest()
                .body(errorResponseDto);
    }

    @ExceptionHandler(SmsCertificationNumberNotSameException.class)
    public ResponseEntity<ErrorResponseDto<?>> handleSmsCertificationNumberNotSameException(SmsCertificationNumberNotSameException e) {

        log.error("[handleSmsCertificationNumberNotSameException] 인증 번호 불일치");
        ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
                .message(e.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest()
                .body(errorResponseDto);
    }

    @ExceptionHandler(SmsCertificationNumberExpiredException.class)
    public ResponseEntity<ErrorResponseDto<?>> handleSmsCertificationNumberExpiredException(SmsCertificationNumberExpiredException e) {

        log.error("[handleSmsCertificationNumberExpiredException] 인증 번호 만료");
        ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
                .message(e.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest()
                .body(errorResponseDto);
    }


}
