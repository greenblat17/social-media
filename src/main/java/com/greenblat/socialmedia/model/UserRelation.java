package com.greenblat.socialmedia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
