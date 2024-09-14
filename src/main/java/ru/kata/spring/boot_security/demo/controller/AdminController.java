package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

    public AdminController(UserService userService, RoleService roleService) {
        super(userService, roleService);
    }

    @Override
    @GetMapping()
    public ModelAndView getPage(HttpSession http, ModelAndView mav) {
        mav = super.getPage(http, mav);
        mav.addObject("users", getUserService().getAll());
        mav.addObject("newUser", new User());
        mav.addObject("roles", getRoleService().getAll());
        mav.setViewName("admin");
        return mav;
    }

    @PostMapping("/create")
    public String saveUser(@ModelAttribute("newUser") User user) {
        getUserService().save(user);
        return "redirect:/admin";
    }

    @PostMapping("/user/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User user) {
        getUserService().update(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        getUserService().delete(getUserService().getUserById(id));
        return "redirect:/admin";
    }
}
