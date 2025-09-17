package com.bahaa.todo.service;

import java.util.List;

import com.bahaa.todo.entity.Category;
import com.bahaa.todo.model.dto.UserDto;


public interface UserService {
    UserDto registerUser(UserDto user);

    boolean authenticateUserWithUsername(String username, String password);

    boolean authenticateUserWithEmail(String email, String password);

    void changePassword(String username, String newPassword);

    void deleteUser();

    UserDto updateUser(UserDto user);

    UserDto getUserByUsername(String username);

    UserDto getCurrentUser();

    boolean userExists(String username);

    boolean emailExists(String email);

    List<UserDto> getAllUsers();

    List<Category> getCategoriesByUserId();
}
