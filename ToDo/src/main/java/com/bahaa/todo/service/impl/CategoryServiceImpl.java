package com.bahaa.todo.service.impl;

import com.bahaa.todo.entity.Category;
import com.bahaa.todo.mapper.CategoryMapper;
import com.bahaa.todo.model.dto.CategoryDto;
import com.bahaa.todo.repository.CategoryRepository;
import com.bahaa.todo.repository.UserRepository;
import com.bahaa.todo.service.CategoryService;
import com.bahaa.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    UserRepository userRepository;
    CategoryMapper categoryMapper;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.categoryMapper = categoryMapper;

    }

    @Override
    public List<CategoryDto> findAllByUserId(Long userId) {
        List<Category> categories = categoryRepository.findAllByUserId(userId);
        return categories.stream().map((category) -> categoryMapper.toCategoryDto(category)).toList();
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category with id " + id + " does not exist."));
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toCategoryEntity(categoryDto);
        category.setUser(userRepository.getUserById(3L).orElseThrow(() -> new RuntimeException("User with id 12 does not exist.")));
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new RuntimeException("Category with id " + categoryDto.getId() + " does not exist."));
        categoryMapper.updateFromCategoryDto(categoryDto, category);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category with id " + id + " does not exist."));
        categoryRepository.delete(category);
    }
}
