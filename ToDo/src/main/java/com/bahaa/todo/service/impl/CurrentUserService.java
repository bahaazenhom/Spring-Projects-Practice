package com.bahaa.todo.service.impl;

import com.bahaa.todo.exception.NoAuthenticatedUser;
import com.bahaa.todo.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserService {

    public Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getId();
        }
        throw new NoAuthenticatedUser("No authenticated user found.");
    }
}
