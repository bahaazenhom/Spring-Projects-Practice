package com.example.demo.service.impl;

import com.example.demo.dao.CategoryRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(String username, String email, String password) {
        User user = new User(username, email, passwordEncoder.encode(password));
        user.setCreatedAt(java.time.Instant.now());
        user.setUpdatedAt(java.time.Instant.now());
        user.setRole(UserRole.USER.name());
        userRepository.save(user);
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
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(java.time.Instant.now());
        userRepository.save(user);

    }

    @Override
    public void deleteUser(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) throw new RuntimeException("User with username " + username + " does not exist.");
        userRepository.delete(user);
    }

    @Override
    public void updateUser(User user) {
        user.setUpdatedAt(java.time.Instant.now());
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.getUserByUsername(username));
    }


    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
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
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public List<Category> getCategoriesByUserId(Long userId) {
        return categoryRepository.findByUserId(userId);
    }
}
