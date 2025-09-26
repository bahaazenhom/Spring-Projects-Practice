package com.bahaa.todo.service.impl;

import com.bahaa.todo.entity.Category;
import com.bahaa.todo.exception.category.CategoryNotFoundException;
import com.bahaa.todo.exception.user.UserNotFoundException;
import com.bahaa.todo.mapper.CategoryMapper;
import com.bahaa.todo.model.dto.CategoryDto;
import com.bahaa.todo.repository.CategoryRepository;
import com.bahaa.todo.repository.UserRepository;
import com.bahaa.todo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;
    private final CurrentUserService currentUserService;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, UserRepository userRepository, CurrentUserService currentUserService) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.categoryMapper = categoryMapper;
        this.currentUserService = currentUserService;

    }

    @Override
    public List<CategoryDto> findAllByUserId() {
        long userId = currentUserService.getCurrentUserId();
        List<Category> categories = categoryRepository.findAllByUserId(userId);
        return categories.stream().map(categoryMapper::toCategoryDto).toList();
    }

    @Override
    public CategoryDto findByIdAndUserId(Long categoryId) {
        long userId = currentUserService.getCurrentUserId();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist."));
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toCategoryEntity(categoryDto);
        long userId = currentUserService.getCurrentUserId();
        category.setUser(userRepository.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exist.")));
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        long userId = currentUserService.getCurrentUserId();
        Category category = categoryRepository.findByIdAndUserId(categoryDto.getId(), userId)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist."));
        categoryMapper.updateFromCategoryDto(categoryDto, category);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        long userId = currentUserService.getCurrentUserId();
        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new CategoryNotFoundException("Category does not exist."));
        categoryRepository.delete(category);
    }
}
