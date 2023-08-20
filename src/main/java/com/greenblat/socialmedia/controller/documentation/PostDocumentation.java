package com.greenblat.socialmedia.controller.documentation;

import com.greenblat.socialmedia.dto.post.PostRequest;
import com.greenblat.socialmedia.dto.post.PostResponse;
import com.greenblat.socialmedia.exception.ErrorDetailsResponse;
import com.greenblat.socialmedia.exception.FieldValidationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Tag(name = "Post")
public interface PostDocumentation {

    @Operation(
            summary = "Add Post REST API",
            description = "Endpoint for add post in database",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PostResponse.class)
                                    )
                            }
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
    ResponseEntity<PostResponse> addPost(PostRequest postRequest,
                                         UserDetails userDetails);

    @Operation(
            summary = "Edit Post REST API",
            description = "Endpoint for update post in database",
            responses = {
                    @ApiResponse(
                            description = "Accepted",
                            responseCode = "202",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PostResponse.class)
                                    )
                            }
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
                                            schema = @Schema(implementation = FieldValidationResponse.class)
                                    )
                            }
                    ),
            }
    )
    ResponseEntity<PostResponse> editPost(Long postId,
                                          PostRequest postRequest);


    @Operation(
            summary = "Delete Post REST API",
            description = "Endpoint for delete post in database",
            responses = {
                    @ApiResponse(
                            description = "Success",
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
                    )
            }
    )
    ResponseEntity<Void> deletePost(Long postId);

    @Operation(
            summary = "Get Post for User REST API",
            description = "Endpoint for get post by user id from database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<List<PostResponse>> getUserPosts(Long userId);
}
