package com.greenblat.socialmedia.service;

import com.greenblat.socialmedia.dto.PostRequest;
import com.greenblat.socialmedia.dto.PostResponse;
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

    public PostResponse savePost(PostRequest request, UserDetails userDetails) {
        var user = userService.findUser(userDetails.getUsername());
        var images = imageMapper.mapToListImage(request.images());
        var post = postMapper.mapToPost(request, user, images);

        uploadImage(request.images(), post);
        var savedPost = postRepository.save(post);
        var postsImages = imageService.getPostsImages(savedPost);

        return postMapper.mapToDto(savedPost, postsImages);
    }

    public PostResponse updatePost(Long id, PostRequest request) {
        var toUpdatePost = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Post with ID [%s] not found"
                                        .formatted(id)
                        ));

        toUpdatePost.setTitle(request.title());
        toUpdatePost.setContent(request.content());

        var updatedPost = postRepository.save(toUpdatePost);
        var postsImages = imageService.getPostsImages(updatedPost);

        return postMapper.mapToDto(updatedPost, postsImages);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<PostResponse> getPostsByUser(Long userId) {
        var posts = postRepository.findByAuthor_Id(userId);
        return posts.stream()
                .map(post -> {
                    var postsImages = imageService.getPostsImages(post);
                    return postMapper.mapToDto(post, postsImages);
                })
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private void uploadImage(List<MultipartFile> images, Post post) {
        for (MultipartFile image : images) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream(), post);
        }
    }
}
