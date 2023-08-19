package com.greenblat.socialmedia.service;

import com.greenblat.socialmedia.dto.PostDTO;
import com.greenblat.socialmedia.exception.ResourceNotFoundException;
import com.greenblat.socialmedia.mapper.PostMapper;
import com.greenblat.socialmedia.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;

    public PostDTO savePost(PostDTO postDTO, UserDetails userDetails) {
        var user = userService.findUser(userDetails.getUsername());
        var post = postMapper.mapToPost(postDTO, user);
        var savedPost = postRepository.save(post);
        return postMapper.mapToDto(savedPost);
    }

    public PostDTO updatePost(Long id, PostDTO postDTO) {
        var toUpdatePost = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Post with ID [%s] not found"
                                        .formatted(id)
                        ));

        toUpdatePost.setTitle(postDTO.title());
        toUpdatePost.setContent(postDTO.content());

        var updatedPost = postRepository.save(toUpdatePost);
        return postMapper.mapToDto(updatedPost);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<PostDTO> getPostsByUser(Long userId) {
        var posts = postRepository.findByAuthor_Id(userId);
        return posts.stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
