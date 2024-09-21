package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.util.Util;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private int id;

    private String username;

    private String firstName;

    private String lastName;

    private int age;

    private Set<Role> roles = new HashSet<>();

    private String password;

    public UserDTO() {

    }

    public static UserDTO of(User user) {
        UserDTO userDTO = new UserDTO();
        Util.getMapper().map(user, userDTO);
        return userDTO;
    }

    public User toUser() {
        return Util.getMapper().map(this, User.class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
