package com.example.impl;

import com.example.interfaces.PromotionDao;
import com.example.interfaces.PromotionService;
import com.example.mapper.PromotionMapper;
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
public class PromotionImpl implements PromotionService {

    private final PromotionDao promotionDao;
    private final PromotionMapper promotionMapper;

    @Override
    public PromotionDto add(PromotionDto object)  {
        try {
            Promotion promotion = new Promotion();
            BeanUtils.copyProperties(object, promotion);
            promotion.setListing(new Listing(object.getListing()));
            promotion.setUser(new User(object.getUser()));
            Promotion savedPromotion = promotionDao.save(promotion);
            object.setId(savedPromotion.getId());
            log.info("Adding promotion: {}", object);
            return object;
        } catch (Exception e) {
            log.error("Add promotion error", e);
        }
        return object;
    }

    @Override
    public PromotionDto get(Long id)  {
        try {
            Promotion promotion = promotionDao.findById(id).orElse(null);
            log.info("Get promotion: {}", promotion);
            return promotionMapper.toPromotionDto(promotion);
        } catch (Exception e) {
            log.error("Get promotion error", e);
            return null;
        }
    }

    @Override
    public boolean update(PromotionDto object, Long id) {
        try {
            Promotion promotion = promotionDao.findById(id).orElse(null);
            if (promotion == null) {
                log.warn("promotion not found");
                return false;
            }
            promotionDao.save(promotionMapper.toPromotion(object));
            log.info("Updating promotion: {}", object);
            return true;
        } catch (Exception e) {
            log.error("Update promotion error", e);
            return false;
        }
    }

    @Override
    public boolean remove(Long id)  {
        try {
            promotionDao.deleteById(id);
            log.info("Removing promotion: {}", id);
            return true;
        } catch (Exception e) {
            log.error("Removing promotion error", e);
            return false;
        }
    }

    @Override
    public List<PromotionDto> getAll()  {
        try {
            List<Promotion> promotions = promotionDao.findAll();
            log.info("Get all promotions: {}", promotions);
            return promotions.stream().map(promotionMapper::toPromotionDto).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Get all promotion error", e);
            return null;
        }
    }
}
