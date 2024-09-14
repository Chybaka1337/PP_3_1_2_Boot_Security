package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.servlet.http.HttpSession;

@Controller
public abstract class AbstractController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AbstractController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public ModelAndView getPage(HttpSession http, ModelAndView mav) {
        return mav.addObject("cur_user", http.getAttribute("user"));
    }

    public UserService getUserService() {
        return userService;
    }

    public RoleService getRoleService() {
        return roleService;
    }
}
