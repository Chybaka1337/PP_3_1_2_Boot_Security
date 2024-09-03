package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

    public AdminController(UserService userService, RoleService roleService) {
        super(userService, roleService);
    }

    @GetMapping()
    public ModelAndView getAdminPage(ModelAndView mav) {
        mav.setViewName("admin");
        mav.addObject("users", getUserService().getAll());
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView saveUser() {
        return new ModelAndView("saveUser")
                .addObject("user", new User())
                .addObject("roles", getRoleService().getAll());
    }

    @PostMapping("/create")
    public String saveUser(@ModelAttribute("user") User user) {
        getUserService().save(user);
        return "redirect:/admin";
    }

    @GetMapping("/user/{id}")
    public ModelAndView updateUser(@PathVariable int id) {
        User user = getUserService().getUserById(id);
        return new ModelAndView("updateUser")
                .addObject("user", user)
                .addObject("roles", getRoleService().getAll());
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
