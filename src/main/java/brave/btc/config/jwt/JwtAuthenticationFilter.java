package brave.btc.config.jwt;

import java.io.IOException;
import java.util.Date;

import brave.btc.dto.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import brave.btc.config.auth.PrincipalDetails;
import brave.btc.domain.common.user.User;
import brave.btc.dto.common.auth.jwt.JwtTokenDto;
import brave.btc.dto.common.auth.login.LoginRequestDto;
import brave.btc.service.app.auth.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// "/login" 요청에서 username, password 전송 시 동작

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtServiceImpl jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        setFilterProcessesUrl("/v1/auth/login");
    }

    //login 요청을 하면 로그인 시도를 위해 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("[Authentication] 로그인 시도 중");

        //username(loginId) password 받아서 로그인 시도
        try {
            ObjectMapper om = new ObjectMapper();
            LoginRequestDto user = om.readValue(request.getInputStream(), LoginRequestDto.class);
            log.info("[Authentication] loginRequest = " + user);
            //PrincipalDetailsService 함수 실행
            try {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getLoginId(), user.getPassword());
                Authentication authentication = authenticationManager.authenticate(authenticationToken);
                PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
                log.info("[Authentication] 인증된 유저 = " + principalDetails.getUser());
                return authentication;
            } catch (BadCredentialsException e) {
                throw new JwtException("로그인에 실패하였습니다.");
            }
            //authentication을 세션 영역에 저장 -> 인증에 성공
        } catch (IOException e) {
            e.printStackTrace();
        }
        //AuthenticationManager로 로그인 시도를 하면
        //PrincipalDetailsService 호출 함수 실행
        //PrincipalDetails를 세션에 담고(세션에 담아야 시큐리티가 권한 관리를 할 수 있음)
        //JWT토큰을 만들어서 응답

        return null;
    }

    //인증 완료 시 실행
    //JWT 토큰을 만들어서 사용자에게 리턴
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("==========로그인 완료=========");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        User loginSuccessUser = principalDetails.getUser();
        String accessToken = JWT.create()
                //토큰 이름
                .withSubject("accessToken")
                //만료시간
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.AT_EXP_TIME))
                //비공개 값
                .withClaim("id", loginSuccessUser.getId())
                .withClaim("loginId", loginSuccessUser.getLoginId())
                //암호화 방식
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        log.info("[Authentication] accessToken 생성 완료: " + accessToken);

        String refreshToken = JWT.create()
                //토큰 이름
                .withSubject("refreshToken")
                //만료시간
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.RT_EXP_TIME))
                //비공개 값
                .withClaim("id", loginSuccessUser.getId())
                //암호화 방식
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        log.info("[Authentication] refreshToken 생성 완료: " + refreshToken);

        jwtService.updateRefreshToken(refreshToken, loginSuccessUser.getId());
        log.info("[Authentication] refreshToken DB에 저장 완료: " + refreshToken);

        JwtTokenDto jwtTokenDto = JwtTokenDto.builder()
                .usePersonId(loginSuccessUser.getId())
                .accessToken(JwtProperties.TOKEN_PREFIX+accessToken)
                .refreshToken(JwtProperties.TOKEN_PREFIX+refreshToken)
                .build();

        CommonResponseDto<Object> responseDto = CommonResponseDto.builder()
                .data(jwtTokenDto)
                .message("로그인에 성공하였습니다.")
                .statusCode(HttpStatus.OK.value())
                .build();
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        mapper.writeValue(response.getOutputStream(), responseDto);
    }

}
