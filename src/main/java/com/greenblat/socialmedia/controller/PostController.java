package com.greenblat.socialmedia.controller;

import com.greenblat.socialmedia.dto.PostDTO;
import com.greenblat.socialmedia.service.PostService;
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
public class PostController {

    private final PostService postService;

    @PostMapping("/")
    public ResponseEntity<PostDTO> addPost(@RequestBody @Validated PostDTO postDTO,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        var savedPost = postService.savePost(postDTO, userDetails);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> editPost(@PathVariable Long postId,
                                            @RequestBody @Validated PostDTO postDTO) {
        var updatedPost = postService.updatePost(postId, postDTO);
        return new ResponseEntity<>(updatedPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<PostDTO>> getUserPosts(@PathVariable Long userId) {
        var posts = postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
