package com.greenblat.socialmedia.service;

import com.greenblat.socialmedia.dto.post.PostResponse;
import com.greenblat.socialmedia.mapper.PostMapper;
import com.greenblat.socialmedia.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityFeedService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;
    private final ImageService imageService;

    @Value("${spring.data.web.pageable.default-page-size}")
    private int pageSize;

    public Page<PostResponse> getPostsByFollowerUser(int page, UserDetails userDetails) {
        var user = userService.findUser(userDetails.getUsername());

        var pageRequest = PageRequest.of(
                page,
                pageSize,
                Sort.by("created_at").descending()
        );

        return postRepository.findByFollowerUser(pageRequest, user.getId())
                .map(post -> {
                    var postsImages = imageService.getPostsImages(post);
                    return postMapper.mapToDto(post, postsImages);
                });

    }


}
