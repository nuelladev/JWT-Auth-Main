package com.davidson.jwt.JWT_Auth.service;

import com.davidson.jwt.JWT_Auth.requests.AuthenticationRequest;
import com.davidson.jwt.JWT_Auth.requests.AuthenticationResponse;
import com.davidson.jwt.JWT_Auth.requests.RegisterRequest;
import com.davidson.jwt.JWT_Auth.model.User;
import com.davidson.jwt.JWT_Auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws Exception{
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
