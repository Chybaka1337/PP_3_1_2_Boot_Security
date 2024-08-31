package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUserById(int id);
    User getUserByName(String name);
    List<User> getAll();
    void save(User user);
    void delete(User user);
}
