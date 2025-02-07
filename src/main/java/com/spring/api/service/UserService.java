package com.spring.api.service;

import com.spring.api.entity.User;
import com.spring.api.repository.UserRepository;

import com.spring.api.web.dto.UserCreateDto;
import com.spring.api.web.dto.UserPasswordDto;
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
    public UserResponseDto alterPassword(Long id, UserPasswordDto dto) {
        User user = findById(id);

        if (!user.getPassword().equals(dto.getCurrentPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new RuntimeException("The passwords are different");
        }

        user.setPassword(dto.getNewPassword());

        user = userRepository.save(user);

        return UserMapper.INSTANCE.toDto(user);
    }
}