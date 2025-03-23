package com.example.controller;

import com.example.interfaces.UserService;
import com.example.model.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        SecurityContextHolder.setContext(securityContext);
    }


    @Test
    void testUpdateUser_Success() throws Exception {
        UserDto userDto = new UserDto(1L, "user1", "user1@example.com", "+123456789", 1L, "password");


        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user1");

        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userService.findByUsername("user1")).thenReturn(Optional.of(userDto));

        String requestJson = """
                {
                   "email": "user1@example.com",
                   "password": "password",
                   "phone": "+123456789",
                   "role": 1,
                   "username": "user1"
                }
                """;

        mockMvc.perform(put("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("User " + userDto.getUsername()  + " is updates successfully"));
    }

    @Test
    void testAdminUpdateUser_Success() throws Exception {
        UserDto userDto = new UserDto(1L, "user1", "user1@example.com", "+123456789", 1L, "password");

        when(userService.get(1L)).thenReturn(userDto);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userService.findByUsername("user1")).thenReturn(Optional.of(userDto));

        String requestJson = """
            {
                   "email": "user1@example.com",
                   "password": "password",
                   "phone": "+123456789",
                   "role": 1,
                   "username": "user1"
            }
            """;

        mockMvc.perform(put("/profile/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("User " + userDto.getUsername()  + " is updates successfully"));
    }
}
