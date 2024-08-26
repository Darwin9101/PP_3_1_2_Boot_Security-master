package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceInt;
import ru.kata.spring.boot_security.demo.service.ServiceInt;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ServiceInt userService;
    private final RoleServiceInt roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    @GetMapping
    public String index(ModelMap model, Principal principal) {
        List<User> users = userService.getAllUsers(); // Измененный вызов
        model.addAttribute("users", users);

        if (principal != null) {
            User user = userService.getUserByName(principal.getName());
            model.addAttribute("loggedInAdmin", user);
        }

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
    @Transactional
    public String createUser(@ModelAttribute User user) {
        userService.insertUser(user); // Измененный вызов
        return "redirect:/admin"; // Перенаправление на список пользователей
    }

    @Transactional(readOnly = true)
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, ModelMap model) {
        User user = userService.getUserById(id); // Измененный вызов
        model.addAttribute("user", user);
        Collection<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);
        return "editUser"; // Страница для редактирования пользователя
    }

    @PostMapping("/edit/{id}")
    @Transactional
    public String updateUser(@RequestParam String name,
                             @RequestParam String email,
                             @RequestParam(required = false) String password, // Пароль будет необязательным
                             @RequestParam Set<Role> roles,
                             @PathVariable Long id) {
        userService.updateUser(name, email, password, roles, id); // Передайте параметры в сервис
        return "redirect:/admin"; // Перенаправление после обновления
    }

    @PostMapping("/delete/{id}")
    @Transactional
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); // Измененный вызов
        return "redirect:/admin"; // Перенаправление после удаления
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login"; // Перенаправление на страницу логина после выхода
    }

    @Transactional(readOnly = true)
    @GetMapping("/admin")
    public String adminPage(ModelMap model, Principal principal) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("loggedInAdmin", user);
        return "admin"; // имя вашего шаблона
    }
}