package com.example.impl;

import com.example.interfaces.RoleDao;
import com.example.interfaces.RoleService;
import com.example.mapper.RoleMapper;
import com.example.model.Role;
import com.example.model.RoleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleImpl implements RoleService {

    private final RoleDao roleDao;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto add(RoleDto object)  {
        try {
            Role role = new Role();
            BeanUtils.copyProperties(object, role);
            Role savedRole = roleDao.save(role);
            object.setId(savedRole.getId());
            log.info("Adding roles: {}", object);
            return object;
        } catch (Exception e) {
            log.error("Add role error: ", e);
        }
        return object;
    }

    @Override
    public RoleDto get(Long id) {
        try {
            Role role = roleDao.findById(id).orElse(null);
            log.info("Get role: {}", role);
            return roleMapper.toRoleDto(role);
        } catch (Exception e) {
            log.error("Get role error: ", e);
            return null;
        }
    }

    @Override
    public boolean update(RoleDto object, Long id) {
        try {
            Role role = roleDao.findById(id).orElse(null);
            if (role == null) {
                log.warn("role not found");
                return false;
            }
            roleDao.save(roleMapper.toRole(object));
            log.info("Updating roles: {}", object);
            return true;
        } catch (Exception e) {
            log.error("Update role error: ", e);
            return false;
        }
    }

    @Override
    public boolean remove(Long id) {
        try {
            roleDao.deleteById(id);
            log.info("Removing roles: {}", id);
            return true;
        } catch (Exception e) {
            log.error("Remove role error: ", e);
            return false;
        }
    }

    @Override
    public List<RoleDto> getAll()  {
        try {
            List<Role> roles = roleDao.findAll();
            log.info("Get all roles: {}", roles);
            return roles.stream().map(roleMapper::toRoleDto).collect(Collectors.toList());
        }  catch (Exception e) {
            log.error("Get all role error: ", e);
            return null;
        }
    }
}
