package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.model.dto.UserDto;


public interface UserService {
    void registerUser(User user);

    boolean authenticateUserWithUsername(String username, String password);

    boolean authenticateUserWithEmail(String email, String password);

    void changePassword(String username, String newPassword);

    void deleteUser(Long id);

    void updateUser(User user);

    Optional<User> getUserByUsername(String username);

    Optional<UserDto> getUserById(Long id);

    boolean userExists(String username);

    boolean emailExists(String email);

    List<User> getAllUsers();

    List<Category> getCategoriesByUserId(Long userId);
}
