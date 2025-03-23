package com.example.mapper;

import com.example.model.Listing;
import com.example.model.Promotion;
import com.example.model.PromotionDto;
import com.example.model.User;
import org.springframework.stereotype.Component;

@Component
public class PromotionMapper {

    public Promotion toPromotion(PromotionDto dto) {
        if (dto == null) {
            return null;
        }
        return new Promotion(
                dto.getId(),
                new Listing(dto.getListing()),
                new User(dto.getUser()),
                dto.getPaymentAmount(),
                dto.getStartDate(),
                dto.getEndDate()
        );
    }

    public PromotionDto toPromotionDto(Promotion entity) {
        if (entity == null) {
            return null;
        }
        return new PromotionDto(
                entity.getId(),
                entity.getListing().getId(),
                entity.getUser().getId(),
                entity.getPaymentAmount(),
                entity.getStartDate(),
                entity.getEndDate()
        );
    }
}
