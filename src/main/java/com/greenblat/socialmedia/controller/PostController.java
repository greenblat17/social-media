package com.greenblat.socialmedia.controller;

import com.greenblat.socialmedia.dto.post.PostRequest;
import com.greenblat.socialmedia.dto.post.PostResponse;
import com.greenblat.socialmedia.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
@Tag(name = "Post")
public class PostController {

    private final PostService postService;

    @PostMapping("/")
    public ResponseEntity<PostResponse> addPost(@RequestBody @Validated PostRequest postRequest,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        var savedPost = postService.savePost(postRequest, userDetails);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> editPost(@PathVariable Long postId,
                                                 @RequestBody @Validated PostRequest postRequest) {
        var updatedPost = postService.updatePost(postId, postRequest);
        return new ResponseEntity<>(updatedPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable Long userId) {
        var posts = postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
