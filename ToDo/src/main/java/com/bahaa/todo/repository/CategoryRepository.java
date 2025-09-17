package com.bahaa.todo.repository;

import com.bahaa.todo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByUserId(Long userId);

    List<Category> findAllByUserId(Long userId);

    Optional<Category> findByIdAndUserId(Long categoryId, Long userId);
}
