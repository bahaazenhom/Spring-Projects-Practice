package com.bahaa.todo.service.impl;

import com.bahaa.todo.entity.User;
import com.bahaa.todo.repository.UserRepository;
import com.bahaa.todo.config.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Slf4j
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = Logger.getLogger(CustomUserDetailsServiceImpl.class.getName());
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user by username: " + username);
        User user = userRepository.getUserByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " does not exist."));
        CustomUserDetails customUserDetails =  new CustomUserDetails(user);
        logger.info("User loaded successfully.");
        return customUserDetails;
    }
}
