package com.bahaa.todo.service;

import com.bahaa.todo.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    public List<CategoryDto> findAllByUserId();
    public CategoryDto findByIdAndUserId(Long categoryId);
    public CategoryDto createCategory(CategoryDto categoryDto);
    public CategoryDto updateCategory(CategoryDto categoryDto);
    public void deleteCategory(Long id);
}
