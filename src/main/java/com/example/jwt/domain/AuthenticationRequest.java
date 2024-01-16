package com.example.jwt.domain;

import lombok.Data;

@Data
public class AuthenticationRequest {
    // 파라미터로 바인딩 해서 받아올 필드들
    private String username;
    private String password;
}
