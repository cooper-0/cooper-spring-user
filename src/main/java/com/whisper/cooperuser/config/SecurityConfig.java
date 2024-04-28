//package com.whisper.cooperuser.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.whisper.cooperuser.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.logout.LogoutFilter;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final UserService userService;
//    private final ObjectMapper objectMapper;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable) // OAuth2, Jwt토큰 등을 사용할 예정이라 disable
//                .httpBasic(AbstractHttpConfigurer::disable) // REST API,토큰을 사용하기에 disable
//                .formLogin(AbstractHttpConfigurer::disable) // 마찬기지
////                .addFilterAfter(customFilter, LogoutFilter.class)
//                .authorizeHttpRequests((authorize) -> authorize // 인증, 인가가 필요한 URL 지정
//                        .requestMatchers("/users/signup", "/users/**", "/users/login", "cooper-test/test").permitAll() // requestMatchers에서 지정된 url은 인증, 인가 없이도 접근 허용, "/user/signup", "/", "/user/login", "/css/**", "/exception/**", "/favicon.ico"
//                        .anyRequest().authenticated())
//                .logout((logout) -> logout
//                        .logoutSuccessUrl("/login")
//                        .invalidateHttpSession(true))
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 토큰을 사용해 세션 사용x
//                .build();
//    }
//
//    // Spring Security 비밀번호 암호화
//    // 암호화는 가능, 복호화는 불가능
//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//        // DelegatingPasswordEncoder 여러 인코딩 알고리즘 사용 가능
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//}