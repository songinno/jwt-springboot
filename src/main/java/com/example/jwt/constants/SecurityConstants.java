package com.example.jwt.constants;

// ! Spring Security 및 JWT 관련 상수를 정의한 상수 클래스
/* 
 * HTTP
 *  headers: {
 *  Authorization : Bearer ${jwt}
 * }
 */

 // final로 선언하여 상속 방지, 상수만 정의
public final class SecurityConstants {
    //! JWT 토근을 HTTP 헤더에서 식별하는데 사용되는 헤더 이름
    public static final String TOKEN_HEADER = "Authrization";

    //! JWT 토큰의 접두사 (일반적으로 "Bearer " 다음에 실제 토큰이 온다.)
    public static final String TOKEN_PREFIX = "Bearer ";

    //! JWT 토큰의 타입을 나타내는 상수
    public static final String TOKEN_TYPE = "JWT";
}
