package com.example.mapper;

import com.example.model.Category;
import com.example.model.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toCategory(CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        return new Category(
                dto.getId(),
                dto.getTitle()
        );
    }

    public CategoryDto toCategoryDto(Category entity) {
        if (entity == null) {
            return null;
        }
        return new CategoryDto(
                entity.getId(),
                entity.getTitle()
        );
    }
}
