package com.spring.api.web.controller;

import com.spring.api.entity.User;
import com.spring.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User newUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User userFound = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userFound);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user) {
        User userWithUpdatedPassword = userService.alterPassword(id, user.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(userWithUpdatedPassword);
    }
}