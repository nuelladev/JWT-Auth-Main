package com.davidson.jwt.JWT_Auth.requests;


import com.davidson.jwt.JWT_Auth.model.Role;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private Role role;
}
