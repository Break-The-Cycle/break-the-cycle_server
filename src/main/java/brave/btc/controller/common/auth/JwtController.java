package brave.btc.controller.common.auth;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import brave.btc.dto.CommonResponseDto;
import brave.btc.exception.ErrorResponseDto;
import brave.btc.service.app.auth.JwtServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "01-3. COMMON_JWT", description = "JWT 검증")
@Slf4j
@Valid
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class JwtController {

    private final JwtServiceImpl jwtService;

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
    public void refresh(HttpServletRequest request) {
;
    }
}
