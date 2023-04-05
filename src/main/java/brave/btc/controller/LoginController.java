package brave.btc.controller;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.auth.login.LoginRequestDto;
import brave.btc.dto.auth.register.RegisterRequestDto;
import brave.btc.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "01. Auth", description = "회원가입/로그인")
@Slf4j
@Valid
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class LoginController {

    private final AuthService authService;

    @Operation(summary = "Login ID Duplication Check", description = "아이디 중복 체크",
        responses = {
            @ApiResponse(responseCode = "200", description = "사용 가능한 아이디"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 아이디")
        })
    @GetMapping("/duplicate-check/{loginId}")
    public ResponseEntity<?> dupCheckV1(
        @Parameter(description = "중복 확인할 아이디")
        @Pattern(regexp = "^[a-z]+[a-zA-Z1-9]{6,20}",message = "아이디는 영문 소문자로 시작하고 숫자를 포함하여 7~20자로 구성되어야 합니다.")
        @PathVariable("loginId") String loginId) {

        CommonResponseDto<Object> responseDto = authService.loginIdIdDuplicateCheck(loginId);
        return ResponseEntity.ok()
                .body(responseDto);
    }

    @Operation(summary = "register", description = "회원 가입 폼 제출",
    responses = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원 가입 실패")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerV1(
        @RequestBody @Valid RegisterRequestDto request,
        BindingResult bindingResult) {
        CommonResponseDto<Object> responseDto = authService.register(request);
        return ResponseEntity.ok()
            .body(responseDto);
    }

    @Operation(summary = "Login", description = "로그인 ID PW 확인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "400", description = "로그인 실패")
            })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping ("/login")
    public void loginV1(@RequestBody LoginRequestDto request) {
        authService.login(request.getLoginId(), request.getPassword());
        log.info("로그인 성공. userId = {}", request.getLoginId());
    }
}
