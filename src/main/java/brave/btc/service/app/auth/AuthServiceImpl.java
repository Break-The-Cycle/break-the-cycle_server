package brave.btc.service.app.auth;

import brave.btc.config.jwt.JwtProperties;
import brave.btc.domain.temporary.jwt.RefreshToken;

import brave.btc.exception.auth.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brave.btc.domain.app.user.UsePerson;
import brave.btc.dto.CommonResponseDto;
import brave.btc.dto.app.auth.register.RegisterRequestDto;
import brave.btc.repository.app.UsePersonRepository;
import brave.btc.repository.temporary.jwt.RefreshTokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UsePersonRepository usePersonRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UsePerson checkIsPasswordEqual(String loginId, String rawPassword) {
        UsePerson usePerson = usePersonRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserPrincipalNotFoundException("해당하는 유저를 찾을 수 없습니다."));

        //비밀번호 확인 로직
        //보통 DB에는 해시된 값이 들어가 있기 때문에 rawPassword를 해싱한 후에 비교해봄
        String encPassword = usePerson.getPassword();

        if (rawPassword.equals(encPassword)) {
            log.info("[checkIsPasswordEqual] 비밀번호 일치");
            return usePerson;
        }
        log.error("[checkIsPasswordEqual] 비밀번호 불일치 에러");
        throw new AuthenticationInvalidException("비밀번호가 일치하지 않습니다.");
    }

    @Override
    @Transactional(readOnly = true)
    public CommonResponseDto<Object> loginIdIdDuplicateCheck(String loginId) {

        UsePerson user = usePersonRepository.findByLoginId(loginId)
                .orElse(null);

        if (user == null) {
            return CommonResponseDto.builder()
                    .message("사용 가능한 아이디입니다.")
                    .code(HttpStatus.OK.value())
                    .build();
        }
        return CommonResponseDto.builder()
                .message("이미 사용중인 아이디입니다.")
                .code(HttpStatus.OK.value())
                .build();
    }


    @Override
    public CommonResponseDto<Object> register(RegisterRequestDto request) {

        log.debug("[register] request: {}", request);
        //비밀번호 매칭 확인
        String password = request.getPassword();
        String password2 = request.getPassword2();
        if (password.equals(password2)) {
            //TODO : MapStruct 도입하기 ... (나중에 필드 많아지면 훨씬 편함) DTO <-> Entity Mapper

            UsePerson newUsePerson = UsePerson.builder()
                    .loginId(request.getLoginId())
                    .password(bCryptPasswordEncoder.encode(password))
                    .name(request.getName())
                    .phoneNumber(request.getPhoneNumber())
                    .build();
            usePersonRepository.save(newUsePerson);
            log.info("[register] 회원 가입 완료");
            return CommonResponseDto.builder()
                    .message("회원 가입이 완료되었습니다.")
                    .code(HttpStatus.OK.value())
                    .build();
        }
        log.error("[register] 회원 가입 실패");
        throw new AuthenticationInvalidException("비밀번호와 확인 비밀번호가 일치하지 않습니다.");
    }

    public void updateRefreshToken(String refreshToken, Integer userId) {
        RefreshToken findToken = refreshTokenRepository.findById(userId)
                .orElse(RefreshToken.builder()
                        .token(refreshToken)
                        .id(userId)
                        .build());
        findToken.changeToken(refreshToken);
    }

    public Map<String, String> refresh(String refreshToken) {
        log.info("[Authorization] refresh 토큰 유효성 검사");
        refreshToken = refreshToken.replace(JwtProperties.TOKEN_PREFIX, "");

        //refresh 토큰 유효성 검사
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build()
                .verify(refreshToken);

        Integer userId = decodedJWT.getClaim("id").asInt();

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
        Map<String, String> token = new HashMap<>();
        token.put("accessToken", JwtProperties.TOKEN_PREFIX + accessToken);
        token.put("refreshToken", JwtProperties.TOKEN_PREFIX + newRefreshToken);

        return token;
    }
}
