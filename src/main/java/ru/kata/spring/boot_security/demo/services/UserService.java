package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User getUserById(int id);
    User getUserByUsername(String name);
    List<User> getAll();
    void save(User user);
    void update(int id, User user);
    void delete(User user);
}
