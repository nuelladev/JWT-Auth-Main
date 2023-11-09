package com.davidson.jwt.JWT_Auth.config;

import com.davidson.jwt.JWT_Auth.repository.UserRepository;
import com.davidson.jwt.JWT_Auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        //Collect the header from the request
        final String authHeader = request.getHeader("Authorization");
        //String to hold the token
        final String jwtToken;
        //Create a string to hold the user email as username
        //this is the generic method in spring boot
        final String username;

        //Check for header null or Bearer
        if( authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        //Extract the token
        jwtToken = authHeader.substring(7);

        //extract the email from using the jwt.extractUsername(token) method
        username = jwtService.extractUsername(jwtToken);

        //Test for efficiency to print to console
//        System.out.println("Auth Header: " + authHeader);
//        System.out.println("Auth Header Starts With Bearer: " + authHeader.startsWith("Bearer "));
//        System.out.println("Username: " + username);


        //check for username not null and no existing authentication
        if( username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //this UserDetailsService method returns UserDetails
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);


            if(jwtService.isTokenValid(jwtToken, userDetails)){
                //Inbuilt library for username/password authentication, it takes 3 parameters
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                //Set details on authentication token based on request
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Set the Context Holder to hold the authentication token
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        //Another test
        System.out.println(userDetails.getAuthorities());


        filterChain.doFilter(request, response);



    }
}
