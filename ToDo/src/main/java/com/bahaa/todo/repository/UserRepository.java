package com.bahaa.todo.repository;

import com.bahaa.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User getUserByUsername(String username);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    User getUserById(Long id);
}
