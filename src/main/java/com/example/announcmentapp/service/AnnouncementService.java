package com.example.announcmentapp.service;

import com.example.announcmentapp.dto.SearchCriteria;
import com.example.announcmentapp.dto.request.AnnouncementRequest;
import com.example.announcmentapp.dto.response.AnnouncementResponse;
import com.example.announcmentapp.entity.Announcement;
import com.example.announcmentapp.entity.User;
import com.example.announcmentapp.exception.AppException;
import com.example.announcmentapp.mapper.AnnouncementMapper;
import com.example.announcmentapp.repository.AnnouncementRepository;
import com.example.announcmentapp.repository.UserRepository;
import com.example.announcmentapp.spec.AnnouncementSpecification;
import jakarta.persistence.LockModeType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;

import static com.example.announcmentapp.exception.ExceptionConstants.ANNOUNCEMENT_NOT_FOUND;
import static com.example.announcmentapp.exception.ExceptionConstants.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final AnnouncementMapper announcementMapper;
    private final UserService userService;


    public void createAnnouncement(AnnouncementRequest announcementRequest, Long id) {
        log.info("Creating announcement: {}", announcementRequest);
        Announcement announcement = null;
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(id, USER_NOT_FOUND));
        if (user != null && user.isLogin()) {
            announcement = announcementMapper.requestToAnnouncement(announcementRequest);
            announcement.setUser(user);

        }
        announcementRepository.save(announcement);
    }

    public void updateAnnouncement(Long id, AnnouncementRequest announcementRequest) {
        log.info("updateAnnouncementById().start " + id);
        Announcement announcementsEntity = announcementRepository.findById(id)
                .orElseThrow(() -> new AppException(id, ANNOUNCEMENT_NOT_FOUND));
        if (Objects.nonNull(announcementRequest.getTitle())) {
            announcementsEntity.getAnnouncementDetail().setTitle(announcementRequest.getTitle());
        }

        if (Objects.nonNull(announcementRequest.getDescription())) {
            announcementsEntity.getAnnouncementDetail().setDescription(announcementRequest.getDescription());
        }

        if (Objects.nonNull(announcementRequest.getPrice())) {
            announcementsEntity.getAnnouncementDetail().setPrice(announcementRequest.getPrice());
        }
        if (Objects.nonNull(announcementRequest.getCreateDate())) {
            announcementsEntity.getAnnouncementDetail().setCreateDate(announcementRequest.getCreateDate());
        }
        if (Objects.nonNull(announcementRequest.getUpdateDate())) {
            announcementsEntity.getAnnouncementDetail().setUpdateDate(announcementRequest.getUpdateDate());
        }

        log.info("updateAnnouncementById().end " + id);
    }


    public void deleteAnnouncementById(Long id) {
        log.info("deleteAnnouncementById().start " + id);
        announcementRepository.findById(id).orElseThrow(() -> new AppException(id, ANNOUNCEMENT_NOT_FOUND));
        announcementRepository.deleteById(id);
        log.info("deleteAnnouncementById().end " + id);
    }

    @Transactional
    public AnnouncementResponse getOwnAnnouncementWithId(Long userId, Long id) {
        log.info("getOwnAnnouncementWithId().start " + id);
        Announcement announcement;

        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(userId, USER_NOT_FOUND));
        if (user != null && user.isLogin()) {
            announcement = announcementRepository.findById(id)
                    .orElseThrow(() -> new AppException(id, ANNOUNCEMENT_NOT_FOUND));

            int count = 0;
            if (announcement.getViewCount() != null) {
                count = announcement.getViewCount();
            }
            count++;
            announcement.setViewCount(count);
        } else {
            throw new AppException(userId, USER_NOT_FOUND);
        }
        log.info("getOwnAnnouncementWithId().end " + id);
        return announcementMapper.announcementToResponse(announcement);
    }

    public Page<Announcement> getAllOwnAnnouncement(Long userId, Pageable pageable) {
        log.info("getAllOwnAnnouncement().start");
        Page<AnnouncementResponse> announcementResponseList = null;
        Page<Announcement> announcementList = null;
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(userId, USER_NOT_FOUND));
        if (user != null && user.isLogin()) {
            announcementList = announcementRepository.findAll(pageable);
//            announcementResponseList = announcementMapper.announcementListToResponseList(announcementList);

        }
        log.info("getAllOwnAnnouncement().end");
        return announcementList;
    }

    public AnnouncementResponse getOwnMostViewedAnnouncement(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(userId, USER_NOT_FOUND));
        Announcement announcement = null;
        if (user != null && user.isLogin()) {
            announcement = announcementRepository.findMostViewAnnouncementWithLimit().orElseThrow(() -> new AppException(ANNOUNCEMENT_NOT_FOUND));
        }

        return announcementMapper.announcementToResponse(announcement);
    }

    public AnnouncementResponse getMostViewedAnnouncement() {
        Announcement announcement = announcementRepository.findAllMostViewAnnouncementWithLimit().orElseThrow(() -> new AppException(ANNOUNCEMENT_NOT_FOUND));

        return announcementMapper.announcementToResponse(announcement);

    }

    public Page<Announcement> getAllAnnouncementsWithSpecification(List<SearchCriteria> searchCriteriaList, int pageSize, int pageNumber, String[] pageSort) {
        Pageable pageable = PageRequest.of(pageSize, pageNumber, Sort.by(pageSort[0]).descending());
        AnnouncementSpecification announcementSpecification = new AnnouncementSpecification();
        searchCriteriaList.forEach(announcementSpecification::addSearchCriteria);
        return announcementRepository.findAll(announcementSpecification, pageable);

    }
}
