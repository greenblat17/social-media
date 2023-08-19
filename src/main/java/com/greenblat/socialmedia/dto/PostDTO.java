package com.greenblat.socialmedia.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record PostDTO(String title,
                      String content,
                      LocalDateTime createdAt,
                      List<MultipartFile> images) {
}
