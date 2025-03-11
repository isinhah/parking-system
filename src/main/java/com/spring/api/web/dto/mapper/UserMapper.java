package com.spring.api.web.dto.mapper;

import com.spring.api.entity.User;
import com.spring.api.web.dto.UserCreateDto;
import com.spring.api.web.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "role", target = "role", qualifiedByName = "stringToRole")
    User toUser(UserCreateDto createDto);

    @Mapping(target = "role", source = "role", qualifiedByName = "mapRoleToDto")
    UserResponseDto toDto(User user);

    @Named("stringToRole")
    default User.Role stringToRole(String role) {
        if (role != null && !role.isEmpty()) {
            return User.Role.valueOf("ROLE_" + role.toUpperCase());
        }
        return User.Role.ROLE_CLIENTE;
    }

    @Named("mapRoleToDto")
    default String mapRoleToDto(User.Role role) {
        return role.name().replace("ROLE_", "");
    }

    static List<UserResponseDto> toDtoList(List<User> users) {
        return users.stream()
                .map(UserMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}