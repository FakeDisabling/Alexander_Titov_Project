package com.example.interfaces;

import com.example.model.ListingDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ListingService extends InterfaceService<ListingDto> {

    List<ListingDto> getSortedListingsByPromotion(List<ListingDto> filteredListings);

    List<ListingDto> getSortedByRatingListings(List<ListingDto> filteredListings);

    List<ListingDto> getSortedListingsByRatingAndPromotion(List<ListingDto> filteredListings);

    List<ListingDto> findListing(String searchString, String categoryName);
}
