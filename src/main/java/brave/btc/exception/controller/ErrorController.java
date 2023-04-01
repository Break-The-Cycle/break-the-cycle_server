package brave.btc.exception.controller;

import brave.btc.domain.User;
import brave.btc.exception.AuthenticationInvalidException;
import brave.btc.exception.ErrorResult;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> invalidLoginExHandle(AuthenticationInvalidException e) {
        log.error("[아이디/비밀번호 오류]", e);
        ErrorResult errorResult = new ErrorResult("LOGIN-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }



}
