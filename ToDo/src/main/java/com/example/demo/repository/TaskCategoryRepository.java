package com.example.demo.repository;

import com.example.demo.entity.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory,Long> {
}
