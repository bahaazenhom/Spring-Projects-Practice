package com.bahaa.todo.control;

import com.bahaa.todo.model.dto.CategoryDto;
import com.bahaa.todo.model.dto.TaskDto;
import com.bahaa.todo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        logger.info("Request received: GET /categories - Fetching categories for current user");
        List<CategoryDto> categories = categoryService.findAllByUserId();
        logger.info("Fetched categories count: " + (categories != null ? categories.size() : 0));
        return categories;
    }

    @GetMapping("/category/{categoryId}")
    public CategoryDto getCategoryByIdAndUserId(@PathVariable Long categoryId) {
        logger.info("Request received: GET /categories/category/" + categoryId + " - Fetching category for current user");
        CategoryDto category = categoryService.findByIdAndUserId(categoryId);
        logger.info("Category fetched successfully for id: " + categoryId);
        return category;
    }
    
    @GetMapping("/tasks/{categoryId}")
    public ResponseEntity<List<TaskDto>> getTasksByCategoryId(@PathVariable Long categoryId) {
        logger.info("Request received: GET /categories/tasks/" + categoryId + " - Fetching tasks for current user");
        List<TaskDto> tasks = categoryService.findByIdAndUserId(categoryId).getTasks();
        logger.info("Fetched tasks count for category " + categoryId + ": " + (tasks != null ? tasks.size() : 0));
        return ResponseEntity.ok(tasks);
    }

    @PostMapping()
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        logger.info("Request received: POST /categories - Creating category");
        CategoryDto created = categoryService.createCategory(categoryDto);
        logger.info("Category created successfully with id: " + created.getId());
        return created;
    }

    @PutMapping()
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        logger.info("Request received: PUT /categories - Updating category with id: " + categoryDto.getId());
        CategoryDto updated = categoryService.updateCategory(categoryDto);
        logger.info("Category updated successfully with id: " + updated.getId());
        return updated;
    }

}
