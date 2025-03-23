package com.example.impl;

import com.example.interfaces.UserDao;
import com.example.interfaces.UserService;
import com.example.mapper.UserMapper;
import com.example.model.Role;
import com.example.model.User;
import com.example.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    public UserDto add(UserDto object){
        try {
            User user = new User();
            BeanUtils.copyProperties(object, user);
            user.setRole(new Role(object.getRole()));
            User savedUser = userDao.save(user);
            object.setId(savedUser.getId());
            log.info("Adding user: {}", object);
            return object;
        } catch (Exception e) {
            log.error("Add user error:", e);
        }
        return object;
    }

    @Override
    public UserDto get(Long id) {
        try {
            User user = userDao.findById(id).orElse(null);
            log.info("Get user: {}", user);
            return userMapper.toUserDto(user);
        } catch (Exception e) {
            log.error("Get user error:", e);
            return null;
        }
    }

    @Override
    public boolean update(UserDto object, Long id) {
        try {
            User user = userDao.findById(id).orElse(null);
            if (user == null) {
                log.warn("user not found");
                return false;
            }
            object.setId(id);
            userDao.save(userMapper.toUser(object));
            log.info("Updating user: {}", object);
            return true;
        } catch (Exception e) {
            log.error("Update user error:", e);
            return false;
        }
    }

    @Override
    public boolean remove(Long id) {
        try {
            userDao.deleteById(id);
            log.info("Deleting user: {}", id);
            return true;
        } catch (Exception e) {
            log.error("Deleting user error:", e);
            return false;
        }
    }

    @Override
    public List<UserDto> getAll() {
        try {
            List<User> users = userDao.findAll();
            log.info("Get all users: {}", users);
            return users.stream().map(userMapper::toUserDto).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Get all users error:", e);
            return null;
        }
    }

    @Override
    public Optional<UserDto> findByUsername(String username) {
        return userDao.findByUsername(username).map(userMapper::toUserDto);
    }
}
