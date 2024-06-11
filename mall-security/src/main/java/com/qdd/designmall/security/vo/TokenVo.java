package com.qdd.designmall.security.vo;

import lombok.Data;

@Data
public class TokenVo {
    String token;
    String bearerPrefix = "Bearer ";
    String authHeader = "Authorization";
}
