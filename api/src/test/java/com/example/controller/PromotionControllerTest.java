package com.example.controller;

import com.example.interfaces.ListingService;
import com.example.interfaces.PromotionService;
import com.example.interfaces.UserService;
import com.example.model.ListingDto;
import com.example.model.PromotionDto;
import com.example.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PromotionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PromotionService promotionService;

    @Mock
    private ListingService listingService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private PromotionController promotionController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(promotionController).build();
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testAddPromotion_Success() throws Exception {
        String username = "testUser";
        Long userId = 1L;
        Long listingId = 10L;

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setUsername(username);

        ListingDto listingDto = new ListingDto();
        listingDto.setId(listingId);
        listingDto.setUser(userId);

        PromotionDto promotionDto = new PromotionDto();

        when(authentication.getName()).thenReturn(username);
        when(userService.findByUsername(username)).thenReturn(Optional.of(userDto));
        when(listingService.get(listingId)).thenReturn(listingDto);
        when(promotionService.add(any(PromotionDto.class))).thenReturn(promotionDto);

        String requestJson = """
                {
                    "paymentAmount": 30
                }
                """;

        mockMvc.perform(post("/promotion/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Promoted is successful"));

        verify(promotionService, times(1)).add(any(PromotionDto.class));
    }
}
