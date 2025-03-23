package com.example.controller;

import com.example.interfaces.CommentService;
import com.example.interfaces.ListingService;
import com.example.interfaces.UserService;
import com.example.model.CommentDto;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private ListingService listingService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testGetCommentsFromListing() throws Exception {
        List<CommentDto> comments = Arrays.asList(
                new CommentDto(1L, 1L, 1L, "Great listing!", new Date()),
                new CommentDto(2L, 2L, 1L, "Nice place!", new Date())
        );

        when(commentService.findByListingId(1L)).thenReturn(comments);

        mockMvc.perform(get("/comment/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {"id":1,"listing":1,"user":1,"content":"Great listing!"},
                            {"id":2,"listing":1,"user":2,"content":"Nice place!"}
                        ]
                        """));
    }

    @Test
    void testAddComment_Success() throws Exception {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");

        UserDto userDto = new UserDto(1L, "testUser", "test@example.com", "+123456789", 1L, "password");
        when(userService.findByUsername("testUser")).thenReturn(Optional.of(userDto));

        when(listingService.get(1L)).thenReturn(new ListingDto());

        String requestJson = """
                {
                    "listingId": 1,
                    "userId": 1,
                    "content": "Amazing place!"
                }
                """;

        mockMvc.perform(post("/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment added"));

        verify(commentService, times(1)).add(any(CommentDto.class));
    }
}
