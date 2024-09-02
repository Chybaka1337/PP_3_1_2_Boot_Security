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
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/user")
    public ModelAndView getUser(HttpSession http, ModelAndView mav) {
        User user = (User) http.getAttribute("user");
        mav
                .addObject("user", user)
                .addObject("roles", user.getRoles())
                .setViewName("user");
        return mav;
    }

    @GetMapping("/admin")
    public ModelAndView adminPage(ModelAndView mav) {
        mav.setViewName("admin");
        mav.addObject("users", userService.getAll());
        return mav;
    }

    @GetMapping("/admin/create")
    public ModelAndView saveUser() {
        return new ModelAndView("saveUser")
                .addObject("user", new User())
                .addObject("roles", roleService.getAll());
    }

    @PostMapping("/admin/create")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/{id}")
    public ModelAndView updateUser(@PathVariable int id) {
        User user = userService.getUserById(id);
        return new ModelAndView("updateUser")
                .addObject("user", user)
                .addObject("roles", roleService.getAll());
    }

    //TODO
    @PostMapping("/admin/user/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User user) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.delete(userService.getUserById(id));
        return "redirect:/admin";
    }
}
