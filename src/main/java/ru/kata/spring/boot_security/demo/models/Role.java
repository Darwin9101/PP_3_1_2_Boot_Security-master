package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
    private Long id;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String rolle) {
        this.role = rolle;
    }

    @Override
    public String getAuthority() {
        return getRole();
    }
}