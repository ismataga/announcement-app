package com.example.announcmentapp.repository;

import com.example.announcmentapp.dto.response.AnnouncementResponse;
import com.example.announcmentapp.entity.Announcement;
import com.example.announcmentapp.spec.AnnouncementSpecification;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> , JpaSpecificationExecutor<Announcement> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Announcement> findById(Long id);

    @Query(value = "SELECT a FROM Announcement a ORDER BY a.viewCount DESC limit 1")
    Optional<Announcement> findMostViewAnnouncementWithLimit();

    @Query(value = "SELECT a FROM Announcement a ORDER BY a.viewCount DESC limit 1")
    Optional<Announcement> findAllMostViewAnnouncementWithLimit();
//
//
//    List<AnnouncementResponse> findAll(AnnouncementSpecification announcementSpecification);
}
