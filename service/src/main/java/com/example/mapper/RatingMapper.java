package com.example.mapper;

import com.example.model.Rating;
import com.example.model.RatingDto;
import com.example.model.User;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper {

    public Rating toRating(RatingDto dto) {
        if (dto == null) {
            return null;
        }
        return new Rating(
                dto.getId(),
                new User(dto.getUser()),
                new User(dto.getReviewer()),
                dto.getRating()
        );
    }

    public RatingDto toRatingDto(Rating entity) {
        if (entity == null) {
            return null;
        }
        return new RatingDto(
                entity.getId(),
                entity.getUser().getId(),
                entity.getReviewer().getId(),
                entity.getRating()
        );
    }
}
