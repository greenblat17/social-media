package com.greenblat.socialmedia.service;

import com.greenblat.socialmedia.dto.message.MessageRequest;
import com.greenblat.socialmedia.exception.ResourceNotFoundException;
import com.greenblat.socialmedia.model.Message;
import com.greenblat.socialmedia.model.RelationType;
import com.greenblat.socialmedia.model.User;
import com.greenblat.socialmedia.model.UserRelation;
import com.greenblat.socialmedia.repository.MessageRepository;
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
    private final MessageRepository messageRepository;

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
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "User Relation with followingUserId [%s] and currentUserId [%s] not found"
                                            .formatted(followingUser.getId(), user.getId())
                            )
                    );

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

    @Transactional
    public void sendMessage(UserDetails userDetails, MessageRequest request) {
        var sender = findUser(userDetails.getUsername());
        var recipient = findUser(request.username());

        var message = buildMessage(request, sender, recipient);

        messageRepository.save(message);
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

    protected User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with username [%s] not found"
                                        .formatted(username)
                        ));
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with ID [%s] not found"
                                        .formatted(id)
                        ));
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

    private static Message buildMessage(MessageRequest request,
                                        User sender,
                                        User recipient) {
        return Message.builder()
                .sender(sender)
                .recipient(recipient)
                .text(request.text())
                .build();
    }
}
