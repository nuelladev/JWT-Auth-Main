package com.davidson.jwt.JWT_Auth.requests;

import lombok.*;

@Builder
@Getter
@Setter
public class AuthenticationResponse {

    private String token;
}
