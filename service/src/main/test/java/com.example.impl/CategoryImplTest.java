package com.example.impl;

import com.example.interfaces.CategoryDao;
import com.example.mapper.CategoryMapper;
import com.example.model.Category;
import com.example.model.CategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryImplTest {

    private CategoryImpl categoryService;
    private CategoryDao categoryDao;

    @BeforeEach
    void init() {
        categoryDao = Mockito.spy(CategoryDao.class);
        CategoryMapper categoryMapper = Mockito.mock(CategoryMapper.class);
        categoryService = new CategoryImpl(categoryMapper, categoryDao);
    }

    @Test
    void testUnitDeleteById() {
        Long categoryId = 1L;

        Mockito.doNothing()
                .when(categoryDao)
                .deleteById(categoryId);

        boolean isDeleted = categoryService.remove(categoryId);
        assertTrue(isDeleted);
    }

    @Test
    void testUnitGetAll() {
        List<Category> categoriesList = new ArrayList<>();
        Mockito.doReturn(categoriesList)
                .when(categoryDao)
                .findAll();

        List<CategoryDto> categoryDtosList = categoryService.getAll();
        assertEquals(categoriesList.size(), categoryDtosList.size());
    }

    @Test
    void testUnitSave() {
        CategoryDto categoryDto = new CategoryDto(1L, "test");

        Category category = new Category(1L, "test");

        Mockito.when(categoryDao.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
        Mockito.when(categoryDao.save(Mockito.any(Category.class))).thenReturn(category);

        CategoryDto savedPost = categoryService.add(categoryDto);
        assertNotNull(savedPost);
    }

    @Test
    void testUnitUpdate(){
        CategoryDto categoryDto = new CategoryDto(1L, "test");

        Category category = new Category(1L, "test");

        Mockito.when(categoryDao.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
        Mockito.when(categoryDao.save(Mockito.any(Category.class))).thenReturn(category);

        assertTrue(categoryService.update(categoryDto, categoryDto.getId()));
    }

    @Test
    void testUnitGetById() {
        Long id = 1L;
        Category category = new Category();
        Mockito.when(categoryDao.findById(id)).thenReturn(Optional.of(category));

        CategoryDto result = categoryService.get(id);
        assertNull(result);
    }
}
