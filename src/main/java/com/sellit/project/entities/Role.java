package com.sellit.project.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "roles")  // Typically lowercase and without special characters
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Use Long or int with GenerationType.IDENTITY

    @Column(unique = true, nullable = false)
    private String name;  

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<User> users = new HashSet<>();
}
