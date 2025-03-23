package com.example.controller;

import com.example.interfaces.ListingService;
import com.example.interfaces.SaleHistoryService;
import com.example.interfaces.UserService;
import com.example.model.ListingDto;
import com.example.model.SaleHistory;
import com.example.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/saleHistory")
@RestController
@RequiredArgsConstructor
public class SaleHistoryController {

    private final SaleHistoryService saleHistoryService;
    private final ListingService listingService;
    private final UserService userService;

    @GetMapping("{id}")
    public List<ListingDto> getSaleHistoryChooseUser(@PathVariable Long id) {
        UserDto existingUser = userService.get(id);
        if (existingUser == null) {
            return null;
        }

        List<Long> allSaleListing = saleHistoryService.findBySellerId(id);
        List<ListingDto> listingDtos = listingService.getAll();

        return listingDtos.stream()
                .filter(listing -> allSaleListing.contains(listing.getId()))
                .toList();

    }

    @GetMapping
    public List<ListingDto> getSaleHistory() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserDto> user = userService.findByUsername(authentication.getName());

        List<Long> allSaleListing = saleHistoryService.findBySellerId(user.get().getId());
        List<ListingDto> listingDtos = listingService.getAll();

        return listingDtos.stream()
                .filter(listing -> allSaleListing.contains(listing.getId()))
                .toList();

    }
}
