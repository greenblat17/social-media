package com.greenblat.socialmedia.dto.auth;

import com.greenblat.socialmedia.validation.UniqueUserInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@UniqueUserInfo
public record RegisterRequest(@NotNull String username,
                              @Email String email,
                              @NotNull String password) {
}
