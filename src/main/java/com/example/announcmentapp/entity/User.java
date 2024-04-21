package com.example.announcmentapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Entity
@Slf4j
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String age;
    private boolean isLogin;

    @OneToMany
    private List<Announcement> announcementList;

}
