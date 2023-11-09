package com.davidson.jwt.JWT_Auth.requests;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {

    private String email;
    private String password;


}
