package com.example.controller;

import com.example.interfaces.CommentService;
import com.example.interfaces.ListingService;
import com.example.interfaces.UserService;
import com.example.model.CommentDto;
import com.example.model.Listing;
import com.example.model.ListingDto;
import com.example.model.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ListingService listingService;
    private final UserService userService;

    @GetMapping("{id}")
    public List<CommentDto> getCommentsFromListing(@PathVariable Long id) {
        return commentService.findByListingId(id);
    }

    @PostMapping("{id}")
    public ResponseEntity<?> addComment(@PathVariable Long id, @Valid @RequestBody CommentDto commentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserDto> user = userService.findByUsername(authentication.getName());

        if (listingService.get(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Listing not found");
        }
        commentDto.setListing(id);
        commentDto.setUser(user.get().getId());
        commentService.add(commentDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Comment added");
    }
}
