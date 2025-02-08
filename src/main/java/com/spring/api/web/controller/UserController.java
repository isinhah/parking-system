package com.spring.api.web.controller;

import com.spring.api.entity.User;
import com.spring.api.service.UserService;
import com.spring.api.web.controller.docs.UserControllerDocumentation;
import com.spring.api.web.dto.UserCreateDto;
import com.spring.api.web.dto.UserPasswordDto;
import com.spring.api.web.dto.UserResponseDto;
import com.spring.api.web.dto.mapper.UserMapper;
import com.spring.api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController implements UserControllerDocumentation {

    private final UserService userService;

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
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto createDto) {
        UserResponseDto newUserResponse = userService.save(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        User userFound = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.INSTANCE.toDto(userFound));
    }

    @GetMapping
    public Page<UserResponseDto> getAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto dto) {
        UserResponseDto userWithUpdatedPassword = userService.alterPassword(id, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}