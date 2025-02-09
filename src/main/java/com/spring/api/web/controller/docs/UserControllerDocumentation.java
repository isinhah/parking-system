package com.spring.api.web.controller.docs;

import com.spring.api.web.dto.UserCreateDto;
import com.spring.api.web.dto.UserPasswordDto;
import com.spring.api.web.dto.UserResponseDto;
import com.spring.api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "users", description = "contain all operations related to resources of users")
public interface UserControllerDocumentation {

    @Operation(
            summary = "Create a new user", description = "Resource to create a new user",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Resource created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409",
                            description = "Email already registered in the system",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422",
                            description = "Resource not processed by invalid entry data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    ResponseEntity<UserResponseDto> create(UserCreateDto createDto);

    @Operation(
            summary = "Get user by ID",
            description = "Retrieve a user by their ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "User found successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "User not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    ResponseEntity<UserResponseDto> getById(Long id);

    @Operation(
            summary = "Get all users",
            description = "Retrieve all users with pagination",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Users found successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class)))
            }
    )
    Page<UserResponseDto> getAll(Pageable pageable);

    @Operation(
            summary = "Update user password",
            description = "Update a user's password by their ID",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Password updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400",
                            description = "The passwords are different",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Resource not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422",
                            description = "Invalid fields or fields that are not in the correct pattern",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    ResponseEntity<Void> updatePassword(Long id, UserPasswordDto dto);
}