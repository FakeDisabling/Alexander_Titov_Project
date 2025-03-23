package com.example.controller;

import com.example.interfaces.MessageService;
import com.example.interfaces.UserService;
import com.example.model.MessageDto;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @Mock
    private MessageService messageService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private MessageController messageController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testGetMessageStory_Success() throws Exception {
        UserDto user = new UserDto(1L, "user1", "user1@example.com", "+123456789", 1L, "password");
        List<MessageDto> messages = Arrays.asList(
                new MessageDto(1L, 1L, 2L, "Hello", new Date()),
                new MessageDto(2L, 2L, 1L, "Hi!", new Date())
        );

        when(authentication.getName()).thenReturn("user1");
        when(userService.findByUsername("user1")).thenReturn(Optional.of(user));
        when(messageService.findMessageByUserId(1L, 2L)).thenReturn(messages);

        mockMvc.perform(get("/message/1/2"))
                .andExpect(status().isOk());
    }

    @Test
    void testSendMessage_Success() throws Exception {
        UserDto sender = new UserDto(1L, "user1", "user1@example.com", "+123456789", 1L, "password");
        String requestJson = """
                {
                    "receiver": 2,
                    "content": "Hello!"
                }
                """;

        when(authentication.getName()).thenReturn("user1");
        when(userService.findByUsername("user1")).thenReturn(Optional.of(sender));
        when(messageService.add(any(MessageDto.class))).thenReturn(new MessageDto(1L, 1L, 2L, "Hello!", new Date()));

        mockMvc.perform(post("/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Message sended successfully"));
    }
}