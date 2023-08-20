package com.greenblat.socialmedia.controller;

import com.greenblat.socialmedia.dto.pagination.PageResponse;
import com.greenblat.socialmedia.dto.post.PostResponse;
import com.greenblat.socialmedia.service.ActivityFeedService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/feeds")
@RequiredArgsConstructor
@Tag(name = "Activity Feed")
public class ActivityFeedController {

    private final ActivityFeedService activityFeedService;

    @GetMapping("/posts")
    public ResponseEntity<PageResponse<PostResponse>> getPosts(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                               @AuthenticationPrincipal UserDetails userDetails) {
        var posts = activityFeedService.getPostsByFollowerUser(page, userDetails);
        return ResponseEntity.ok(PageResponse.of(posts));
    }
}
