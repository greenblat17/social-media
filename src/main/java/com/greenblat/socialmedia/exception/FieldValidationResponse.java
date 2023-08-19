package com.greenblat.socialmedia.exception;

import java.time.LocalDateTime;

public record FieldValidationResponse(LocalDateTime timestamp,
                                      String field,
                                      String message) {
}
