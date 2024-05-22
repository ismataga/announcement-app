package com.example.announcmentapp.controller;

import com.example.announcmentapp.config.JwtService;
import com.example.announcmentapp.entity.User;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@OpenAPIDefinition(info = @Info(title = "Bank app ",
        version = "1.0.0",
        description = "Bank app for customer"))
public class AccountController {
    @Autowired
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/security")
    public String postPublic() {
        return "get public method";
    }

    @GetMapping("/security/1")
    public String getPublic(Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername("qulu");
        String generateToken = jwtService.generateToken((User) userDetails);
        return "Token: " + generateToken;

    }

    @GetMapping("/security/2")
    public String getPublic2() {
        return "get public method 2";

    }

}
