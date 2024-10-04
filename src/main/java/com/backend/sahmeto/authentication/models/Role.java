package com.backend.sahmeto.authentication.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private ERole name;

    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

}


