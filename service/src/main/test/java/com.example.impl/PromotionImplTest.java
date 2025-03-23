package com.example.impl;

import com.example.interfaces.CategoryDao;
import com.example.interfaces.PromotionDao;
import com.example.mapper.CategoryMapper;
import com.example.mapper.PromotionMapper;
import com.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PromotionImplTest {

    private PromotionImpl promotionService;
    private PromotionDao promotionDao;

    @BeforeEach
    void init() {
        promotionDao = Mockito.spy(PromotionDao.class);
        PromotionMapper promotionMapper = Mockito.mock(PromotionMapper.class);
        promotionService = new PromotionImpl(promotionDao, promotionMapper);
    }

    @Test
    void testUnitDeleteById() {
        Long promotionId = 1L;

        Mockito.doNothing()
                .when(promotionDao)
                .deleteById(promotionId);

        boolean isDeleted = promotionService.remove(promotionId);
        assertTrue(isDeleted);
    }

    @Test
    void testUnitGetAll() {
        List<Promotion> promotionList = new ArrayList<>();
        Mockito.doReturn(promotionList)
                .when(promotionDao)
                .findAll();

        List<PromotionDto> promotionDtoList = promotionService.getAll();
        assertEquals(promotionList.size(), promotionDtoList.size());
    }

    @Test
    void testUnitSave() {
        PromotionDto promotionDto = new PromotionDto(1L, 1L, 1L, 25.0, new Date(), new Date());

        Promotion promotion = new Promotion(1L, new Listing(1L), new User(1L), 25.0, new Date(), new Date());

        Mockito.when(promotionDao.findById(Mockito.anyLong())).thenReturn(Optional.of(promotion));
        Mockito.when(promotionDao.save(Mockito.any(Promotion.class))).thenReturn(promotion);

        PromotionDto savedPost = promotionService.add(promotionDto);
        assertNotNull(savedPost);
    }

    @Test
    void testUnitUpdate(){
        PromotionDto promotionDto = new PromotionDto(1L, 1L, 1L, 25.0, new Date(), new Date());

        Promotion promotion = new Promotion(1L, new Listing(1L), new User(1L), 25.0, new Date(), new Date());

        Mockito.when(promotionDao.findById(Mockito.anyLong())).thenReturn(Optional.of(promotion));
        Mockito.when(promotionDao.save(Mockito.any(Promotion.class))).thenReturn(promotion);

        assertTrue(promotionService.update(promotionDto, promotionDto.getId()));
    }

    @Test
    void testUnitGetById() {
        Long id = 1L;
        Promotion category = new Promotion();
        Mockito.when(promotionDao.findById(id)).thenReturn(Optional.of(category));

        PromotionDto result = promotionService.get(id);
        assertNull(result);
    }
}
