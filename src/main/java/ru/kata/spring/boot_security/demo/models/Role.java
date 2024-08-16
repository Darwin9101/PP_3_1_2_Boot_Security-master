package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table (name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String rolle;

    public String getRolle() {
        return rolle;
    }
    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    @Override
    public String getAuthority() {
        return getRolle();
    }
}