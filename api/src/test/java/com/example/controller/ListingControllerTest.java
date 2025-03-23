package com.example.controller;

import com.example.interfaces.ListingService;
import com.example.interfaces.UserService;
import com.example.model.ListingDto;
import com.example.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ListingControllerTest {

    @Mock
    private ListingService listingService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ListingController listingController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(listingController).build();
    }

    @Test
    void testGetAllListing() throws Exception {
        List<ListingDto> listings = Arrays.asList(
                new ListingDto(1L, 1L, "Title 1", "Description 1", 100.0, new Date()),
                new ListingDto(2L, 1L, "Title 2", "Description 2", 200.0, new Date())
        );

        when(listingService.getSortedListingsByPromotion(any())).thenReturn(listings);
        when(listingService.getAll()).thenReturn(listings);

        mockMvc.perform(get("/listing"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {"id":1,"title":"Title 1","description":"Description 1","price":100.0,"user":1},
                            {"id":2,"title":"Title 2","description":"Description 2","price":200.0,"user":1}
                        ]
                        """));
    }

    @Test
    void testAddListing() throws Exception {
        UserDto userDto = new UserDto(1L, "testUser", "test@example.com", "+123456789", 1L, "password");
        ListingDto listingDto = new ListingDto(1L, 1L, "New Listing", "Description", 150.0, new Date());

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");

        when(userService.findByUsername("testUser")).thenReturn(Optional.of(userDto));
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(post("/listing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "userId":1,
                                    "title": "New Listing",
                                    "description": "Description",
                                    "price": 150.0
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Listing added"));

        verify(listingService, times(1)).add(any(ListingDto.class));
    }

    @Test
    void testUpdateListing_Success() throws Exception {
        ListingDto listingDto = new ListingDto(1L, 1L, "Title", "Desc", 100.0, new Date());
        UserDto userDto = new UserDto(1L, "testUser", "email", "+123456789", 1L, "password");

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(authentication.getName()).thenReturn("testUser");

        when(userService.findByUsername("testUser")).thenReturn(Optional.of(userDto));
        when(listingService.get(1L)).thenReturn(listingDto);

        String requestJson = """
                {
                   "title": "Updated Title",
                   "description": "Updated Desc",
                   "price": 150.0
                }
                """;

        mockMvc.perform(put("/listing/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Listing updated"));
    }

    @Test
    void testDeleteListing_Success() throws Exception {
        ListingDto listingDto = new ListingDto(1L, 1L, "Title", "Desc", 100.0, new Date());
        UserDto userDto = new UserDto(1L, "testUser", "email", "+123456789", 1L, "password");

        when(userService.findByUsername("testUser")).thenReturn(Optional.of(userDto));
        when(listingService.get(1L)).thenReturn(listingDto);

        mockMvc.perform(delete("/listing/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Listing deleted"));
    }
}
