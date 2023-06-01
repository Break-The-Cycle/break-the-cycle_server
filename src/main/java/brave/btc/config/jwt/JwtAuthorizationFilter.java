package brave.btc.config.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import brave.btc.config.auth.PrincipalDetails;
import brave.btc.domain.app.user.UsePerson;
import brave.btc.domain.bo.user.ManagePerson;
import brave.btc.dto.CommonResponseDto;
import brave.btc.exception.auth.UserPrincipalNotFoundException;
import brave.btc.repository.app.UsePersonRepository;
import brave.btc.repository.bo.ManagePersonRepository;
import brave.btc.service.common.auth.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//시큐리티 필터 중 BasicAuthentication 필터가 있음
//권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터에 들어감
//그렇지 않으면 통과함
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UsePersonRepository usePersonRepository;
    private final ManagePersonRepository managePersonRepository;
    private final JwtServiceImpl jwtService;

    public JwtAuthorizationFilter(
        AuthenticationManager authenticationManager,
        UsePersonRepository usePersonRepository,
        ManagePersonRepository managePersonRepository,
        JwtServiceImpl jwtService) {
        super(authenticationManager);
        this.usePersonRepository = usePersonRepository;
        this.managePersonRepository = managePersonRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("request = " + request.getServletPath());
        if (request.getServletPath().startsWith("/v1/auth/")) {
            chain.doFilter(request, response);
            return;
        }

        log.info("[Authorization] Refresh Token 유무 확인");
        String rtHeader = request.getHeader(JwtProperties.RT_HEADER);

        if (rtHeader != null) {
            if (!rtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
                throw new JwtException("유효하지 않은 Refresh Token입니다.");
            }
            String refreshToken = rtHeader.replaceAll(JwtProperties.TOKEN_PREFIX, "");
            try {
                CommonResponseDto<Object> responseDto = jwtService.refresh(refreshToken);
                ObjectMapper mapper = new ObjectMapper();
                response.setContentType("application/json; charset=UTF-8");
                mapper.writeValue(response.getOutputStream(), responseDto);
                chain.doFilter(request, response);
                return;
            } catch (Exception e) {
                throw new JwtException(e.getMessage());
            }
        }
        log.info("[Authorization] Refresh Token 없음 확인");

        log.info("[Authorization] Access Token 유무 확인");
        String atHeader = request.getHeader(JwtProperties.AT_HEADER);
        //토큰 헤더가 없다면 통과
        if (atHeader == null) {
            chain.doFilter(request, response);
            return;
        } else {
            //유효하지 않은 토큰 체크
            if (!atHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
                throw new JwtException("유효하지 않은 AccessToken입니다.");
            }
        }

        //토큰 헤더가 없다면 통과

        String atToken = atHeader.replaceAll(JwtProperties.TOKEN_PREFIX, "");
        log.info("atToken= {}", atToken);
        //서명 확인
        try {
            //Access Token 토큰 유효성 검사
            log.info("[Authorization] Access Token 유효성 검사 시작");
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build()
                    .verify(atToken);
            String loginId = decodedJWT.getClaim("loginId").asString();

            //TODO : ADMIN 추가하기
            Optional<UsePerson> findUsePersonOptional = usePersonRepository.findByLoginId(loginId);
            Optional<ManagePerson> findManagePersonOptional = managePersonRepository.findByLoginId(loginId);

            PrincipalDetails principalDetails;
            if (findUsePersonOptional.isEmpty() && findManagePersonOptional.isEmpty()) {
                throw new UserPrincipalNotFoundException("해당하는 유저가 존재하지 않습니다.");
            } else if (findUsePersonOptional.isPresent()) {
                principalDetails = new PrincipalDetails(findUsePersonOptional.get());
            } else if (findManagePersonOptional.isPresent()) {
                principalDetails = new PrincipalDetails(findManagePersonOptional.get());
            } else {
                throw new IllegalStateException("비정상 상태");
            }

            log.info("[Authorization] Access Token 유효성 검사 통과");
            log.debug("loginId= {}", loginId);

            //서명 통과
            log.info("[Authorization] 서명 통과");

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
