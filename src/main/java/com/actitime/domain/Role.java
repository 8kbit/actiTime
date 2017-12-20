package com.actitime.domain;

import javax.persistence.*;

@Entity
@Table(name = "sec_role")
public class Role extends AbstractEntity{

    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";

    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
