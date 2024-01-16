package com.example.jwt.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/* 
 * Spring Boot의 @ConfigurationProperties 어노테이션을 사용하여,
 * application.properties(속성 설정 파일)로부터 JWT 관련 프로퍼티를 관리하는 프로퍼티 클래스
 * -> applicaiton.properties에서 secret-key 값을 가져오는 것
 */

@Data
@Component
@ConfigurationProperties("com.example.jwt") // application.properties 파일 내 작성된, com.exaple.jwt 경로 하위 속성들 지정
public class JwtProps {
    // ! com.example.jwt.seceretKey로 지정된 프로퍼티 값을 주입 받는 필드
    // ㄴ com.example.jwt.secret-key -> secretKey : {인코딩된 시크릿 키}
    private String secretKey;
    
}
