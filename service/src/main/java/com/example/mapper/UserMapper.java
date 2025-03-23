package com.example.mapper;

import com.example.model.Role;
import com.example.model.User;
import com.example.model.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserDto dto) {
        if (dto == null) {
            return null;
        }
        return new User(
                dto.getId(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail(),
                dto.getPhone(),
                new Role(dto.getRole())
        );
    }

    public UserDto toUserDto(User entity) {
        if (entity == null) {
            return null;
        }
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getRole().getId(),
                entity.getPassword()
        );
    }
}
