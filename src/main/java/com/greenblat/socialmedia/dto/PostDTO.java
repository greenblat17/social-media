package com.greenblat.socialmedia.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record PostDTO(@NotNull String title,
                      @NotNull String content,
                      LocalDateTime createdAt,
                      List<MultipartFile> images) {
}
