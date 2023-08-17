package com.greenblat.socialmedia.dto;

public record RegisterRequest(String username,
                              String email,
                              String password) {
}
