package brave.btc.controller;

import brave.btc.dto.login.LoginRequestDto;
import brave.btc.dto.register.RegisterRequestDto;
import brave.btc.dto.register.RegisterResponseDto;
import brave.btc.exception.auth.DuplicateLoginIdException;
import brave.btc.service.UserService;
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
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class LoginController {

    private final UserService userService;

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

        try {
            userService.idDuplicateCheck(loginId);
            return ResponseEntity.ok()
                .body("Usable Login Id");
        } catch (DuplicateLoginIdException e) {
            return ResponseEntity.badRequest()
                .body("Duplicate Login Id");
        }
    }

    @Operation(summary = "register", description = "회원 가입 폼 제출",
    responses = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원 가입 실패")
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerV1(
        @RequestBody @Valid RegisterRequestDto request,
        BindingResult bindingResult) {
        return userService.registerCheck(request);
    }

    @Operation(summary = "Login", description = "로그인 ID PW 확인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "400", description = "로그인 실패")
            })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping ("/login")
    public void loginV1(@RequestBody LoginRequestDto request) {
        userService.login(request.getLoginId(), request.getPassword());
        log.info("로그인 성공. userId = {}", request.getLoginId());
    }
}
