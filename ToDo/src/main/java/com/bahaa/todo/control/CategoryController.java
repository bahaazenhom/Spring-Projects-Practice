package com.bahaa.todo.control;

import com.bahaa.todo.model.dto.CategoryDto;
import com.bahaa.todo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/categories")
@RestController
@Validated
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{userId}")
    public List<CategoryDto> getCategoriesByUserId(@PathVariable Long userId) {
        return categoryService.findAllByUserId(userId);
    }

    @GetMapping("/category/{categoryId}")
    public CategoryDto getCategoryById(@PathVariable Long categoryId) {
        return categoryService.findById(categoryId);
    }

    @PostMapping()
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    @PutMapping("/category/{categoryId}")
    public CategoryDto updateCategory(@PathVariable Long categoryId, @RequestBody @Valid CategoryDto categoryDto) {
        categoryDto.setId(categoryId);
        return categoryService.updateCategory(categoryDto);
    }

}
