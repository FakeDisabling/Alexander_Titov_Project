package com.example.controller;

import com.example.interfaces.UserService;
import com.example.model.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/profile")
@RestController
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (userService.findByUsername(userDto.getUsername()).isPresent()
                && !authentication.getName().equals(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with this username already exists");
        }

        Optional<UserDto> user = userService.findByUsername(authentication.getName());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRole(userDto.getRole());
        user.ifPresent(dto -> userService.update(userDto, user.get().getId()));

        return ResponseEntity.status(HttpStatus.OK)
                .body("User " + userDto.getUsername() + " is updates successfully");
    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long id) {
        UserDto currentUser = userService.get(id);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User not found");
        }

        if (userService.findByUsername(userDto.getUsername()).isPresent()
                && !currentUser.getUsername().equals(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with this username already exists");
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.update(userDto, id);

        return ResponseEntity.status(HttpStatus.OK)
                .body("User " + userDto.getUsername() + " is updates successfully");
    }
}
