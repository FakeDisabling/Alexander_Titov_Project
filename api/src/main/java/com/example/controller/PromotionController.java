package com.example.controller;

import com.example.interfaces.ListingService;
import com.example.interfaces.PromotionService;
import com.example.interfaces.UserService;
import com.example.model.ListingDto;
import com.example.model.PromotionDto;
import com.example.model.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/promotion")
@RestController
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;
    private final UserService userService;
    private final ListingService listingService;

    @PostMapping("{id}")
    public ResponseEntity<?> addPromotion(@Valid @RequestBody PromotionDto promotionDto, @PathVariable Long id) {
        ListingDto existingListing = listingService.get(id);
        if (existingListing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Listing not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserDto> user = userService.findByUsername(authentication.getName());

        if (!Objects.equals(user.get().getId(), existingListing.getUser())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You do not have permission to promoted this listing");
        }

        promotionDto.setListing(existingListing.getId());
        promotionDto.setUser(existingListing.getUser());
        promotionDto.setStartDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        promotionDto.setEndDate(calendar.getTime());

        promotionService.add(promotionDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Promoted is successful");
    }
}
