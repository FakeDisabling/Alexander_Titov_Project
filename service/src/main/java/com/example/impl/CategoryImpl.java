package com.example.impl;

import com.example.interfaces.CategoryDao;
import com.example.interfaces.CategoryService;
import com.example.mapper.CategoryMapper;
import com.example.model.Category;
import com.example.model.CategoryDto;
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
public class CategoryImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryDao categoryDao;

    @Override
    public CategoryDto add(CategoryDto object) {
        try {
            Category category = new Category();
            BeanUtils.copyProperties(object, category);
            Category savedCategory = categoryDao.save(category);
            object.setId(savedCategory.getId());
            log.info("Adding category: {}", object);
            return object;
        } catch (Exception e) {
            log.error("Add category error:", e);
            return null;
        }
    }

    @Override
    public CategoryDto get(Long id) {
        try {
            Category category = categoryDao.findById(id).orElse(null);
            log.info("Get category: {}", category);
            return categoryMapper.toCategoryDto(category);
        } catch (Exception e) {
            log.error("Get category error:", e);
            return null;
        }
    }

    @Override
    public boolean update(CategoryDto object, Long id) {
        try {
            Category category = categoryDao.findById(id).orElse(null);
            if (category == null) {
                log.warn("category not found");
                return false;
            }
            categoryDao.save(categoryMapper.toCategory(object));
            log.info("Updating category: {}", object);
            return true;
        } catch (Exception e) {
            log.error("Update category error:", e);
            return false;
        }
    }

    @Override
    public boolean remove(Long id){
        try {
            categoryDao.deleteById(id);
            log.info("Removing category: {}", id);
            return true;
        } catch (Exception e) {
            log.error("Removing category error:", e);
            return false;
        }
    }

    @Override
    public List<CategoryDto> getAll() {
        try {
            List<Category> categoryList = categoryDao.findAll();
            log.info("Get all category: {}", categoryList);
            return categoryList.stream().map(categoryMapper::toCategoryDto).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Get all category error:", e);
            return null;
        }
    }
}
