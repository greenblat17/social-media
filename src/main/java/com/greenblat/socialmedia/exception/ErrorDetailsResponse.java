package com.greenblat.socialmedia.exception;


import java.time.LocalDateTime;

public record ErrorDetailsResponse(LocalDateTime timestamp,
                           String message,
                           String path) {
}
