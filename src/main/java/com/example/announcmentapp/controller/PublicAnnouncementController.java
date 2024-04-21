package com.example.announcmentapp.controller;

import com.example.announcmentapp.dto.RegistrationDTO;
import com.example.announcmentapp.dto.SearchCriteria;
import com.example.announcmentapp.dto.response.AnnouncementResponse;
import com.example.announcmentapp.entity.Announcement;
import com.example.announcmentapp.service.AnnouncementService;
import com.example.announcmentapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/public")
@RequiredArgsConstructor
public class PublicAnnouncementController {
    private final AnnouncementService announcementService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegistrationDTO dto){
        userService.register(dto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(Long id){
        userService.login(id);
    }

    @GetMapping("/mostViewed")
    public ResponseEntity<AnnouncementResponse> getMostViewedAnnouncement() {
        return ResponseEntity.ok(announcementService.getMostViewedAnnouncement());
    }
    @GetMapping("/announcement")
    public ResponseEntity<Page<Announcement>> getAllAnnouncementsWithSpecification(@RequestBody List<SearchCriteria> searchCriteriaList,
                                                                                   @RequestParam(value = "pageSize") int pageSize,
                                                                                   @RequestParam(value = "pageNumber") int pageNumber,
                                                                                   @RequestParam(value = "pageSort") String[] pageSort) {
        return ResponseEntity.ok(announcementService.getAllAnnouncementsWithSpecification(searchCriteriaList,pageSize, pageNumber, pageSort));
    }


}
