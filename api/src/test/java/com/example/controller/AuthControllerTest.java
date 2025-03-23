package com.example.controller;

import com.example.interfaces.UserService;
import com.example.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testRegister_Success() throws Exception {
        UserDto userDto = new UserDto(1L, "test", "test@example.com", "+1234567890", 1L, "password");

        when(userService.findByUsername(userDto.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userService.add(any(UserDto.class))).thenReturn(userDto);

        String requestJson = """
            {
                "username": "test",
                "password": "password",
                "email": "test@example.com",
                "phone": "+1234567890",
                "role": 1
            }
            """;

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("User " + userDto.getUsername() + " is registered successfully"));
    }

    @Test
    void testRegister_UserAlreadyExists() throws Exception {
        UserDto existingUser = new UserDto(1L, "test", "test@example.com", "+testtestte", 1L, "password");

        when(userService.findByUsername("testUser")).thenReturn(Optional.of(existingUser));

        String requestJson = """
            {
                "username": "testUser",
                "password": "password",
                "email": "test@example.com",
                "phone": "+1234567890",
                "role": 1
            }
            """;

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User with this username already exists"));
    }
}
