package com.example.impl;

import com.example.interfaces.RatingDao;
import com.example.interfaces.RatingService;
import com.example.mapper.RatingMapper;
import com.example.model.Rating;
import com.example.model.RatingDto;
import com.example.model.Role;
import com.example.model.User;
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
public class RatingImpl implements RatingService {

    private final RatingDao ratingDao;
    private final RatingMapper ratingMapper;

    @Override
    public RatingDto add(RatingDto object)  {
        try {
            Rating rating = new Rating();
            BeanUtils.copyProperties(object, rating);
            rating.setReviewer(new User(object.getReviewer()));
            rating.setUser(new User(object.getUser()));
            Rating savedRating = ratingDao.save(rating);
            object.setId(savedRating.getId());
            log.info("Adding rating: {}", object);
            return object;
        } catch (Exception e) {
            log.error("Add rating error:", e);
        }
        return object;
    }

    @Override
    public RatingDto get(Long id) {
        try {
            Rating rating = ratingDao.findById(id).orElse(null);
            log.info("Get rating: {}", rating);
            return ratingMapper.toRatingDto(rating);
        } catch (Exception e) {
            log.error("Get rating error:", e);
            return null;
        }
    }

    @Override
    public boolean update(RatingDto object, Long id)  {
        try {
            Rating rating = ratingDao.findById(id).orElse(null);
            if (rating == null) {
                log.warn("rating not found");
                return false;
            }
            ratingDao.save(ratingMapper.toRating(object));
            log.info("Updating rating: {}", object);
            return true;
        } catch (Exception e) {
            log.error("Update rating error:", e);
            return false;
        }
    }

    @Override
    public boolean remove(Long id) {
        try {
            ratingDao.deleteById(id);
            log.info("Removing rating: {}", id);
            return true;
        } catch (Exception e) {
            log.error("Removing rating error:", e);
            return false;
        }
    }

    @Override
    public List<RatingDto> getAll()  {
        try {
            List<Rating> ratings = ratingDao.findAll();
            log.info("Get all ratings: {}", ratings);
            return ratings.stream().map(ratingMapper::toRatingDto).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Get all ratings error:", e);
            return null;
        }
    }
}
