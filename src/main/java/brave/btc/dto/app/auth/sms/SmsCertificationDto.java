package brave.btc.dto.app.auth.sms;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "인증번호 확인", description = "인증번호를 확인한다.")
public class SmsCertificationDto {

    @Schema(title = "휴대폰 번호", example = "01012345678")
    @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$",
            message = "휴대전화 형식이 올바르지 않습니다.")
    private String phoneNumber;

    @Schema(title = "입력한 인증 번호 6자리", example = "123456")
    @Pattern(regexp = "\\d{6}",
            message = "인증번호 형식이 올바르지 않습니다.")
    private String certificationNumber;

}
