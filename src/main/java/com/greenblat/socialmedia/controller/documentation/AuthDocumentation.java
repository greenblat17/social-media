package com.greenblat.socialmedia.controller.documentation;

import com.greenblat.socialmedia.dto.auth.AuthRequest;
import com.greenblat.socialmedia.dto.auth.AuthResponse;
import com.greenblat.socialmedia.dto.auth.RegisterRequest;
import com.greenblat.socialmedia.exception.ErrorDetailsResponse;
import com.greenblat.socialmedia.exception.FieldValidationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication")
public interface AuthDocumentation {

    @Operation(
            summary = "Register user REST API",
            description = "Endpoint for register user and save it in database",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = FieldValidationResponse.class)
                                    )
                            }
                    ),
            }
    )
    ResponseEntity<AuthResponse> register(RegisterRequest registerRequest);

    @Operation(
            summary = "Authentication user REST API",
            description = "Endpoint for authenticate user",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ErrorDetailsResponse.class)
                                    )
                            }
                    ),
            }
    )
    ResponseEntity<AuthResponse> authenticate(AuthRequest registerRequest);
}
