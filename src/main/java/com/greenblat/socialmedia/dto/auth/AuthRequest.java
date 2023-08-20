package com.greenblat.socialmedia.dto.auth;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UniqueElements;

public record AuthRequest(@NotBlank @UniqueElements String username,
                          @NotBlank String password) {
}
