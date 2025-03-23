package com.example.interfaces;

import com.example.model.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService extends InterfaceService<UserDto> {

    Optional<UserDto> findByUsername(String username);
}
