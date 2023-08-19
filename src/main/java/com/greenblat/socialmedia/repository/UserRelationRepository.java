package com.greenblat.socialmedia.repository;

import com.greenblat.socialmedia.model.User;
import com.greenblat.socialmedia.model.UserRelation;
import com.greenblat.socialmedia.model.UserRelationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRelationRepository extends JpaRepository<UserRelation, UserRelationId> {

    void deleteByRelatingUserAndFollowingUser(User relatingUser, User followingUser);

    Optional<UserRelation> findByRelatingUserAndFollowingUser(User relatingUser, User followingUser);

    List<UserRelation> findByRelatingUserId(Long relatingUserId);
}
