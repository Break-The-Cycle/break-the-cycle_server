package brave.btc.exception.controller;

import brave.btc.exception.ErrorResponseDto;
import brave.btc.exception.auth.AuthenticationInvalidException;
import brave.btc.exception.auth.SmsCertificationNumberExpiredException;
import brave.btc.exception.auth.SmsCertificationNumberNotSameException;
import brave.btc.exception.auth.UserPrincipalNotFoundException;
import lombok.extern.slf4j.Slf4j;

import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto<?>> handleException(Exception e) {

        log.error("[handleException] 특정 되지 않은 예외");
        ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
            .message(e.getMessage())
            .build();
        return ResponseEntity.internalServerError()
            .body(errorResponseDto);
    }

    @ExceptionHandler(AuthenticationInvalidException.class)
        public ResponseEntity<ErrorResponseDto<?>> handleAuthenticationInvalidException(AuthenticationInvalidException e) {

        log.error("[handleAuthenticationInvalidException] 로그인 실패 예외");
        ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
            .message(e.getMessage())
            .build();
        return ResponseEntity.badRequest()
            .body(errorResponseDto);
    }
    @ExceptionHandler(UserPrincipalNotFoundException.class)
    public ResponseEntity<ErrorResponseDto<?>> handleUserPrincipalNotFoundException(UserPrincipalNotFoundException e) {

        log.error("[handleUserPrincipalNotFoundException] 회원 존재하지 않음");
        ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
            .message(e.getMessage())
            .build();
        return ResponseEntity.badRequest()
            .body(errorResponseDto);
    }

    @ExceptionHandler(NurigoMessageNotReceivedException.class)
    public ResponseEntity<ErrorResponseDto<?>> handleNurigoMessageNotReceivedException(NurigoMessageNotReceivedException e) {

        log.error("[handleNurigoMessageNotReceivedException] 인증 번호 전송 실패");
        ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest()
                .body(errorResponseDto);
    }

    @ExceptionHandler(SmsCertificationNumberNotSameException.class)
    public ResponseEntity<ErrorResponseDto<?>> handleSmsCertificationNumberNotSameException(SmsCertificationNumberNotSameException e) {

        log.error("[handleSmsCertificationNumberNotSameException] 인증 번호 불일치");
        ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest()
                .body(errorResponseDto);
    }

    @ExceptionHandler(SmsCertificationNumberExpiredException.class)
    public ResponseEntity<ErrorResponseDto<?>> handleSmsCertificationNumberExpiredException(SmsCertificationNumberExpiredException e) {

        log.error("[handleSmsCertificationNumberExpiredException] 인증 번호 만료");
        ErrorResponseDto<Object> errorResponseDto = ErrorResponseDto.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest()
                .body(errorResponseDto);
    }

}
