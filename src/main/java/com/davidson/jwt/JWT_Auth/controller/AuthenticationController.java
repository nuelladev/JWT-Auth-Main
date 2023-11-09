package com.davidson.jwt.JWT_Auth.controller;

import com.davidson.jwt.JWT_Auth.requests.AuthenticationRequest;
import com.davidson.jwt.JWT_Auth.requests.AuthenticationResponse;
import com.davidson.jwt.JWT_Auth.requests.RegisterRequest;
import com.davidson.jwt.JWT_Auth.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authorize")
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @GetMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
