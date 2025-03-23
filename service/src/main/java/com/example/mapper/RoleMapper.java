package com.example.mapper;

import com.example.model.Role;
import com.example.model.RoleDto;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role toRole(RoleDto dto) {
        if (dto == null) {
            return null;
        }
        return new Role(
                dto.getId(),
                dto.getName()
        );
    }

    public RoleDto toRoleDto(Role entity) {
        if (entity == null) {
            return null;
        }
        return new RoleDto(
                entity.getId(),
                entity.getName()
        );
    }
}
