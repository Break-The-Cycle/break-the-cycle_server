package brave.btc.controller;

import brave.btc.domain.User;
import brave.btc.dto.login.LoginRequestDto;
import brave.btc.dto.register.RegisterRequestDto;
import brave.btc.dto.register.RegisterResponseDto;
import brave.btc.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "auth", description = "회원가입/로그인")
@RequestMapping("/v1/auth")
public class LoginController {

    private final UserService userService;

    @Operation(summary = "register", description = "회원 가입")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerV1(@RequestBody @Valid RegisterRequestDto request, BindingResult bindingResult) {
        return userService.registerCheck(request, bindingResult);
    }

    @Operation(summary = "ID Duplication Check", description = "아이디 중복 체크")
    @GetMapping("/register/dupCheck")
    public ResponseEntity<?> dupCheckV1(@RequestBody String loginId) {
        if (userService.idDuplicateCheck(loginId)) {
            return ResponseEntity.badRequest()
                    .body("Duplicate Login Id");
        }
        return ResponseEntity.ok()
                .body("Usable Login Id");

    }

    @Operation(summary = "Login", description = "로그인 ID PW 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping ("/login")
    public void loginV1(@RequestBody LoginRequestDto request) {
        userService.login(request.getId(), request.getPassword());
        log.info("로그인 성공. userId = {}", request.getId());
    }



}
