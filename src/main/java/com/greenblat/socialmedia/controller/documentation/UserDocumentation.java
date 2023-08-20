package com.greenblat.socialmedia.controller.documentation;

import com.greenblat.socialmedia.dto.message.MessageRequest;
import com.greenblat.socialmedia.exception.ErrorDetailsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

@Tag(name = "User")
public interface UserDocumentation {

    @Operation(
            summary = "Follow to user REST API",
            description = "Endpoint for follow to another user",
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
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ErrorDetailsResponse.class)
                                    )
                            }
                    ),
            }
    )
    ResponseEntity<Void> follow(@Parameter(description = "ID of following user") Long followingId,
                                UserDetails userDetails);

    @Operation(
            summary = "Friend request to user REST API",
            description = "Endpoint for send friend request to another user",
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
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ErrorDetailsResponse.class)
                                    )
                            }
                    ),
            }
    )
    ResponseEntity<Void> requestToFriend(@Parameter(description = "ID of user who will friend") Long friendId,
                                         @Parameter(description = "Accept friend request or not") Boolean isFriend,
                                         UserDetails userDetails);

    @Operation(
            summary = "Unfollow to user REST API",
            description = "Endpoint for unfollow to another user",
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
    ResponseEntity<Void> unfollow(@Parameter(description = "ID of following user") Long followingId,
                                  UserDetails userDetails);

    @Operation(
            summary = "Unfollow to user REST API",
            description = "Endpoint for unfollow to another user",
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
    ResponseEntity<Void> sendMessage(UserDetails userDetails,
                                     MessageRequest messageRequest);
}
