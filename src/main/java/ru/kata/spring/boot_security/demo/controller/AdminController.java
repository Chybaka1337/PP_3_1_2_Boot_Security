package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

    public AdminController(UserService userService, RoleService roleService) {
        super(userService, roleService);
    }

    @GetMapping()
    public ModelAndView getPage(ModelAndView mav) {
        mav.setViewName("admin");
        return mav;
    }

    @ResponseBody
    @GetMapping("/api")
    public ResponseEntity<UserDTO> getAdmin(HttpSession http) {
        UserDTO user = UserDTO.of((User) http.getAttribute("user"));
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(user);
    }

    @ResponseBody
    @GetMapping("/api/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = getUserService().getAll()
                .stream()
                .map(UserDTO::of)
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(users);
    }

    @ResponseBody
    @GetMapping("/api/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        UserDTO user = UserDTO.of(getUserService().getUserById(id));
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(user);
    }

    @ResponseBody
    @GetMapping("/api/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(getRoleService().getAll());
    }

    @ResponseBody
    @PostMapping("/api/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        getUserService().save(userDTO.toUser());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDTO);
    }

    @ResponseBody
    @PostMapping("/api/user/{id}")
    public ResponseEntity<UserDTO> editUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        getUserService().update(id, userDTO.toUser());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(userDTO);
    }

    @ResponseBody
    @GetMapping("/api/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id) {
        User user = getUserService().getUserById(id);
        if (user != null) {
            getUserService().delete(user);
        }
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }


}
