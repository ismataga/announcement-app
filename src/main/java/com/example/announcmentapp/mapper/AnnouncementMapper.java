package com.example.announcmentapp.mapper;

import com.example.announcmentapp.dto.request.AnnouncementRequest;
import com.example.announcmentapp.dto.response.AnnouncementResponse;
import com.example.announcmentapp.entity.Announcement;
import com.example.announcmentapp.entity.AnnouncementDetail;
import com.example.announcmentapp.entity.User;
import com.example.announcmentapp.exception.AppException;
import com.example.announcmentapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.example.announcmentapp.exception.ExceptionConstants.USER_NOT_FOUND;

@Mapper(componentModel = "spring", uses = {AnnouncementDetail.class})
public interface AnnouncementMapper {

    @Mapping(target = "announcementDetail.title", source = "title")
    @Mapping(target = "announcementDetail.description", source = "description")
    @Mapping(target = "announcementDetail.price", source = "price")
    @Mapping(target = "announcementDetail.createDate", source = "createDate")
    @Mapping(target = "announcementDetail.updateDate", source = "updateDate")
    Announcement requestToAnnouncement(AnnouncementRequest announcementRequest);


    @Mapping(target = "title", source = "announcementDetail.title")
    @Mapping(target = "description", source = "announcementDetail.description")
    @Mapping(target = "price", source = "announcementDetail.price")
    @Mapping(target = "viewCount", source = "viewCount")
    AnnouncementResponse announcementToResponse(Announcement announcement);

    List<AnnouncementResponse> announcementListToResponseList(List<Announcement> announcementList);
}
