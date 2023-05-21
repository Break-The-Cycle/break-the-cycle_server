package brave.btc.controller.common.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.common.auth.jwt.JwtResponseDto;
import brave.btc.dto.common.auth.login.LoginRequestDto;
import brave.btc.dto.common.auth.register.RegisterDto;
import brave.btc.exception.ErrorResponseDto;
import brave.btc.service.app.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "01-1. COMMON_Auth", description = "회원가입/로그인")
@Slf4j
@Valid
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login ID Duplication Check", description = "아이디 중복 체크",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용 가능한 아이디",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "이미 존재하는 아이디",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponseDto.class)))
            })
    @GetMapping("/duplicate-check/{loginId}")
    public ResponseEntity<?> dupCheckV1(
            @Parameter(description = "중복 확인할 아이디")
            @Pattern(regexp = "^[a-z]+[a-zA-Z0-9]{6,19}", message = "아이디는 영문 소문자로 시작하고 숫자를 포함하여 7~20자로 구성되어야 합니다.")
            @PathVariable("loginId") String loginId) {

        CommonResponseDto<Object> responseDto = authService.loginIdIdDuplicateCheck(loginId);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(responseDto);
    }

    @Operation(summary = "use person register", description = " 사용 개인 회원 가입",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원 가입 성공",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "회원 가입 실패",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    @PostMapping("/register/use-person")
    public ResponseEntity<?> registerUsePersonV1(
            @RequestBody @Valid RegisterDto.UsePersonCreate request,
            BindingResult bindingResult) {
        CommonResponseDto<Object> responseDto = authService.registerUsePerson(request);
        return ResponseEntity.ok()
                .body(responseDto);
    }

    @Operation(summary = "manage person register", description = "관리 개인 회원 가입",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원 가입 성공",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "회원 가입 실패",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    @PostMapping("/register/manage-person")
    public ResponseEntity<?> registerManagePersonV1(
            @RequestBody @Valid RegisterDto.ManagePersonCreate request,
            BindingResult bindingResult) {
        CommonResponseDto<Object> responseDto = authService.registerManagePerson(request);
        return ResponseEntity.ok()
                .body(responseDto);
    }

    @Operation(summary = "bo manage person register", description = "백 오피스 관리자 개인 회원 가입(ADMIN)",
        responses = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "회원 가입 실패",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDto.class)))
        })
    @PostMapping("/register/bo-manage-person")
    public ResponseEntity<?> registerAdminPersonV1(
        @RequestBody @Valid RegisterDto.BackOfficeManagePersonCreate request,
        BindingResult bindingResult) {
        CommonResponseDto<Object> responseDto = authService.registerBackOffIceManagePerson(request);
        return ResponseEntity.ok()
            .body(responseDto);
    }


    @Operation(summary = "Login", description = "로그인 ID PW 확인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = JwtResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "로그인 실패",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = JwtResponseDto.class)))
            })
    @PostMapping("/login")
    public void loginV1(
            @RequestBody @Valid LoginRequestDto request) {
    }
}
