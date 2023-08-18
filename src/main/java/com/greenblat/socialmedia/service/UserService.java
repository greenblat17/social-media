package com.greenblat.socialmedia.service;

import com.greenblat.socialmedia.model.RelationType;
import com.greenblat.socialmedia.model.UserRelation;
import com.greenblat.socialmedia.repository.UserRelationRepository;
import com.greenblat.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRelationRepository userRelationRepository;

    public void followUser(Long followingUserId, UserDetails userDetails) {
        var user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();
        var followingUser = userRepository.findById(followingUserId)
                .orElseThrow();

        var userRelation = UserRelation.builder()
                .relatingUser(user)
                .followingUser(followingUser)
                .relationType(RelationType.FOLLOWER)
                .build();

        userRelationRepository.save(userRelation);
    }

}
