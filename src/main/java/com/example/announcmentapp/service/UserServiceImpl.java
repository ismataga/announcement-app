package com.example.announcmentapp.service;

import com.example.announcmentapp.entity.User;
import com.example.announcmentapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl  {
    private final UserRepository userRepository;


    public User getUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
//        user.getAddress();
        return user;

    }
}
