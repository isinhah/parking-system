package com.spring.api.web.controller;

import com.spring.api.entity.User;
import com.spring.api.service.UserService;
import com.spring.api.web.controller.docs.UserControllerDocumentation;
import com.spring.api.web.dto.UserCreateDto;
import com.spring.api.web.dto.UserPasswordDto;
import com.spring.api.web.dto.UserResponseDto;
import com.spring.api.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController implements UserControllerDocumentation {

    private final UserService userService;

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