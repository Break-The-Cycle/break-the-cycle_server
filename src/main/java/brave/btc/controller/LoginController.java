package brave.btc.controller;

import brave.btc.domain.User;
import brave.btc.dto.login.LoginRequestDto;
import brave.btc.dto.register.RegisterRequestDto;
import brave.btc.dto.register.RegisterResponseDto;
import brave.btc.exception.DuplicateIdException;
import brave.btc.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "01. Auth", description = "회원가입/로그인")
@RequestMapping("/v1/auth")
public class LoginController {

    private final UserService userService;

    @Operation(summary = "register", description = "회원 가입 폼 제출",
    responses = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공 -> 응답 에러 모두 false"),
            @ApiResponse(responseCode = "400", description = "회원 가입 실패 -> 응답 에러 중 적어도 하나가 true")
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerV1(BindingResult bindingResult,
        @RequestBody @Valid RegisterRequestDto request) {
        return userService.registerCheck(request);
    }

    @Operation(summary = "ID Duplication Check", description = "아이디 중복 체크",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용 가능한 아이디"),
                    @ApiResponse(responseCode = "400", description = "이미 존재하는 아이디")
            })
    @GetMapping("/register/dupCheck")
    public ResponseEntity<?> dupCheckV1(@RequestBody String loginId) {
        try {
            userService.idDuplicateCheck(loginId);
            return ResponseEntity.ok()
                    .body("Usable Login Id");
        } catch (DuplicateIdException e) {
            return ResponseEntity.badRequest()
                    .body("Duplicate Login Id");
        }
    }

    @Operation(summary = "Login", description = "로그인 ID PW 확인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "400", description = "로그인 실패")
            })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping ("/login")
    public void loginV1(@RequestBody LoginRequestDto request) {
        userService.login(request.getId(), request.getPassword());
        log.info("로그인 성공. userId = {}", request.getId());
    }
}
