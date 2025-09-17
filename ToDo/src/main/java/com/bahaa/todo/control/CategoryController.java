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

    @GetMapping("")
    public List<CategoryDto> getCategoriesByUserId() {
        return categoryService.findAllByUserId();
    }

    @GetMapping("/category/{categoryId}")
    public CategoryDto getCategoryByIdAndUserId(@PathVariable Long categoryId) {
        return categoryService.findByIdAndUserId(categoryId);
    }

    @PostMapping()
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    @PutMapping()
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }

}
