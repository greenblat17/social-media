package com.greenblat.socialmedia.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostRequest(@NotBlank String title,
                          @NotBlank String content,
                          List<MultipartFile> images) {
}
