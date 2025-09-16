package com.bahaa.todo.service.impl;

import java.util.List;
import java.util.Optional;

import com.bahaa.todo.mapper.UserMapper;
import com.bahaa.todo.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bahaa.todo.entity.Category;
import com.bahaa.todo.entity.User;
import com.bahaa.todo.repository.CategoryRepository;
import com.bahaa.todo.repository.UserRepository;
import com.bahaa.todo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = userMapper.toUserEntity(userDto);
        user.setCreatedAt(java.time.Instant.now());
        user.setUpdatedAt(java.time.Instant.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.toUserDto(user);
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
        User user = userRepository.getUserByUsername(username).
                orElseThrow(() -> new RuntimeException("User with username " + username + " does not exist."));
        user.setPassword(newPassword);
        user.setUpdatedAt(java.time.Instant.now());
        userRepository.save(user);
    }

    @Override
    public UserDto deleteUser(Long id) {
        User user = userRepository.getUserById(id).
                orElseThrow(() -> new RuntimeException("User with id " + id + " does not exist."));
        if (user == null) throw new RuntimeException("User with id " + id + " does not exist.");
        userRepository.delete(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.getUserByUsername(userDto.getUsername()).
                orElseThrow(() -> new RuntimeException("User with username " + userDto.getUsername() + " does not exist."));
        userMapper.updateUserFromDto(userDto, user);
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username).
                orElseThrow(() -> new RuntimeException("User with username " + username + " does not exist."));
        return userMapper.toUserDto(user);
    }


    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.getUserById(id).
                orElseThrow(() -> new RuntimeException("User with id " + id + " does not exist."));
        if (user == null) throw new RuntimeException("User with id " + id + " does not exist.");
        return userMapper.toUserDto(user);
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
                .map(userMapper::toUserDto)
                .toList();
    }


    @Override
    public List<Category> getCategoriesByUserId(Long userId) {
        return categoryRepository.findByUserId(userId);
    }
}
