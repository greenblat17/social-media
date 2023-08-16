package com.greenblat.socialmedia.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostDTO(String title,
                      String content,
                      List<MultipartFile> images) {
}
