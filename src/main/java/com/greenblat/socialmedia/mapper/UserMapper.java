package com.greenblat.socialmedia.mapper;

import com.greenblat.socialmedia.dto.auth.RegisterRequest;
import com.greenblat.socialmedia.model.User;
import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class UserMapper {

    public User mapToUser(RegisterRequest request, String encodePassword) {
        return User.builder()
                .username(request.username())
                .email(request.email())
                .password(encodePassword)
                .build();
    }

    public User mapToUser(RegisterRequest request) {
        return User.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .build();
    }
}
