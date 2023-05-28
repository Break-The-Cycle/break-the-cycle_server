package brave.btc.config.security;

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

import brave.btc.config.CorsConfig;
import brave.btc.config.jwt.JwtAuthenticationFilter;
import brave.btc.config.jwt.JwtAuthorizationFilter;
import brave.btc.config.jwt.JwtExceptionFilter;
import brave.btc.repository.app.UsePersonRepository;
import brave.btc.repository.bo.ManagePersonRepository;
import brave.btc.service.common.auth.JwtServiceImpl;
import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final UsePersonRepository usePersonRepository;

    private final ManagePersonRepository managePersonRepository;
    private final JwtServiceImpl jwtService;
    private final JwtExceptionFilter jwtExceptionFilter;



    @Bean
    public CustomPasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }




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
                .headers()
                .frameOptions().disable()
                .and()
                .authorizeHttpRequests()
                .requestMatchers( "/v1/use-persons/**","/v1/violent-records/**")
                .access(new WebExpressionAuthorizationManager("hasRole('ROLE_USE_PERSON') or hasRole('ROLE_MANAGE_PERSON') or hasRole('ROLE_BO_MANAGE_PERSON')"))
                .requestMatchers("/v1/manage-persons/**")
                .access(new WebExpressionAuthorizationManager("hasRole('ROLE_MANAGE_PERSON') or hasRole('ROLE_BO_MANAGE_PERSON')"))
                .requestMatchers("/v1/bo-manage-persons/**")
                .access(new WebExpressionAuthorizationManager("hasRole('ROLE_BO_MANAGE_PERSON')"))
                .anyRequest().permitAll();


        return http.build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtService))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, usePersonRepository, managePersonRepository, jwtService));
        }
    }
}
