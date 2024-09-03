package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

    public UserController(UserService userService, RoleService roleService) {
        super(userService, roleService);
    }

    @GetMapping()
    public ModelAndView getUserPage(HttpSession http, ModelAndView mav) {
        User user = (User) http.getAttribute("user");
        mav
                .addObject("user", user)
                .addObject("roles", user.getRoles())
                .setViewName("user");
        return mav;
    }
}
