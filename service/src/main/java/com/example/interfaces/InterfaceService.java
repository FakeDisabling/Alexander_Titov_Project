package com.example.interfaces;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface InterfaceService<T> {

    T add(T object);

    T get(Long id);

    boolean update(T object, Long id);

    boolean remove(Long id);

    List<T> getAll();
}
