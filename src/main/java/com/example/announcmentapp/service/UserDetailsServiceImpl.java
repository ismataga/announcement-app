package com.example.announcmentapp.service;


import com.example.announcmentapp.exception.AppException;
import com.example.announcmentapp.exception.ExceptionConstants;
import com.example.announcmentapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ////      UserDetails userDetails  = org.springframework.security.core.userdetails.User
////              .builder()
////              .username("a")
////              .password("a")
////
//    }

        return userRepository.findByUsername(username).orElseThrow(() -> new AppException(ExceptionConstants.USER_NOT_FOUND));
    }
}



