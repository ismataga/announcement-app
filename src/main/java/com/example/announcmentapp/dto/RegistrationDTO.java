package com.example.announcmentapp.dto;

import com.example.announcmentapp.annotation.PasswordMatch;

import com.example.announcmentapp.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@PasswordMatch
public class RegistrationDTO {

    @NotBlank
    @Email
//    @Unique(repositoryClass = UserRepository.class, methodName = "existsByUsername")
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

}
