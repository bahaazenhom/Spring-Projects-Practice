package com.bahaa.todo.repository;

import com.bahaa.todo.entity.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory,Long> {
}
