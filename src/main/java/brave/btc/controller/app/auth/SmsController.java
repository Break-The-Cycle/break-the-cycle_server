package brave.btc.controller.app.auth;

import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.auth.sms.SmsCertificationDto;
import brave.btc.dto.app.auth.sms.SmsRequestDto;
import brave.btc.service.app.auth.SmsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "01-2. Sms-Certification", description = "인증번호 전송/확인")
@Slf4j
@Valid
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/sms-certification")
public class SmsController {

    private final SmsServiceImpl smsServiceImpl;

    @Operation(summary = "Sms Certification Send", description = "인증번호 요청",
            responses = {
                    @ApiResponse(responseCode = "200", description = "인증번호 요청 성공"),
                    @ApiResponse(responseCode = "400", description = "인증번호 요청 실패")
            })
    @PostMapping("/sms-certification/send")
    public ResponseEntity<?> sendSms(@RequestBody SmsRequestDto request){
        CommonResponseDto<Object> responseDto = smsServiceImpl.sendAuthNumber(request.getPhoneNumber());
        return ResponseEntity.ok()
                .body(responseDto);
    }

    @Operation(summary = "Sms Certification Confirm", description = "인증번호 확인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "인증번호 일치"),
                    @ApiResponse(responseCode = "400", description = "인증번호 불일치")
            })
    @PostMapping("/sms-certification/confirm")
    public ResponseEntity<?> smsCertification(@RequestBody SmsCertificationDto request) {
        CommonResponseDto<Object> responseDto = smsServiceImpl.checkAuthNumber(request.getCertificationNumber(), request.getPhoneNumber());
        return ResponseEntity.ok()
                .body(responseDto);
    }


}



