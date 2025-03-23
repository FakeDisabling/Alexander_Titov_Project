package com.example.impl;

import com.example.interfaces.CategoryDao;
import com.example.interfaces.SaleHistoryDao;
import com.example.mapper.CategoryMapper;
import com.example.mapper.SaleHistoryMapper;
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
public class SaleHistoryImplTest {

    private SaleHistoryImpl saleHistoryService;
    private SaleHistoryDao saleHistoryDao;

    @BeforeEach
    void init() {
        saleHistoryDao = Mockito.spy(SaleHistoryDao.class);
        SaleHistoryMapper saleHistoryMapper = Mockito.mock(SaleHistoryMapper.class);
        saleHistoryService = new SaleHistoryImpl(saleHistoryDao, saleHistoryMapper);
    }

    @Test
    void testUnitDeleteById() {
        Long saleHistoryId = 1L;

        Mockito.doNothing()
                .when(saleHistoryDao)
                .deleteById(saleHistoryId);

        boolean isDeleted = saleHistoryService.remove(saleHistoryId);
        assertTrue(isDeleted);
    }

    @Test
    void testUnitGetAll() {
        List<SaleHistory> saleHistoryList = new ArrayList<>();
        Mockito.doReturn(saleHistoryList)
                .when(saleHistoryDao)
                .findAll();

        List<SaleHistoryDto> saleHistoryDtoList = saleHistoryService.getAll();
        assertEquals(saleHistoryList.size(), saleHistoryDtoList.size());
    }

    @Test
    void testUnitSave() {
        SaleHistoryDto saleHistoryDto = new SaleHistoryDto(1L, 1L, 1L, 1L, new Date());

        SaleHistory saleHistory = new SaleHistory(1L, new User(1L), new User(1L), new Listing(1L), new Date());

        Mockito.when(saleHistoryDao.findById(Mockito.anyLong())).thenReturn(Optional.of(saleHistory));
        Mockito.when(saleHistoryDao.save(Mockito.any(SaleHistory.class))).thenReturn(saleHistory);

        SaleHistoryDto savedPost = saleHistoryService.add(saleHistoryDto);
        assertNotNull(savedPost);
    }

    @Test
    void testUnitUpdate(){
        SaleHistoryDto saleHistoryDto = new SaleHistoryDto(1L, 1L, 1L, 1L, new Date());

        SaleHistory saleHistory = new SaleHistory(1L, new User(1L), new User(1L), new Listing(1L), new Date());

        Mockito.when(saleHistoryDao.findById(Mockito.anyLong())).thenReturn(Optional.of(saleHistory));
        Mockito.when(saleHistoryDao.save(Mockito.any(SaleHistory.class))).thenReturn(saleHistory);

        assertTrue(saleHistoryService.update(saleHistoryDto, saleHistoryDto.getId()));
    }

    @Test
    void testUnitGetById() {
        Long id = 1L;
        SaleHistory saleHistory = new SaleHistory();
        Mockito.when(saleHistoryDao.findById(id)).thenReturn(Optional.of(saleHistory));

        SaleHistoryDto result = saleHistoryService.get(id);
        assertNull(result);
    }
}
