package com.example.impl;

import com.example.interfaces.SaleHistoryDao;
import com.example.interfaces.SaleHistoryService;
import com.example.mapper.SaleHistoryMapper;
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
public class SaleHistoryImpl implements SaleHistoryService {

    private final SaleHistoryDao saleHistoryDao;
    private final SaleHistoryMapper saleHistoryMapper;

    @Override
    public SaleHistoryDto add(SaleHistoryDto object) {
        try {
            SaleHistory saleHistory = new SaleHistory();
            BeanUtils.copyProperties(object, saleHistory);
            saleHistory.setBuyer(new User(object.getBuyer()));
            saleHistory.setSeller(new User(object.getSeller()));
            saleHistory.setListing(new Listing(object.getListing()));
            SaleHistory savedSaleHistory = saleHistoryDao.save(saleHistory);
            object.setId(savedSaleHistory.getId());
            log.info("Adding saleHistory: {}", object);
            return object;
        } catch (Exception e) {
            log.error("Add saleHistory error:", e);
        }
        return object;
    }

    @Override
    public SaleHistoryDto get(Long id)  {
        try {
            SaleHistory saleHistory = saleHistoryDao.findById(id).orElse(null);
            log.info("Get saleHistory: {}", saleHistory);
            return saleHistoryMapper.toSaleHistoryDto(saleHistory);
        } catch (Exception e) {
            log.error("Get saleHistory error:", e);
            return null;
        }
    }

    @Override
    public boolean update(SaleHistoryDto object, Long id)  {
        try {
            SaleHistory saleHistory = saleHistoryDao.findById(id).orElse(null);
            if (saleHistory == null) {
                log.warn("saleHistory not found");
                return false;
            }
            saleHistoryDao.save(saleHistoryMapper.toSaleHistory(object));
            log.info("Updating saleHistory: {}", object);
            return true;
        } catch (Exception e) {
            log.error("Updating saleHistory error:", e);
            return false;
        }
    }

    @Override
    public boolean remove(Long id) {
        try {
            saleHistoryDao.deleteById(id);
            log.info("Removing saleHistory: {}", id);
            return true;
        } catch (Exception e) {
            log.error("Removing saleHistory error:", e);
            return false;
        }
    }

    @Override
    public List<SaleHistoryDto> getAll()  {
        try {
            List<SaleHistory> saleHistoryList = saleHistoryDao.findAll();
            log.info("Get all saleHistory: {}", saleHistoryList);
            return saleHistoryList.stream().map(saleHistoryMapper::toSaleHistoryDto).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Get all saleHistory error:", e);
            return null;
        }
    }

    @Override
    public List<Long> findBySellerId(Long sellerId) {
        try {
            List<Long> saleHistorylist = saleHistoryDao.findBySellerId(sellerId);
            log.info("Get all saleHistory: {}", saleHistorylist);
            return saleHistorylist;
        } catch (Exception e) {
            log.error("Get all saleHistory error:", e);
            return null;
        }
    }
}
