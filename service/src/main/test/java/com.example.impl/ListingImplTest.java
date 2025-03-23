package com.example.impl;

import com.example.interfaces.*;
import com.example.mapper.ListingMapper;
import com.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ListingImplTest {

    private ListingImpl listingService;
    private ListingDao listingDao;
    private RatingDao ratingDao;
    private PromotionDao promotionDao;
    private CategoryDao categoryDao;
    private CategoryListingDao categoryListingDao;

    private List<ListingDto> sampleListings;
    private List<Promotion> samplePromotions;
    private List<Rating> sampleRatings;

    @BeforeEach
    void init() {
        listingDao = Mockito.spy(ListingDao.class);
        ratingDao = Mockito.spy(RatingDao.class);
        promotionDao = Mockito.spy(PromotionDao.class);
        categoryDao = Mockito.spy(CategoryDao.class);
        categoryListingDao = Mockito.spy(CategoryListingDao.class);
        ListingMapper listingMapper = Mockito.mock(ListingMapper.class);
        listingService = new ListingImpl(listingDao, listingMapper, ratingDao, promotionDao, categoryDao, categoryListingDao);

        sampleListings = Arrays.asList(
                new ListingDto(1L, 1L, "Item A", "Test item", 100.0, new Date()),
                new ListingDto(2L, 2L, "Item B", "Test item", 150.0, new Date()),
                new ListingDto(3L, 3L, "Item C", "Test item", 200.0, new Date())
        );

        samplePromotions = Arrays.asList(
                new Promotion(1L, new Listing(3L), new User(1L), 50.0, new Date(), new Date()),
                new Promotion(2L, new Listing(1L), new User(2L), 25.0, new Date(), new Date())
        );

        sampleRatings = Arrays.asList(
                new Rating(1L, new User(1L), new User(2L), 5),
                new Rating(2L, new User(2L), new User(3L), 3),
                new Rating(3L, new User(3L), new User(1L), 4)
        );
    }

    @Test
    void testUnitDeleteById() {
        Long listingId = 1L;

        Mockito.doNothing()
                .when(listingDao)
                .deleteById(listingId);

        boolean isDeleted = listingService.remove(listingId);
        assertTrue(isDeleted);
    }

    @Test
    void testUnitGetAll() {
        List<Listing> listingList = new ArrayList<>();
        Mockito.doReturn(listingList)
                .when(listingDao)
                .findAll();

        List<ListingDto> listingDtoList = listingService.getAll();
        assertEquals(listingList.size(), listingDtoList.size());
    }

    @Test
    void testUnitSave() {
        ListingDto listingDto = new ListingDto(1L, 1L, "test", "test", 25.0, new Date());

        Listing listing = new Listing(1L, new User(1L), "test", "test", 25.0, new Date());

        Mockito.when(listingDao.findById(Mockito.anyLong())).thenReturn(Optional.of(listing));
        Mockito.when(listingDao.save(Mockito.any(Listing.class))).thenReturn(listing);

        ListingDto savedListing = listingService.add(listingDto);
        assertNotNull(savedListing);
    }

    @Test
    void testUnitUpdate(){
        ListingDto listingDto = new ListingDto(1L, 1L, "test", "test", 25.0, new Date());

        Listing listing = new Listing(1L, new User(1L), "test", "test", 25.0, new Date());

        Mockito.when(listingDao.findById(Mockito.anyLong())).thenReturn(Optional.of(listing));
        Mockito.when(listingDao.save(Mockito.any(Listing.class))).thenReturn(listing);

        assertTrue(listingService.update(listingDto, listingDto.getId()));
    }

    @Test
    void testUnitGetById() {
        Long id = 1L;
        Listing listing = new Listing();
        Mockito.when(listingDao.findById(id)).thenReturn(Optional.of(listing));

        ListingDto result = listingService.get(id);
        assertNull(result);
    }

    @Test
    void testUnitGetSortedListingsByPromotion() {
        Mockito.when(promotionDao.findAll()).thenReturn(samplePromotions);

        List<ListingDto> sortedListings = listingService.getSortedListingsByPromotion(sampleListings);

        assertEquals(3, sortedListings.size());
        assertEquals(3L, sortedListings.get(0).getId());
        assertEquals(1L, sortedListings.get(1).getId());
        assertEquals(2L, sortedListings.get(2).getId());
    }

    @Test
    void testUnitGetSortedByRatingListings() {
        Mockito.when(ratingDao.findAll()).thenReturn(sampleRatings);

        List<ListingDto> sortedListings = listingService.getSortedByRatingListings(sampleListings);

        assertEquals(3, sortedListings.size());
        assertEquals(1L, sortedListings.get(0).getId());
        assertEquals(3L, sortedListings.get(1).getId());
        assertEquals(2L, sortedListings.get(2).getId());
    }

    @Test
    void testUnitGetSortedListingsByRatingAndPromotion() {
        Mockito.when(promotionDao.findAll()).thenReturn(samplePromotions);
        Mockito.when(ratingDao.findAll()).thenReturn(sampleRatings);

        List<ListingDto> sortedListings = listingService.getSortedListingsByRatingAndPromotion(sampleListings);

        assertEquals(3, sortedListings.size());
        assertEquals(1L, sortedListings.get(0).getId());
    }
}
