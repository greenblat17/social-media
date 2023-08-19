package com.greenblat.socialmedia.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record PostDTO(@NotBlank String title,
                      @NotBlank String content,
                      LocalDateTime createdAt,
                      List<MultipartFile> images) {
}
