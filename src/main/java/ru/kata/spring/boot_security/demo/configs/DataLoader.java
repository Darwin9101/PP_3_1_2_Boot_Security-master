package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadRoles();
    }

    private void loadRoles() {
        if (roleRepository.count() == 0) { // Проверяем, есть ли уже роли
            Role adminRole = new Role();
            Role userRole = new Role();
            adminRole.setRole("ROLE_ADMIN");
            userRole.setRole("ROLE_USER");
            roleRepository.saveAll(Arrays.asList(adminRole, userRole));
            System.out.println("Roles loaded: " + Arrays.asList(adminRole, userRole));
        }
    }
}