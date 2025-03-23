package com.example.controller;

import com.example.interfaces.MessageService;
import com.example.interfaces.UserService;
import com.example.model.MessageDto;
import com.example.model.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/message")
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @GetMapping("{user1Id}/{user2Id}")
    public List<MessageDto> getMessageStory(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserDto> user = userService.findByUsername(authentication.getName());

        if (!(user.get().getId().equals(user1Id) || user.get().getId().equals(user2Id))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not have access to this conversation");
        }
        return messageService.findMessageByUserId(user1Id, user2Id);
    }

    @PostMapping()
    public ResponseEntity<?> sendMessage(@Valid @RequestBody MessageDto messageDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserDto> user = userService.findByUsername(authentication.getName());

        if (user.get().getId().equals(messageDto.getReceiver())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You can't send message for yourself");
        }

        messageDto.setSender(user.get().getId());
        messageDto.setSentAt(new Date());
        messageService.add(messageDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Message sended successfully");
    }
}
