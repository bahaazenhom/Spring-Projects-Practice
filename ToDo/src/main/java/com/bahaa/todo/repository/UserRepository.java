package com.bahaa.todo.repository;

import com.bahaa.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> getUserByUsername(String username);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    Optional<User> getUserById(Long id);
}
