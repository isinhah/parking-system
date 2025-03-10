package com.spring.api.service;

import com.spring.api.entity.User;
import com.spring.api.repository.UserRepository;
import com.spring.api.web.dto.UserCreateDto;
import com.spring.api.web.dto.UserPasswordDto;
import com.spring.api.web.dto.UserResponseDto;
import com.spring.api.web.dto.mapper.UserMapper;
import com.spring.api.web.exception.EntityNotFoundException;
import com.spring.api.web.exception.PasswordInvalidException;
import com.spring.api.web.exception.UsernameUniqueViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto save(UserCreateDto createDto) {
        User userToSave = UserMapper.INSTANCE.toUser(createDto);

        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));

        try {
            User savedUser = userRepository.save(userToSave);
            return UserMapper.INSTANCE.toDto(savedUser);
        } catch (DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("The username - %s - already exists", createDto.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id=%s not found", id)));
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDto> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserMapper.INSTANCE::toDto);
    }

    @Transactional
    public UserResponseDto alterPassword(Long id, UserPasswordDto dto) {
        User user = findById(id);

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new PasswordInvalidException("The password is incorrect");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new PasswordInvalidException("The new passwords are different");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        user = userRepository.save(user);

        return UserMapper.INSTANCE.toDto(user);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with username=%s not found", username)));
    }

    public User.Role findRoleByUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }
}