package com.greenblat.socialmedia.mapper;

import com.greenblat.socialmedia.dto.PostDTO;
import com.greenblat.socialmedia.model.Post;
import com.greenblat.socialmedia.model.User;
import com.greenblat.socialmedia.util.Mapper;

import java.time.LocalDateTime;
import java.util.Collections;

@Mapper
public class PostMapper {

    public PostDTO mapToDto(Post post) {
        return new PostDTO(
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                Collections.emptyList()
        );
    }

    public Post mapToPost(PostDTO dto, User user) {
        return Post.builder()
                .author(user)
                .title(dto.title())
                .content(dto.content())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
