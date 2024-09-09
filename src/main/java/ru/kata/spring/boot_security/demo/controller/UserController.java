package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

    public UserController(UserService userService, RoleService roleService) {
        super(userService, roleService);
    }

    @Override
    @GetMapping()
    public ModelAndView getPage(HttpSession http, ModelAndView mav) {
        mav = super.getPage(http, mav);
        mav.setViewName("user");
        return mav;
    }
}
