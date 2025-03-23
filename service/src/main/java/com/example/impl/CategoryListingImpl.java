package com.example.impl;

import com.example.interfaces.CategoryListingDao;
import com.example.interfaces.CategoryListingService;
import com.example.mapper.CategoryListingMapper;
import com.example.mapper.CategoryMapper;
import com.example.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryListingImpl implements CategoryListingService {

    private final CategoryListingMapper categoryListingMapper;
    private final CategoryListingDao categoryListingDao;

    @Override
    public CategoryListingDto add(CategoryListingDto object) {
        try {
           CategoryListing categoryListing = new CategoryListing();
           BeanUtils.copyProperties(object, categoryListing);
           categoryListing.setListing(new Listing(object.getListing()));
           categoryListing.setCategory(new Category(object.getCategory()));
           CategoryListing savedCategoryListing = categoryListingDao.save(categoryListing);
           object.setId(savedCategoryListing.getId());
           log.info("Adding categoryListing: {}", object);
           return object;
        } catch (Exception e) {
            log.error("Add categoryListing error", e);
        }
        return object;
    }

    @Override
    public CategoryListingDto get(Long id) {
        try {
            CategoryListing categoryListing = categoryListingDao.findById(id).orElse(null);
            log.info("Getting categoryListing: {}", categoryListing);
            return categoryListingMapper.toCategoryListingDto(categoryListing);
        } catch (Exception e) {
            log.error("Getting categoryListing error", e);
            return null;
        }
    }

    @Override
    public boolean update(CategoryListingDto object, Long id) {
        try {
            CategoryListing categoryListing = categoryListingDao.findById(id).orElse(null);
            if (categoryListing == null) {
                log.warn("categoryListing not found");
                return false;
            }
            categoryListingDao.save(categoryListingMapper.toCategoryListing(object));
            log.info("Updating categoryListing: {}", object);
            return true;
        } catch (Exception e) {
            log.error("Updating categoryListing error", e);
            return false;
        }
    }

    @Override
    public boolean remove(Long id) {
        try {
            categoryListingDao.deleteById(id);
            log.info("Removing categoryListing: {}", id);
            return true;
        } catch (Exception e) {
            log.error("Removing categoryListing error", e);
            return false;
        }
    }

    @Override
    public List<CategoryListingDto> getAll() {
        try {
            List<CategoryListing> categoryListings = categoryListingDao.findAll();
            log.info("Getting all categoryListings {}", categoryListings);
            return categoryListings.stream().map(categoryListingMapper::toCategoryListingDto).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Getting all categoryListings error", e);
            return null;
        }
    }
}
