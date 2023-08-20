package com.greenblat.socialmedia.exception;


public class FriendRelationAlreadyExistsException extends RuntimeException{
    public FriendRelationAlreadyExistsException(String message) {
        super(message);
    }
}
