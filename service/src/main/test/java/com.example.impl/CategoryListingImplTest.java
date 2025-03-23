package com.example.impl;

import com.example.interfaces.CategoryDao;
import com.example.interfaces.CategoryListingDao;
import com.example.mapper.CategoryListingMapper;
import com.example.mapper.CategoryMapper;
import com.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryListingImplTest {

    private CategoryListingImpl categoryListingService;
    private CategoryListingDao categoryListingDao;

    @BeforeEach
    void init() {
        categoryListingDao = Mockito.spy(CategoryListingDao.class);
        CategoryListingMapper categoryListingMapper = Mockito.mock(CategoryListingMapper.class);
        categoryListingService = new CategoryListingImpl(categoryListingMapper, categoryListingDao);
    }

    @Test
    void testUnitDeleteById() {
        Long categoryListingId = 1L;

        Mockito.doNothing()
                .when(categoryListingDao)
                .deleteById(categoryListingId);

        boolean isDeleted = categoryListingService.remove(categoryListingId);
        assertTrue(isDeleted);
    }

    @Test
    void testUnitGetAll() {
        List<CategoryListing> categoryListingList = new ArrayList<>();
        Mockito.doReturn(categoryListingList)
                .when(categoryListingDao)
                .findAll();

        List<CategoryListingDto> categoryListingDtos = categoryListingService.getAll();
        assertEquals(categoryListingList.size(), categoryListingDtos.size());
    }

    @Test
    void testUnitSave() {
        CategoryListingDto categoryListingDto = new CategoryListingDto();
        categoryListingDto.setCategory(1L);
        categoryListingDto.setListing(1L);

        CategoryListing categoryListing = new CategoryListing();
        categoryListing.setCategory(new Category(1L));
        categoryListing.setListing(new Listing(1L));

        Mockito.when(categoryListingDao.findById(Mockito.anyLong())).thenReturn(Optional.of(categoryListing));
        Mockito.when(categoryListingDao.save(Mockito.any(CategoryListing.class))).thenReturn(categoryListing);

        CategoryListingDto savedCategoryListing = categoryListingService.add(categoryListingDto);
        assertNotNull(savedCategoryListing);
    }

    @Test
    void testUnitUpdate(){
        CategoryListingDto categoryListingDto = new CategoryListingDto();
        categoryListingDto.setId(1L);
        categoryListingDto.setCategory(1L);
        categoryListingDto.setListing(1L);

        CategoryListing categoryListing = new CategoryListing();
        categoryListingDto.setId(1L);
        categoryListing.setCategory(new Category(1L));
        categoryListing.setListing(new Listing(1L));

        Mockito.when(categoryListingDao.findById(Mockito.anyLong())).thenReturn(Optional.of(categoryListing));
        Mockito.when(categoryListingDao.save(Mockito.any(CategoryListing.class))).thenReturn(categoryListing);

        assertTrue(categoryListingService.update(categoryListingDto, categoryListingDto.getId()));
    }

    @Test
    void testUnitGetById() {
        Long id = 1L;
        CategoryListing categoryListing = new CategoryListing();
        Mockito.when(categoryListingDao.findById(id)).thenReturn(Optional.of(categoryListing));

        CategoryListingDto result = categoryListingService.get(id);
        assertNull(result);
    }
}
