package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements ServiceInt {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional // Обработка транзакции при вставке
    public void insertUser(User user) {
        userRepository.save(user);
    }

    @Transactional // Обработка транзакции при удалении
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true) // Чтение данных
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true) // Чтение с ролями
    public User getUserById(Long id) {
        return userRepository.findByIdWithRoles(id);
    }

    @Override
    @Transactional // Обработка транзакции при обновлении
    public void updateUser(String name, String email, String password, Set<Role> roles, Long id) {
        User userToUpdate = userRepository.findByIdWithRoles(id);
        userToUpdate.setName(name);
        userToUpdate.setEmail(email);
        userToUpdate.setPassword(password);
        userToUpdate.setRoles(roles);
        userRepository.save(userToUpdate);
    }

    @Transactional(readOnly = true) // Чтение данных
    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }
}