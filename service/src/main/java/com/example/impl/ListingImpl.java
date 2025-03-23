package com.example.impl;

import com.example.interfaces.*;
import com.example.mapper.ListingMapper;
import com.example.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingImpl implements ListingService {

    private final ListingDao listingDao;
    private final ListingMapper listingMapper;
    private final RatingDao ratingDao;
    private final PromotionDao promotionDao;
    private final CategoryDao categoryDao;
    private final CategoryListingDao categoryListingDao;

    @Override
    public ListingDto add(ListingDto object) {
        try {
            Listing listing = new Listing();
            BeanUtils.copyProperties(object, listing);
            listing.setId(null);
            listing.setUser(new User(object.getUser()));
            Listing savedListing = listingDao.save(listing);
            object.setId(savedListing.getId());
            log.info("Adding listing: {}", object);
        } catch (Exception e) {
            log.error("Add listing error:", e);
        }
        return object;
    }

    @Override
    public ListingDto get(Long id) {
        try {
            Listing listing = listingDao.findById(id).orElse(null);
            log.info("Get listing: {}", listing);
            return listingMapper.toListingDto(listing);
        } catch (Exception e) {
            log.error("Get listing error:", e);
            return null;
        }
    }

    @Override
    public boolean update(ListingDto object, Long id)  {
        try {
            Listing listing = listingDao.findById(id).orElse(null);
            if (listing == null) {
                log.warn("listing not found");
                return false;
            }
            object.setId(id);
            listingDao.save(listingMapper.toListing(object));
            log.info("Updating listing: {}", object);
            return true;
        } catch (Exception e) {
            log.error("Update listing error:", e);
            return false;
        }
    }

    @Override
    public boolean remove(Long id)  {
        try {
            listingDao.deleteById(id);
            log.info("Removing listing: {}", id);
            return true;
        } catch (Exception e) {
            log.error("Removing listing error:", e);
            return false;
        }
    }

    @Override
    public List<ListingDto> getAll()  {
        try {
            List<Listing> listings = listingDao.findAll();
            log.info("Get listing: {}", listings);
            return listings.stream().map(listingMapper::toListingDto).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Get listing error:", e);
            return null;
        }
    }

    public List<ListingDto> findListing(String searchString, String categoryName) {

        List<ListingDto> allListing = this.getAll();
        List<Category> categoryList = categoryDao.findAll();
        List<CategoryListing> categoryListingList = categoryListingDao.findAll();

        List<Long> categoryIds = categoryName == null ? List.of() : categoryList.stream()
                .filter(cat -> cat.getTitle().equalsIgnoreCase(categoryName))
                .map(Category::getId)
                .toList();

        List<Long> listingIds = categoryIds.isEmpty() ? List.of() : categoryListingList.stream()
                .filter(categoryListing -> categoryIds.contains(categoryListing.getCategory().getId()))
                .map(categoryListing -> categoryListing.getListing().getId())
                .toList();

        List<ListingDto> filteredListings = allListing.stream()
                .filter(listingDto -> listingDto.getTitle().toLowerCase().contains(searchString.toLowerCase()))
                .filter(listingDto -> categoryName == null || listingIds.contains(listingDto.getId()))
                .toList();

        log.info("Get sorted listings successful");
        return getSortedByRatingListings(filteredListings);
    }

    @Override
    public List<ListingDto> getSortedListingsByPromotion(List<ListingDto> filteredListings) {
        List<Promotion> promotionList = promotionDao.findAll();
        List<ListingDto> promotedListings = new ArrayList<>();
        List<ListingDto> nonPromotedListings = new ArrayList<>();

        for (ListingDto listing : filteredListings) {
            promotionList.stream()
                    .filter(promo -> promo.getListing().getId().equals(listing.getId()))
                    .max(Comparator.comparingDouble(Promotion::getPaymentAmount))
                    .ifPresentOrElse(
                            promo -> promotedListings.add(listing),
                            () -> nonPromotedListings.add(listing)
                    );
        }


        promotedListings.sort((l1, l2) -> {
            double payment1 = promotionList.stream()
                    .filter(promo -> promo.getListing().getId().equals(l1.getId()))
                    .mapToDouble(Promotion::getPaymentAmount)
                    .max().orElse(0);

            double payment2 = promotionList.stream()
                    .filter(promo -> promo.getListing().getId().equals(l2.getId()))
                    .mapToDouble(Promotion::getPaymentAmount)
                    .max().orElse(0);

            return Double.compare(payment2, payment1);
        });

        List<ListingDto> sortedListings = new ArrayList<>(promotedListings);
        sortedListings.addAll(nonPromotedListings);

        log.info("Get sorted listings successful by promotion");
        return sortedListings;
    }

    @Override
    public List<ListingDto> getSortedByRatingListings(List<ListingDto> filteredListings) {
        List<Rating> ratingList = ratingDao.findAll();

        Map<Long, List<Integer>> sellerRatingsMap = new HashMap<>();
        for (Rating rating : ratingList) {
            sellerRatingsMap.computeIfAbsent(rating.getUser().getId(), k -> new ArrayList<>()).add(rating.getRating());
        }

        Map<Long, Double> sellerAverageRatings = new HashMap<>();
        for (var entry : sellerRatingsMap.entrySet()) {
            double avg = entry.getValue().stream().mapToInt(Integer::intValue).average().orElse(0.0);
            sellerAverageRatings.put(entry.getKey(), avg);
        }

        log.info("Get sorted listings successful by rating");
        return filteredListings.stream()
                .sorted(Comparator.comparingDouble((ListingDto l) -> sellerAverageRatings.getOrDefault(l.getUser(), 0.0))
                        .reversed())
                .toList();
    }

    @Override
    public List<ListingDto> getSortedListingsByRatingAndPromotion(List<ListingDto> filteredListings) {
        List<Promotion> promotionList = promotionDao.findAll();
        List<Rating> ratingList = ratingDao.findAll();

        Map<Long, Double> promotionMap = new HashMap<>();
        Map<Long, List<Integer>> sellerRatingsMap = new HashMap<>();

        for (Promotion promo : promotionList) {
            promotionMap.put(promo.getListing().getId(),
                    Math.max(promotionMap.getOrDefault(promo.getListing().getId(), 0.0), promo.getPaymentAmount()));
        }

        for (Rating rating : ratingList) {
            sellerRatingsMap.computeIfAbsent(rating.getUser().getId(), k -> new ArrayList<>()).add(rating.getRating());
        }

        Map<Long, Double> sellerAverageRatings = new HashMap<>();
        for (var entry : sellerRatingsMap.entrySet()) {
            double avg = entry.getValue().stream().mapToInt(Integer::intValue).average().orElse(0.0);
            sellerAverageRatings.put(entry.getKey(), avg);
        }

        log.info("Get sorted listings successful by promotion and rating");
        return filteredListings.stream()
                .sorted(Comparator
                        .comparingDouble((ListingDto l) -> sellerAverageRatings.getOrDefault(l.getUser(), 0.0))
                        .reversed()
                        .thenComparing(Comparator.comparingDouble((ListingDto l) -> promotionMap.getOrDefault(l.getUser(), 0.0))
                                .reversed())
                )
                .toList();
    }
}
