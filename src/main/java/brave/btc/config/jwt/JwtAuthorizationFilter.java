package brave.btc.config.jwt;

import brave.btc.config.auth.PrincipalDetails;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.repository.app.UsePersonRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

//시큐리티 필터 중 BasicAuthentication 필터가 있음
//권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터에 들어감
//그렇지 않으면 통과함
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UsePersonRepository usePersonRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UsePersonRepository usePersonRepository) {
        super(authenticationManager);
        this.usePersonRepository = usePersonRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String atHeader = request.getHeader(JwtProperties.AT_HEADER);
        System.out.println("request = " + request.getServletPath());
        //토큰 헤더가 없다면 통과
        if (atHeader == null || !request.getServletPath().startsWith("/v1/auth/user")) {
            chain.doFilter(request, response);
            return;
        } else {
            //유효하지 않은 토큰 체크
            if (!atHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
                throw new JwtException("유효하지 않은 AccessToken입니다.");
            }
        }

        String atToken = request.getHeader(JwtProperties.AT_HEADER).replace(JwtProperties.TOKEN_PREFIX, "");
        log.info("atToken= {}", atToken);
        //서명 확인
        try {
            //Access Token 토큰 유효성 검사
            log.info("[Authorization] Access Token 유효성 검사 시작");
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build()
                    .verify(atToken);
            String phoneNumber = decodedJWT.getClaim("phoneNumber").asString();

            usePersonRepository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new JWTVerificationException("유효하지 않은 Access Token 입니다."));
            log.info("[Authorization] Access Token 유효성 검사 통과");
            log.info("phoneNumber= {}", phoneNumber);

            //서명 통과
            log.info("[Authorization] 서명 통과");
            UsePerson userEntity = usePersonRepository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new JwtException("서명과 일치하는 사용자가 없습니다."));
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

            //JWT 토큰 서명을 통해 서명이 정상이면 Authentication 객체를 만들어준다
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            //강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (TokenExpiredException e) {
            log.info("[Authorization] Access Token 만료");
            throw new JwtException("Access Token이 만료되었습니다.");
        } catch (JWTVerificationException e) {
            log.info("[Authorization] 유효하지 않은 Access Token");
            throw new JwtException("유효하지 않은 Access Token입니다.");
        }


        chain.doFilter(request, response);

    }
}
