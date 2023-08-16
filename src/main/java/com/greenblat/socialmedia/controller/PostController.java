package com.greenblat.socialmedia.controller;

import com.greenblat.socialmedia.dto.PostDTO;
import com.greenblat.socialmedia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/")
    public ResponseEntity<PostDTO> addPost(@RequestBody PostDTO postDTO) {
        var savedPost = postService.savePost(postDTO);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> editPost(@PathVariable Long postId,
                                            @RequestBody PostDTO postDTO) {
        var updatedPost = postService.updatePost(postId, postDTO);
        return new ResponseEntity<>(updatedPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
