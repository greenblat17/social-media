package com.greenblat.socialmedia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(@NotNull String username,
                              @Email String email,
                              @NotNull String password) {
}
