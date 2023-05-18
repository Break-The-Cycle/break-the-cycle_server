package brave.btc.dto.common.auth.sms;

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
@Schema(title = "인증번호 요청", description = "인증번호를 요청한다.")
public class SmsRequestDto {

    @Schema(title = "휴대폰 번호", example = "01012345678")
    @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$",
            message = "휴대전화 형식이 올바르지 않습니다.")
    private String phoneNumber;

}
