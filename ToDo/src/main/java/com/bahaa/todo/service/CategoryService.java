package com.bahaa.todo.service;

import com.bahaa.todo.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    public List<CategoryDto> findAllByUserId(Long userId);
    public CategoryDto findById(Long id);
    public CategoryDto createCategory(CategoryDto categoryDto);
    public CategoryDto updateCategory(CategoryDto categoryDto);
    public void deleteCategory(Long id);
}
