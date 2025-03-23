package com.example.mapper;

import com.example.model.Listing;
import com.example.model.SaleHistory;
import com.example.model.SaleHistoryDto;
import com.example.model.User;
import org.springframework.stereotype.Component;

@Component
public class SaleHistoryMapper {

    public SaleHistory toSaleHistory(SaleHistoryDto dto) {
        if (dto == null) {
            return null;
        }
        return new SaleHistory(
                dto.getId(),
                new User(dto.getSeller()),
                new User(dto.getBuyer()),
                new Listing(dto.getListing()),
                dto.getSaleDate()
        );
    }

    public SaleHistoryDto toSaleHistoryDto(SaleHistory entity) {
        if (entity == null) {
            return null;
        }
        return new SaleHistoryDto(
                entity.getId(),
                entity.getSeller().getId(),
                entity.getBuyer().getId(),
                entity.getListing().getId(),
                entity.getSaleDate()
        );
    }
}
