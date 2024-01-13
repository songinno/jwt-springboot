package com.example.jwt.config;

// SpringSecurity 5.4 이하에서는 extends WebSecurityConfigurerAdapter를 상속 받아 구현했었음

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Spring Security 5.5 이상
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // * Security를 직접적으로 사용할 것이 아니라, 비활성화 작업 진행

        // ! form 기반 로그인 비활성화
        // http.formLogin().disable();
        // 람다식으로 표현
        http.formLogin(login -> login.disable());

        // ! HTTP 기본 인증 비활성화
        http.httpBasic(basic -> basic.disable());

        // ! CSRF 공격 방어 기능 비활성화(나중에 고려)
        http.csrf(csrf -> csrf.disable());

        // ! session 관리 정책 설정
        // ㄴ 세션 인증을 사용하지 않고, JWT를 사용하여 인증하기 때문에 session 불필요 -> 비활성화
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // STATELESS 설정 시, 서버에서 세션 생성X

        // ! 구성 완료된 SecurityFilterChain 반환
        return http.build();
    }
}
