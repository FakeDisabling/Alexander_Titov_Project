package com.example.controller;

import com.example.interfaces.UserService;
import com.example.model.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) {
        if (userService.findByUsername(userDto.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with this username already exists");
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRole(1L);

        UserDto user = userService.add(userDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User " + user.getUsername() + " is registered successfully");
    }
}
