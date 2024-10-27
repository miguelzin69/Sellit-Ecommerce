package com.sellit.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sellit.project.entities.Role;
import com.sellit.project.repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository repository;

    public Role findByName(String name){
        return repository.findByName(name);
    }
}
