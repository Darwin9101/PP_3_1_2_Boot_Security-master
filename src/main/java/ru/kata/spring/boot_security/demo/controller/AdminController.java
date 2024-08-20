package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.ServiceInt;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ServiceInt serviceInt;
    private final RoleService roleService;

    @Autowired
    public AdminController(ServiceInt serviceInt, RoleService roleService) {
        this.serviceInt = serviceInt;
        this.roleService = roleService;
    }

    @GetMapping
    public String index(ModelMap model) {
        List<User> users = serviceInt.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/create")
    public String showAddUserForm(ModelMap model) {
        model.addAttribute("user", new User());
        Collection<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);
        return "addUser"; // Страница для добавления пользователя
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        serviceInt.insertUser(user);
        return "redirect:/admin"; // Перенаправление на список пользователей
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, ModelMap model) {
        User user = serviceInt.getUserById(id);
        model.addAttribute("user", user);
        Collection<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);
        return "editUser"; // Страница для редактирования пользователя
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@ModelAttribute User user, @PathVariable Long id) {
        serviceInt.updateUser(user, id);
        return "redirect:/admin"; // Перенаправление после обновления
    }

    @PostMapping("/delete/{id}")

    public String deleteUser(@PathVariable Long id) {
        serviceInt.deleteUser(id);
        return "redirect:/admin"; // Перенаправление после удаления
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login"; // Перенаправление на страницу логина после выхода
    }

}