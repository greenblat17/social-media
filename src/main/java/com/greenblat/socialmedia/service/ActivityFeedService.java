package com.greenblat.socialmedia.service;

import com.greenblat.socialmedia.dto.PostDTO;
import com.greenblat.socialmedia.mapper.PostMapper;
import com.greenblat.socialmedia.model.Post;
import com.greenblat.socialmedia.repository.PostRepository;
import com.greenblat.socialmedia.repository.UserRelationRepository;
import com.greenblat.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityFeedService {

    private final PostRepository postRepository;
    private final UserRelationRepository userRelationRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public List<PostDTO> getPostsByFollowerUser(UserDetails userDetails) {
        var user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        var followingUsers = userRelationRepository.findByRelatingUserId(user.getId())
                .stream()
                .filter(userRelation -> !userRelation.getFollowingUser().getPosts().isEmpty())
                .toList();

        List<PostDTO> activityFeed = new ArrayList<>(followingUsers.size());

        followingUsers.forEach(followingUser -> {
            var authorId = followingUser.getFollowingUser().getId();
            var post = postRepository.findByAuthor_Id(authorId)
                    .stream()
                    .max(Comparator.comparing(Post::getCreatedAt))
                    .orElseThrow();
            activityFeed.add(postMapper.mapToDto(post));

        });

        return activityFeed;

    }


}
