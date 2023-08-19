package com.greenblat.socialmedia.service;

import com.greenblat.socialmedia.dto.PostDTO;
import com.greenblat.socialmedia.mapper.PostMapper;
import com.greenblat.socialmedia.repository.PostRepository;
import com.greenblat.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityFeedService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Value("${spring.data.web.pageable.default-page-size}")
    private int pageSize;

    public Page<PostDTO> getPostsByFollowerUser(int page, UserDetails userDetails) {
        var user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        var pageRequest = PageRequest.of(
                page,
                pageSize,
                Sort.by("created_at").descending()
        );
        var posts = postRepository.findByFollowerUser(pageRequest, user.getId())
                .stream()
                .map(postMapper::mapToDto)
                .toList();

        return new PageImpl<>(posts);
    }


}
