package com.spring.api.service;

import com.spring.api.entity.User;
import com.spring.api.repository.UserRepository;

import com.spring.api.web.dto.UserCreateDto;
import com.spring.api.web.dto.UserResponseDto;
import com.spring.api.web.dto.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto save(UserCreateDto createDto) {
        User userToSave = UserMapper.INSTANCE.toUser(createDto);

        User savedUser = userRepository.save(userToSave);

        return UserMapper.INSTANCE.toDto(savedUser);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User alterPassword(Long id, String password) {
        User user = findById(id);
        user.setPassword(password);
        return user;
    }
}