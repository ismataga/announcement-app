package com.example.announcmentapp.service;

import com.example.announcmentapp.dto.RegistrationDTO;
import com.example.announcmentapp.entity.User;
import com.example.announcmentapp.exception.AppException;
import com.example.announcmentapp.mapper.UserMapper;
import com.example.announcmentapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.announcmentapp.exception.ExceptionConstants.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public void login(long id) {
        userRepository.findById(id)
                .ifPresentOrElse(this::activateUser, () -> {
                    throw new AppException(USER_NOT_FOUND);
                });

    }

    protected void activateUser(User user) {
        user.setLogin(true);
        userRepository.save(user);
    }


    public void register(RegistrationDTO dto) {
        Optional.of(dto)
                .map(mapper::toEntity)
                .map(userRepository::save)
                .orElseThrow();

    }
}
