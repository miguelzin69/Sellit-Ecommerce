package com.sellit.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellit.project.entities.User;

public interface UserRepository extends JpaRepository<User, String>{

    User findByNameIgnoreCase(String name);

    
}
