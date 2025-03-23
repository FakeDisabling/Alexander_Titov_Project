package com.example.controller;

import com.example.interfaces.ListingService;
import com.example.interfaces.SaleHistoryService;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class SaleHistoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SaleHistoryService saleHistoryService;

    @Mock
    private ListingService listingService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SaleHistoryController saleHistoryController;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(saleHistoryController).build();
    }


    @Test
    void testGetSaleHistoryChooseUser_Success() throws Exception {
        UserDto user = new UserDto(1L, "user1", "user1@example.com", "+123456789", 1L, "password");
        List<Long> saleHistory = List.of(101L, 102L);
        List<ListingDto> listings = List.of(
                new ListingDto(101L, 1L, "Title 1", "Description 1", 100.0, new Date()),
                new ListingDto(102L, 2L, "Title 2", "Description 2", 200.0, new Date()),
                new ListingDto(103L, 3L, "Title 2", "Description 2", 200.0, new Date())
        );

        when(userService.get(1L)).thenReturn(user);
        when(saleHistoryService.findBySellerId(1L)).thenReturn(saleHistory);
        when(listingService.getAll()).thenReturn(listings);

        mockMvc.perform(get("/saleHistory/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(101))
                .andExpect(jsonPath("$[1].id").value(102));
    }

    @Test
    void testGetSaleHistory_Success() throws Exception {
        UserDto user = new UserDto(2L, "user2", "user2@example.com", "+987654321", 2L, "password");
        List<Long> saleHistory = List.of(201L, 202L);
        List<ListingDto> listings = List.of(
                new ListingDto(201L, 1L, "Title 1", "Description 1", 100.0, new Date()),
                new ListingDto(202L, 2L, "Title 2", "Description 2", 200.0, new Date()),
                new ListingDto(203L, 3L, "Title 2", "Description 2", 200.0, new Date())
        );


        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(authentication.getName()).thenReturn("user2");
        when(userService.findByUsername("user2")).thenReturn(Optional.of(user));
        when(saleHistoryService.findBySellerId(2L)).thenReturn(saleHistory);
        when(listingService.getAll()).thenReturn(listings);

        mockMvc.perform(get("/saleHistory")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(201))
                .andExpect(jsonPath("$[1].id").value(202));
    }
}
