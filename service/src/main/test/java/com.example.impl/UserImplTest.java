package com.example.impl;

import com.example.interfaces.CategoryDao;
import com.example.interfaces.UserDao;
import com.example.mapper.CategoryMapper;
import com.example.mapper.UserMapper;
import com.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserImplTest {

    private UserImpl userService;
    private UserDao userDao;

    @BeforeEach
    void init() {
        userDao = Mockito.spy(UserDao.class);
        UserMapper userMapper = Mockito.mock(UserMapper.class);
        userService = new UserImpl(userDao, userMapper);
    }

    @Test
    void testUnitDeleteById() {
        Long userId = 1L;

        Mockito.doNothing()
                .when(userDao)
                .deleteById(userId);

        boolean isDeleted = userService.remove(userId);
        assertTrue(isDeleted);
    }

    @Test
    void testUnitGetAll() {
        List<User> userList = new ArrayList<>();
        Mockito.doReturn(userList)
                .when(userDao)
                .findAll();

        List<UserDto> userDtoList = userService.getAll();
        assertEquals(userList.size(), userDtoList.size());
    }

    @Test
    void testUnitSave() {
        UserDto userDto = new UserDto(1L, "test", "test@example.com", "+testtestte", 1L, "$2b$12$gM3WDNtBACjOGzstzBdwoen2y3LUjQDi7vadaEpd9Hd52fntuu7k.");

        User user = new User(1L, "test", "$2b$12$gM3WDNtBACjOGzstzBdwoen2y3LUjQDi7vadaEpd9Hd52fntuu7k.", "test@example.com", "+testtestte", new Role(1L));

        Mockito.when(userDao.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(userDao.save(Mockito.any(User.class))).thenReturn(user);

        UserDto savedUser = userService.add(userDto);
        assertNotNull(savedUser);
    }

    @Test
    void testUnitUpdate(){
        UserDto userDto = new UserDto(1L, "test", "test@example.com", "+testtestte", 1L, "$2b$12$gM3WDNtBACjOGzstzBdwoen2y3LUjQDi7vadaEpd9Hd52fntuu7k.");

        User user = new User(1L, "test", "$2b$12$gM3WDNtBACjOGzstzBdwoen2y3LUjQDi7vadaEpd9Hd52fntuu7k.", "test@example.com", "+testtestte", new Role(1L));

        Mockito.when(userDao.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(userDao.save(Mockito.any(User.class))).thenReturn(user);

        assertTrue(userService.update(userDto, userDto.getId()));
    }

    @Test
    void testUnitGetById() {
        Long id = 1L;
        User user = new User();

        Mockito.when(userDao.findById(id)).thenReturn(Optional.of(user));

        UserDto result = userService.get(id);
        assertNull(result);
    }
}
