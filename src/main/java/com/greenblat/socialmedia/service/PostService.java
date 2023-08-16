package com.greenblat.socialmedia.service;

import com.greenblat.socialmedia.dto.PostDTO;
import com.greenblat.socialmedia.mapper.PostMapper;
import com.greenblat.socialmedia.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostDTO savePost(PostDTO postDTO) {
        var post = postMapper.mapToPost(postDTO, null);
        var savedPost = postRepository.save(post);
        return postMapper.mapToDto(savedPost);
    }

    public PostDTO updatePost(Long id, PostDTO postDTO) {
        var toUpdatePost = postRepository.findById(id)
                .orElseThrow();

        toUpdatePost.setTitle(postDTO.title());
        toUpdatePost.setContent(postDTO.content());

        var updatedPost = postRepository.save(toUpdatePost);
        return postMapper.mapToDto(updatedPost);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
