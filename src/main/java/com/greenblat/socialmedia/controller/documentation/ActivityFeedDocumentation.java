package com.greenblat.socialmedia.controller.documentation;

import com.greenblat.socialmedia.dto.pagination.PageResponse;
import com.greenblat.socialmedia.dto.post.PostResponse;
import com.greenblat.socialmedia.exception.ErrorDetailsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "Activity Feed")
public interface ActivityFeedDocumentation {

    @Operation(
            summary = "Get activity feed with posts REST API",
            description = "Endpoint for getting a feed with activities with posts from users who are subscribed to",
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
    ResponseEntity<PageResponse<PostResponse>> getPosts(@Parameter(description = "Number of page with posts") Integer page,
                                                        UserDetails userDetails);
}
