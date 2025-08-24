package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;


public interface UserService {
    void registerUser(String username, String email, String password);

    boolean authenticateUserWithUsername(String username, String password);

    boolean authenticateUserWithEmail(String email, String password);

    void changePassword(String username, String newPassword);

    void deleteUser(String username);

    void updateUser(User user);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserById(Long id);

    boolean userExists(String username);

    boolean emailExists(String email);

    Iterable<User> getAllUsers();

    List<Category> getCategoriesByUserId(Long userId);
}
