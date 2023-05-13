package brave.btc.service.app.auth;

import brave.btc.config.jwt.JwtProperties;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.domain.temporary.jwt.RefreshToken;
import brave.btc.dto.app.auth.jwt.JwtResponseDto;
import brave.btc.repository.app.UsePersonRepository;
import brave.btc.repository.temporary.jwt.RefreshTokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private final UsePersonRepository usePersonRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void updateRefreshToken(String refreshToken, Integer userId) {
        RefreshToken findToken = refreshTokenRepository.findById(userId)
                .orElse(RefreshToken.builder()
                        .token(refreshToken)
                        .id(userId)
                        .build());
        findToken.changeToken(refreshToken);
    }

    @Override
    public JwtResponseDto refresh(String refreshToken) {
        log.info("[Authorization] Refresh Token 유효성 검사");
        //refresh 토큰 유효성 검사
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build()
                .verify(refreshToken);

        Integer userId = decodedJWT.getClaim("id").asInt();

        System.out.println("userId = " + userId);
        System.out.println("refreshToken = " + refreshToken);
        UsePerson usePerson = usePersonRepository.findById(userId)
                .orElseThrow(() -> new JWTVerificationException("유효하지 않은 Refresh Token 입니다."));
        log.info("[Authorization] refresh 토큰 유효성 검사 완료");

        //Access Token 재발급
        log.info("[Authorization] Access Token 재발급");
        String accessToken = JWT.create()
                //토큰 이름
                .withSubject("accessToken")
                //만료시간
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.AT_EXP_TIME))
                //비공개 값
                .withClaim("id", usePerson.getId())
                .withClaim("phoneNumber", usePerson.getPhoneNumber())
                //암호화 방식
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        log.info("[Authorization] Access Token 재발급 완료");

        //Refresh Token 재발급
        log.info("[Authorization] Refresh Token 재발급");
        String newRefreshToken = JWT.create()
                //토큰 이름
                .withSubject("refreshToken")
                //만료시간
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.RT_EXP_TIME))
                //비공개 값
                .withClaim("id", usePerson.getId())
                //암호화 방식
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        log.info("[Authorization] Refresh Token 재발급 완료");

        updateRefreshToken(newRefreshToken, userId);
        log.info("[Authorization] Refresh Token DB에 저장 완료");

        return JwtResponseDto.builder()
                .accessToken(JwtProperties.TOKEN_PREFIX + accessToken)
                .refreshToken(JwtProperties.TOKEN_PREFIX + newRefreshToken)
                .message("Access Token, Refresh 토큰이 갱신되었습니다.")
                .code(HttpStatus.OK.value())
                .build();
    }
}
