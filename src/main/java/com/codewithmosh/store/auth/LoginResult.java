package com.codewithmosh.store.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResult {
    private Jwt accessToken;
    private Jwt refreshToken;
}
