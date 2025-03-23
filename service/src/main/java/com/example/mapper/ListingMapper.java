package com.example.mapper;

import com.example.model.Category;
import com.example.model.Listing;
import com.example.model.ListingDto;
import com.example.model.User;
import org.springframework.stereotype.Component;

@Component
public class ListingMapper {

    public Listing toListing(ListingDto dto) {
        if (dto == null) {
            return null;
        }
        return new Listing(
                dto.getId(),
                new User(dto.getUser()),
                dto.getTitle(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getCreatedAt()
        );
    }

    public ListingDto toListingDto(Listing entity) {
        if (entity == null) {
            return null;
        }
        return new ListingDto(
                entity.getId(),
                entity.getUser().getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getCreatedAt()
        );
    }
}
