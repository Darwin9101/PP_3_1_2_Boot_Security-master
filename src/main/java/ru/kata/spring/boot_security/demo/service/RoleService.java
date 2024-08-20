package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.Collection;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Collection<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
