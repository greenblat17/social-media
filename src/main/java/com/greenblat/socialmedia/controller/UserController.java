package com.greenblat.socialmedia.controller;

import com.greenblat.socialmedia.dto.MessageRequest;
import com.greenblat.socialmedia.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @PostMapping("/follow/{followingId}")
    public ResponseEntity<Void> follow(@PathVariable Long followingId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        userService.followUser(followingId, userDetails);
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/friend/{friendId}")
    public ResponseEntity<Void> requestToFriend(@PathVariable Long friendId,
                                                @RequestParam("is_friend") Boolean isFriend,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        userService.requestToFriend(friendId, userDetails, isFriend);
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/unfollow/{followingId}")
    public ResponseEntity<Void> unfollow(@PathVariable Long followingId,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        userService.unfollowUser(followingId, userDetails);
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/send-message")
    public ResponseEntity<Void> sendMessage(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody MessageRequest messageRequest) {
        userService.sendMessage(userDetails, messageRequest);
        return ResponseEntity
                .ok()
                .build();
    }
}
