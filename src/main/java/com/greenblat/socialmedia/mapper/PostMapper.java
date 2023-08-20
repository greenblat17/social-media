package com.greenblat.socialmedia.mapper;

import com.greenblat.socialmedia.dto.PostRequest;
import com.greenblat.socialmedia.dto.PostResponse;
import com.greenblat.socialmedia.model.Image;
import com.greenblat.socialmedia.model.Post;
import com.greenblat.socialmedia.model.User;
import com.greenblat.socialmedia.util.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public class PostMapper {

    public PostResponse mapToDto(Post post, List<byte[]> bytes) {
        return new PostResponse(
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                bytes
        );
    }

    public Post mapToPost(PostRequest dto, User user, List<Image> images) {
        return Post.builder()
                .author(user)
                .title(dto.title())
                .content(dto.content())
                .createdAt(LocalDateTime.now())
                .images(images)
                .build();
    }
}
