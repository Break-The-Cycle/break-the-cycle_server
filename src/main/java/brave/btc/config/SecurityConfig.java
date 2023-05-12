package brave.btc.config;

import brave.btc.config.jwt.JwtAuthenticationFilter;
import brave.btc.config.jwt.JwtAuthorizationFilter;
import brave.btc.config.jwt.JwtExceptionFilter;
import brave.btc.repository.app.UsePersonRepository;
import brave.btc.service.app.auth.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final UsePersonRepository usePersonRepository;
    private final AuthServiceImpl authServiceImpl;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtExceptionFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new MyCustomDsl())  //@CrossOrigin(인증X) 인증이 필요한 것에는 필터 추가
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/v1/auth/user/**")
                .access(new WebExpressionAuthorizationManager("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')"))
                .requestMatchers("/v1/manager/**")
                .access(new WebExpressionAuthorizationManager("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')"))
                .requestMatchers("/v1/admin/**")
                .access(new WebExpressionAuthorizationManager("hasRole('ROLE_ADMIN')"))
                .anyRequest().permitAll();


        return http.build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAuthenticationFilter(authenticationManager, authServiceImpl))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, usePersonRepository));
        }
    }
}
