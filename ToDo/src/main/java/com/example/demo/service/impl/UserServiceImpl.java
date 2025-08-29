package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setCreatedAt(java.time.Instant.now());
        user.setUpdatedAt(java.time.Instant.now());
        userRepository.save(user);
        return new UserDto(user.getUsername(), user.getEmail(), user.getPassword(), user.getRole());
    }

    @Override
    public boolean authenticateUserWithUsername(String username, String password) {
        return false;
    }

    @Override
    public boolean authenticateUserWithEmail(String email, String password) {
        return false;
    }

    @Override
    public void changePassword(String username, String newPassword) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) throw new RuntimeException("User with username " + username + " does not exist.");
        user.setPassword(newPassword);
        user.setUpdatedAt(java.time.Instant.now());
        userRepository.save(user);
    }

    @Override
    public UserDto deleteUser(Long id) {
        User user = userRepository.getUserById(id);
        if (user == null) throw new RuntimeException("User with id " + id + " does not exist.");
        userRepository.delete(user);
        return new UserDto(user.getUsername(), user.getEmail(), user.getPassword(), user.getRole());
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.getUserByUsername(userDto.getUserName());
        if (user == null) throw new RuntimeException("User with username " + userDto.getUserName() + " does not exist.");
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setCreatedAt(user.getCreatedAt());
        user.setUpdatedAt(java.time.Instant.now());
        userRepository.save(user);
        return new UserDto(user.getUsername(), user.getEmail(), user.getPassword(), user.getRole());
    }

    @Override
    public Optional<UserDto> getUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) return Optional.empty();
        UserDto userDto = new UserDto(user.getUsername(), user.getEmail(), user.getPassword(), user.getRole());
        return Optional.of(userDto);
    }


    @Override
    public Optional<UserDto> getUserById(Long id) {
        User user = userRepository.getUserById(id);
        if (user == null) return Optional.empty();
        UserDto userDto = new UserDto(user.getUsername(), user.getEmail(), user.getPassword(), user.getRole());

        return Optional.of(userDto);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsUserByUsername(username);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getUsername(), user.getEmail(), user.getPassword(), user.getRole()))
                .toList();
    }


    @Override
    public List<Category> getCategoriesByUserId(Long userId) {
        return categoryRepository.findByUserId(userId);
    }
}
