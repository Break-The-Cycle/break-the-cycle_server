package brave.btc.controller.app.auth;

import brave.btc.config.jwt.JwtProperties;
import brave.btc.exception.ErrorResponseDto;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import brave.btc.dto.app.auth.login.LoginRequestDto;
import brave.btc.dto.app.auth.register.RegisterRequestDto;
import brave.btc.service.app.auth.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Tag(name = "01-1. Auth", description = "회원가입/로그인")
@Slf4j
@Valid
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

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

        CommonResponseDto<Object> responseDto = authServiceImpl.loginIdIdDuplicateCheck(loginId);
        return ResponseEntity.ok()
                .body(responseDto);
    }

    @Operation(summary = "register", description = "회원 가입 폼 제출",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원 가입 성공",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "회원 가입 실패",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    @PostMapping("/register")
    public ResponseEntity<?> registerV1(
            @RequestBody @Valid RegisterRequestDto request,
            BindingResult bindingResult) {
        CommonResponseDto<Object> responseDto = authServiceImpl.register(request);
        return ResponseEntity.ok()
                .body(responseDto);
    }

    @Operation(summary = "Login", description = "로그인 ID PW 확인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "로그인 실패",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    @PostMapping("/login")
    public ResponseEntity<?> loginV1(
            @RequestBody @Valid LoginRequestDto request) {
        CommonResponseDto<Object> responseDto = CommonResponseDto.builder()
                .message("로그인에 성공하였습니다.")
                .build();
        return ResponseEntity.ok()
                .body(responseDto);
    }

    @Operation(summary = "Access Token Validation", description = "Access Token 유효성 검사(30초)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유효성 통과",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "유효하지 않음",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    @GetMapping("/user")
    public ResponseEntity<?> user() {
        CommonResponseDto<Object> responseDto = CommonResponseDto.builder()
                .message("Access Token으로 진입 완료.")
                .build();
        return ResponseEntity.ok()
                .body(responseDto);
    }

    @Operation(summary = "Refresh Token Validation", description = "Refresh Token 유효성 검사",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유효성 통과",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommonResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "유효하지 않음",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    @Parameter(name = "Refresh-Token", in = ParameterIn.HEADER)
    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader(JwtProperties.RT_HEADER);
        Map<String, String> tokens = authServiceImpl.refresh(refreshToken);
        String newAccessToken = tokens.get("accessToken");
        String newRefreshToken = tokens.get("refreshToken");
        response.addHeader("Authorization", newAccessToken);
        response.addHeader("Refresh-Token", newRefreshToken);
        CommonResponseDto<Object> responseDto = CommonResponseDto.builder()
                .message("Access Token, Refresh 토큰이 갱신되었습니다.")
                .build();
        return ResponseEntity.ok()
                .body(responseDto);
    }


}
