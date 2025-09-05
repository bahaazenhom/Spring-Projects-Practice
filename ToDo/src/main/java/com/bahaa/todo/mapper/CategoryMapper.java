package com.bahaa.todo.mapper;

import com.bahaa.todo.entity.Category;
import com.bahaa.todo.model.dto.CategoryDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);
    Category toCategoryEntity(CategoryDto categoryDto);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateFromCategoryDto(CategoryDto categoryDto, @MappingTarget Category category);
}
