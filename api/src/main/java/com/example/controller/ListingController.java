package com.example.controller;

import com.example.interfaces.*;
import com.example.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/listing")
@RestController
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;
    private final UserService userService;

    @GetMapping
    public List<ListingDto> getAllListing() {
        return listingService.getSortedListingsByPromotion(listingService.getAll());
    }

    @GetMapping("/{searchString}")
    public List<ListingDto> findListing(
            @PathVariable String searchString,
            @Valid @RequestParam(required = false) String category) {

        return listingService.findListing(searchString, category);
    }

    @PostMapping
    public ResponseEntity<?> addListing(@Valid @RequestBody ListingDto listingDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<UserDto> user = userService.findByUsername(authentication.getName());
        listingDto.setUser(user.get().getId());
        listingDto.setCreatedAt(new Date());
        listingService.add(listingDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Listing added");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateListing(@Valid @RequestBody ListingDto listingDto, @PathVariable Long id) {
        ListingDto existingListing = listingService.get(id);
        if (existingListing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Listing not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserDto> user = userService.findByUsername(authentication.getName());

        if (!Objects.equals(user.get().getId(), existingListing.getUser())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You do not have permission to update this listing");
        }

        listingDto.setCreatedAt(existingListing.getCreatedAt());
        listingDto.setUser(existingListing.getUser());

        listingService.update(listingDto, id);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Listing updated");
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteListing(@PathVariable Long id) {
        ListingDto existingListing = listingService.get(id);
        if (existingListing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Listing not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserDto> user = userService.findByUsername(authentication.getName());

        if (!Objects.equals(user.get().getId(), existingListing.getUser())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You do not have permission to update this listing");
        }

        listingService.remove(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Listing deleted");
    }
}
