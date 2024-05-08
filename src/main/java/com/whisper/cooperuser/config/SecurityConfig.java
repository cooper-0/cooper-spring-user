package com.whisper.cooperuser.config;

import com.whisper.cooperuser.jwt.JwtAccessDeniedHandler;
import com.whisper.cooperuser.jwt.JwtAuthenticationEntryPoint;
import com.whisper.cooperuser.jwt.JwtFilter;
import com.whisper.cooperuser.jwt.JwtUtil;
import com.whisper.cooperuser.service.UserDetailServiceImpl;
import com.whisper.cooperuser.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final UserService userService;

    private final JwtUtil jwtUtil;
    private final UserDetailServiceImpl userDetailService;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)      // OAuth2, Jwt토큰 등을 사용할 예정이라 disable
                .cors(Customizer.withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable) // REST API,토큰을 사용하기에 disable
                .formLogin(AbstractHttpConfigurer::disable) // 마찬기지

                .addFilterBefore(new JwtFilter(jwtUtil, userDetailService), UsernamePasswordAuthenticationFilter.class) // jwt 필터 추가
                                                                                                     // JwtFilter가 서버에 대한 요청에 유효성 검증 수행
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig
                                .accessDeniedHandler(accessDeniedHandler)
                                .authenticationEntryPoint(authenticationEntryPoint)
                ) // 401 403 관련 예외처리

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))    // 토큰을 사용해 세션 사용x

                .authorizeHttpRequests((authorize) -> authorize                     // 인증, 인가가 필요한 URL 지정
                        .requestMatchers("cooper-user/signup").permitAll() // requestMatchers에서 지정된 url은 인증, 인가 없이도 접근 허용
                        .requestMatchers("cooper-user/signin").permitAll()
//                        .anyRequest().permitAll()                                   // 인증, 인가 없이 테스트할 때는 모든 경로 허용하고 테스트
                        .anyRequest().authenticated())

//                .logout((logout) -> logout
//                        .logoutSuccessUrl("/cooper-user/signin")
//                        .invalidateHttpSession(true))
//
//                .userDetailsService(userService)

                .build();
    }

    // Spring Security 비밀번호 암호화
    // 암호화는 가능, 복호화는 불가능
    @Bean
    public static PasswordEncoder passwordEncoder() {
        // DelegatingPasswordEncoder 여러 인코딩 알고리즘 사용 가능
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}