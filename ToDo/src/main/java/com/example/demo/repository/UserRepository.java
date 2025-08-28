package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User getUserByUsername(String username);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    User getUserById(Long id);
}
