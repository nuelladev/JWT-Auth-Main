package com.davidson.jwt.JWT_Auth.controller;

import com.davidson.jwt.JWT_Auth.model.Role;
import com.davidson.jwt.JWT_Auth.model.User;
import com.davidson.jwt.JWT_Auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("ADMIN")
    @GetMapping("/securedDatabasePull")
    public List<User> allUsers(){
        return userRepository.findAll();

    }

    @PreAuthorize("ADMIN")
    @GetMapping("/usersByRole")
    public List<User> getUsersByRole(Role role){
        return userRepository.findByRole(role).orElseThrow(() -> new RuntimeException("No User with such role in the database"));
    }

    @GetMapping("/securedString")
    public String withoutLogin(){
        return "This is available because of JWT";
    }
}
