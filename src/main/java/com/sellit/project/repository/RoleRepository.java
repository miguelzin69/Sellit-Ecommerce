package com.sellit.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellit.project.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

     Role findByName(String name);

}
