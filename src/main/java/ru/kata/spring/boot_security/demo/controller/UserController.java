package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
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
    public ModelAndView getPage(ModelAndView mav) {
        mav.setViewName("user");
        return mav;
    }

    @ResponseBody
    @GetMapping("/api")
    public ResponseEntity<UserDTO> getUser(HttpSession http) {
        UserDTO user = UserDTO.of((User) http.getAttribute("user"));
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(user);
    }
}
