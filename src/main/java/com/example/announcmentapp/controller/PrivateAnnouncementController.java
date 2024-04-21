package com.example.announcmentapp.controller;

import com.example.announcmentapp.dto.request.AnnouncementRequest;
import com.example.announcmentapp.dto.response.AnnouncementResponse;

import com.example.announcmentapp.entity.Announcement;
import com.example.announcmentapp.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/private")
@RequiredArgsConstructor
public class PrivateAnnouncementController {

    private final AnnouncementService announcementService;
    @GetMapping("/all")
    public ResponseEntity<Page<Announcement>> getAllOwnAnnouncement(@RequestHeader Long userId, Pageable pageable) {
        return ResponseEntity.ok(announcementService.getAllOwnAnnouncement(userId,pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponse> getOwnAnnouncementWithId(@RequestHeader Long userId ,@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.getOwnAnnouncementWithId(userId,id));
    }

    @GetMapping("/mostViewed")
    public ResponseEntity<AnnouncementResponse> getOwnMostViewedAnnouncement(@RequestHeader Long userId ) {
        return ResponseEntity.ok(announcementService.getOwnMostViewedAnnouncement(userId));
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAnnouncement(@PathVariable Long id,@RequestBody AnnouncementRequest announcementRequest) {
        announcementService.createAnnouncement(announcementRequest,id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateAnnouncement(@PathVariable Long id, @RequestBody AnnouncementRequest announcementRequest) {
        announcementService.updateAnnouncement(id, announcementRequest);
    }




    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAnnouncementById(@PathVariable Long id) {
        announcementService.deleteAnnouncementById(id);
    }

}
