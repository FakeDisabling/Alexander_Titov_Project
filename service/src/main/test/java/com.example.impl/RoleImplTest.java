package com.example.impl;

import com.example.interfaces.RoleDao;
import com.example.mapper.RoleMapper;
import com.example.model.Category;
import com.example.model.CategoryDto;
import com.example.model.Role;
import com.example.model.RoleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoleImplTest {

    private RoleImpl roleService;
    private RoleDao roleDao;

    @BeforeEach
    void init() {
        roleDao = Mockito.spy(RoleDao.class);
        RoleMapper roleMapper = Mockito.mock(RoleMapper.class);
        roleService = new RoleImpl(roleDao, roleMapper);
    }

    @Test
    void testUnitDeleteById() {
        Long roleId = 1L;

        Mockito.doNothing()
                .when(roleDao)
                .deleteById(roleId);

        boolean isDeleted = roleService.remove(roleId);
        assertTrue(isDeleted);
    }

    @Test
    void testUnitGetAll() {
        List<Role> roleList = new ArrayList<>();
        Mockito.doReturn(roleList)
                .when(roleDao)
                .findAll();

        List<RoleDto> roleDtoList = roleService.getAll();
        assertEquals(roleList.size(), roleDtoList.size());
    }

    @Test
    void testUnitSave() {
        RoleDto roleDto = new RoleDto(1L, "test");

        Role role = new Role(1L, "test");

        Mockito.when(roleDao.findById(Mockito.anyLong())).thenReturn(Optional.of(role));
        Mockito.when(roleDao.save(Mockito.any(Role.class))).thenReturn(role);

        RoleDto savedRole = roleService.add(roleDto);
        assertNotNull(savedRole);
    }

    @Test
    void testUnitUpdate(){
        RoleDto roleDto = new RoleDto(1L, "test");

        Role role = new Role(1L, "test");

        Mockito.when(roleDao.findById(Mockito.anyLong())).thenReturn(Optional.of(role));
        Mockito.when(roleDao.save(Mockito.any(Role.class))).thenReturn(role);

        assertTrue(roleService.update(roleDto, roleDto.getId()));
    }

    @Test
    void testUnitGetById() {
        Long id = 1L;
        Role role = new Role();
        Mockito.when(roleDao.findById(id)).thenReturn(Optional.of(role));

        RoleDto result = roleService.get(id);
        assertNull(result);
    }
}
