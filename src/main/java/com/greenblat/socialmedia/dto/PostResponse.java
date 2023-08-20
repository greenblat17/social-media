package com.greenblat.socialmedia.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(String title,
                           String content,
                           LocalDateTime createdAt,
                           List<byte[]> images) {
}
