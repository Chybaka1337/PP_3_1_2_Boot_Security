package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
public abstract class AbstractController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AbstractController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public UserService getUserService() {
        return userService;
    }

    public RoleService getRoleService() {
        return roleService;
    }
}
