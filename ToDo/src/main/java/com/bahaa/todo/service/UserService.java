package com.bahaa.todo.service;

import java.util.List;
import java.util.Optional;

import com.bahaa.todo.entity.Category;
import com.bahaa.todo.model.dto.UserDto;


public interface UserService {
    UserDto registerUser(UserDto user);

    boolean authenticateUserWithUsername(String username, String password);

    boolean authenticateUserWithEmail(String email, String password);

    void changePassword(String username, String newPassword);

    UserDto deleteUser(Long id);

    UserDto updateUser(UserDto user);

    Optional<UserDto> getUserByUsername(String username);

    Optional<UserDto> getUserById(Long id);

    boolean userExists(String username);

    boolean emailExists(String email);

    List<UserDto> getAllUsers();

    List<Category> getCategoriesByUserId(Long userId);
}
