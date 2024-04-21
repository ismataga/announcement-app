package com.example.announcmentapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Entity
@Slf4j
@Data
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer viewCount;

    @OneToOne(cascade = CascadeType.PERSIST)
    private AnnouncementDetail announcementDetail;

    @ManyToOne
    private User user;


}
