package brave.btc.dto.app.auth.jwt;

import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDto {

    private String accessToken;
    private String refreshToken;
    private String message;
    private int code;

}
