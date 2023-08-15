package com.greenblat.socialmedia.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRelationId implements Serializable {
    private User relatingUser;
    private User followingUser;
}
