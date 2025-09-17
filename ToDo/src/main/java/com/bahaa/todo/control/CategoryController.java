package com.bahaa.todo.control;

import com.bahaa.todo.model.dto.CategoryDto;
import com.bahaa.todo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

import static org.hibernate.internal.CoreLogging.logger;

@RequestMapping("/categories")
@RestController
@Validated
public class CategoryController {
    private final CategoryService categoryService;
    private final Logger logger = Logger.getLogger(CategoryController.class.getName());

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return categoryService.createCategory(categoryDto);
    }

    @PutMapping("/category/{categoryId}")
    public CategoryDto updateCategory(@PathVariable Long categoryId, @RequestBody @Valid CategoryDto categoryDto) {
        categoryDto.setId(categoryId);
        return categoryService.updateCategory(categoryDto);
    }

}
