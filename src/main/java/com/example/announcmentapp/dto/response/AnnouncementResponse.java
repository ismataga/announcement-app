package com.example.announcmentapp.dto.response;

import lombok.Data;

@Data
public class AnnouncementResponse {
    private String title;
    private String description;
    private Integer price;
    private Integer viewCount;
}
