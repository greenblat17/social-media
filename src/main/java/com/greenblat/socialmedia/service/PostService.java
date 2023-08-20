package com.greenblat.socialmedia.service;

import com.greenblat.socialmedia.dto.PostDTO;
import com.greenblat.socialmedia.exception.ResourceNotFoundException;
import com.greenblat.socialmedia.mapper.ImageMapper;
import com.greenblat.socialmedia.mapper.PostMapper;
import com.greenblat.socialmedia.model.Post;
import com.greenblat.socialmedia.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final ImageMapper imageMapper;
    private final UserService userService;
    private final ImageService imageService;

    public PostDTO savePost(PostDTO postDTO, UserDetails userDetails) {
        var user = userService.findUser(userDetails.getUsername());
        var images = imageMapper.mapToListImage(postDTO.images());
        var post = postMapper.mapToPost(postDTO, user, images);

        uploadImage(postDTO.images(), post);

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

    @SneakyThrows
    private void uploadImage(List<MultipartFile> images, Post post) {
        for (MultipartFile image : images) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream(), post);
        }
    }
}
