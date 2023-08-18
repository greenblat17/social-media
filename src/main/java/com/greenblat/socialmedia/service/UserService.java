package com.greenblat.socialmedia.service;

import com.greenblat.socialmedia.model.RelationType;
import com.greenblat.socialmedia.model.User;
import com.greenblat.socialmedia.model.UserRelation;
import com.greenblat.socialmedia.repository.UserRelationRepository;
import com.greenblat.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRelationRepository userRelationRepository;

    @Transactional
    public void followUser(Long followingUserId, UserDetails userDetails) {
        var user = findUser(userDetails.getUsername());
        var followingUser = findUser(followingUserId);

        var followingUserToCurrentUserRelation = findUserRelation(followingUser, user);

        if (followingUserToCurrentUserRelation.isPresent()) {
            acceptFriendRequest(user, followingUser, followingUserToCurrentUserRelation.get());
        } else {
            var userRelation = buildUserRelation(user, followingUser, RelationType.FOLLOWER);
            userRelationRepository.save(userRelation);
        }
    }

    @Transactional
    public void requestToFriend(Long friendRequestId,
                                UserDetails userDetails,
                                boolean isFriend) {
        if (isFriend) {
            var user = findUser(userDetails.getUsername());
            var followingUser = findUser(friendRequestId);

            var userRelation = findUserRelation(followingUser, user)
                    .orElseThrow();

            if (userRelation.getRelationType().equals(RelationType.FRIEND)) {
                throw new RuntimeException();
            }

            acceptFriendRequest(user, followingUser, userRelation);
        }
    }

    @Transactional
    public void unfollowUser(Long followingUserId, UserDetails userDetails) {
        var user = findUser(userDetails.getUsername());
        var followingUser = findUser(followingUserId);
        userRelationRepository.deleteByRelatingUserAndFollowingUser(user, followingUser);

        findUserRelation(followingUser, user)
                .ifPresent(userRelation -> updateUserRelation(userRelation, RelationType.FOLLOWER));
    }

    private void acceptFriendRequest(User currentUser,
                                     User userToFriend,
                                     UserRelation followingUserToCurrentUserRelation) {
        updateUserRelation(followingUserToCurrentUserRelation, RelationType.FRIEND);

        var userRelation = buildUserRelation(currentUser, userToFriend, RelationType.FRIEND);
        userRelationRepository.save(userRelation);
    }

    private void updateUserRelation(UserRelation userRelation, RelationType relationType) {
        userRelation.setRelationType(relationType);
        userRelationRepository.save(userRelation);
    }

    private Optional<UserRelation> findUserRelation(User followingUser, User currentUser) {
        return userRelationRepository
                .findByRelatingUserAndFollowingUser(followingUser, currentUser);
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow();
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    private static UserRelation buildUserRelation(User currentUser,
                                                  User followingUser,
                                                  RelationType relationType) {
        return UserRelation.builder()
                .relatingUser(currentUser)
                .followingUser(followingUser)
                .relationType(relationType)
                .build();
    }
}
