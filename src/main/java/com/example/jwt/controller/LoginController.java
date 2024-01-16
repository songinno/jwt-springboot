package com.example.jwt.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.constants.Token;
import com.example.jwt.domain.AuthenticationRequest;
import com.example.jwt.prop.JwtProps;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
public class LoginController {

    // ! 시크릿키 받아오기
    @Autowired
    private JwtProps jwtProps;

    /*
     * JWT를 생성하는 login 요청
     * [GET] - /login
     * body:
     * {
     * "username" : "xxxx",
     * "password: : "1234"
     * }
     * 
     * @param authReq
     * 
     * @return
     */
    
    // * 토큰 생성
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authReq) {
        String username = authReq.getUsername();
        String password = authReq.getPassword();
        log.info("username: " + username);
        log.info("password: " + password);

        // ! 사용자 권한(DB 연동 전, 임시)
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLD_ADMIN");

        // ! secretkey를 byte로 변환해서 가져오기
        byte[] signingKey = jwtProps.getSecretKey().getBytes();
        log.info("signingKey: " + signingKey);

        // ! 토큰 생성
        String jwt = Jwts.builder()
                // .signWith(시크릿키, 알고리즘)
                .signWith(Keys.hmacShaKeyFor(signingKey), Jwts.SIG.HS512) // 시그니처에 사용할 비밀키와 알고리즘 설정
                // 헤더에 토큰 타입 설정
                .header()
                .add("typ", Token.TYPE.text)
                // .and() 이후, 데이터에 대한 설정 진행
                .and()
                // 토큰 만료 시간 설정 (5일)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 5))
                // PAYLOAD - uid : user (사용자 아이디)
                .claim("uid", username)
                // PAYLOAD - role : [ROLE_USER, ROLE_ADMIN] (권한 정보)
                .claim("rol", roles)
                // 최종 토큰 생성
                .compact();

        log.info("jwt: " + jwt);

        return new ResponseEntity<String>(jwt, HttpStatus.OK);
    }

    // * 토큰 해석
    @GetMapping("/user/info")
    public ResponseEntity<?> userInfo(@RequestHeader(name = "Authorization") String header) {
                                    // @RequestHeader : 인자로 설정한 이름의 Header를 꺼내서 쓸 수 있음
        log.info("======= header =======");
        log.info("Authorization: " + header);

        // ! 전처리 - Authorization : Bearer ${jwt}
        String jwt = header.replace(Token.PREFIX.text, ""); // 토큰 접두사 삭제

        // ! secretkey를 byte로 변환해서 가져오기
        byte[] signingKey = jwtProps.getSecretKey().getBytes();
        log.info("signingKey: " + signingKey);

        // ! 해석 객체 생성
        Jws<Claims> parsedToken = Jwts.parser()
                                    .verifyWith(Keys.hmacShaKeyFor(signingKey))
                                    .build()
                                    .parseSignedClaims(jwt);
        log.info("parsedToken: " + parsedToken);
        // uid: user
        String username = parsedToken.getPayload().get("uid").toString();
        log.info("username: " + username);
        
        // rol : [ROLE_USER, ROLE_ADMIN]
        Claims claims = parsedToken.getPayload();
        Object roles = claims.get("rol");
        log.info("roles: " + roles);

        return new ResponseEntity<String>(parsedToken.toString(), HttpStatus.OK);
    }

}
