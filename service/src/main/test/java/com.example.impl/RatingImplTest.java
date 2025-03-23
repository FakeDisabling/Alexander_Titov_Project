package com.example.impl;

import com.example.interfaces.CategoryDao;
import com.example.interfaces.RatingDao;
import com.example.mapper.CategoryMapper;
import com.example.mapper.RatingMapper;
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
public class RatingImplTest {
    private RatingImpl ratingService;
    private RatingDao ratingDao;

    @BeforeEach
    void init() {
        ratingDao = Mockito.spy(RatingDao.class);
        RatingMapper ratingMapper = Mockito.mock(RatingMapper.class);
        ratingService = new RatingImpl(ratingDao, ratingMapper);
    }

    @Test
    void testUnitDeleteById() {
        Long ratingId = 1L;

        Mockito.doNothing()
                .when(ratingDao)
                .deleteById(ratingId);

        boolean isDeleted = ratingService.remove(ratingId);
        assertTrue(isDeleted);
    }

    @Test
    void testUnitGetAll() {
        List<Rating> ratingList = new ArrayList<>();
        Mockito.doReturn(ratingList)
                .when(ratingDao)
                .findAll();

        List<RatingDto> ratingDtoList = ratingService.getAll();
        assertEquals(ratingList.size(), ratingDtoList.size());
    }

    @Test
    void testUnitSave() {
        RatingDto ratingDto = new RatingDto(1L, 1L, 1L, 5);

        Rating rating = new Rating(1L, new User(1L), new User(1L), 5);

        Mockito.when(ratingDao.findById(Mockito.anyLong())).thenReturn(Optional.of(rating));
        Mockito.when(ratingDao.save(Mockito.any(Rating.class))).thenReturn(rating);

        RatingDto savedRating = ratingService.add(ratingDto);
        assertNotNull(savedRating);
    }

    @Test
    void testUnitUpdate(){
        RatingDto ratingDto = new RatingDto(1L, 1L, 1L, 5);

        Rating rating = new Rating(1L, new User(1L), new User(1L), 5);

        Mockito.when(ratingDao.findById(Mockito.anyLong())).thenReturn(Optional.of(rating));
        Mockito.when(ratingDao.save(Mockito.any(Rating.class))).thenReturn(rating);

        assertTrue(ratingService.update(ratingDto, ratingDto.getId()));
    }

    @Test
    void testUnitGetById() {
        Long id = 1L;
        Rating rating = new Rating();
        Mockito.when(ratingDao.findById(id)).thenReturn(Optional.of(rating));

        RatingDto result = ratingService.get(id);
        assertNull(result);
    }

}
