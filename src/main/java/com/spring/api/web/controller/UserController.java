package com.spring.api.web.controller;

import com.spring.api.entity.User;
import com.spring.api.service.UserService;
import com.spring.api.web.dto.UserCreateDto;
import com.spring.api.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto createDto) {
        UserResponseDto newUserResponse = userService.save(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User userFound = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userFound);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user) {
        User userWithUpdatedPassword = userService.alterPassword(id, user.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(userWithUpdatedPassword);
    }
}