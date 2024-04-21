package com.example.announcmentapp.mapper;

import com.example.announcmentapp.dto.RegistrationDTO;
import com.example.announcmentapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Base64;

@Mapper(componentModel = "spring")
public interface UserMapper {


//    @Mapping(target = "password", expression = "java(java.util.Base64.getEncoder().encode(dto.getPassword()))")
    @Mapping(target = "password", source = "password" , qualifiedByName ="encodePassword")
    User toEntity(RegistrationDTO dto);

    @Named("encodePassword")
    default String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }


}
