package com.example.mapper;

import com.example.model.CategoryListing;
import com.example.model.CategoryListingDto;
import com.example.model.Category;
import com.example.model.Listing;
import org.springframework.stereotype.Component;

@Component
public class CategoryListingMapper {

    public CategoryListing toCategoryListing(CategoryListingDto dto) {
        if (dto == null) {
            return null;
        }
        return new CategoryListing(
                dto.getId(),
                new Listing(dto.getListing()),
                new Category(dto.getCategory())
        );
    }

    public CategoryListingDto toCategoryListingDto(CategoryListing entity) {
        if (entity == null) {
            return null;
        }
        return new CategoryListingDto(
                entity.getId(),
                entity.getListing().getId(),
                entity.getCategory().getId()
        );
    }
}
