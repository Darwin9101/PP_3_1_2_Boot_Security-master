package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceInt;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private ServiceInt serviceInt;

    @Autowired
    public UserController(ServiceInt serviceInt) {
        this.serviceInt = serviceInt;
    }

    @GetMapping
    public String getUsers(ModelMap model) {
        List<User> users = serviceInt.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createUser(User user) {
        serviceInt.insertUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUser(@RequestParam Long id, ModelMap model) {
        User user = serviceInt.getUserById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(@RequestParam Long id, User user) {
        user.setId(id);
        serviceInt.insertUser(user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@RequestParam Long id) {
        serviceInt.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/role")
    public String getUsersByRole(@RequestParam Role role, ModelMap model) {
        List<User> usersByRole = serviceInt.getUserByRole(role);
        model.addAttribute("users", usersByRole);
        return "users"; // Предполагается, что вы хотите отобразить тех же пользователей на странице users
    }
}