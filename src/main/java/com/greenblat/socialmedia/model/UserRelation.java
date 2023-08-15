package com.greenblat.socialmedia.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "user_relation")
@IdClass(UserRelationId.class)
public class UserRelation implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User relatingUser;

    @Id
    @ManyToOne
    @JoinColumn(name = "following_user", nullable = false)
    private User followingUser;

    @Enumerated(EnumType.STRING)
    private RelationType relationType;
}
