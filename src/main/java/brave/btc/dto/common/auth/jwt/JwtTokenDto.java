package brave.btc.dto.common.auth.jwt;

import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtTokenDto {

    private String accessToken;
    private String refreshToken;

}
