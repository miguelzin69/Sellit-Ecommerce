package com.sellit.project.dto;

import com.sellit.project.entities.Role;

public record UserRegisterDto(Long id, String name, String password, String email, Role role) {
    
}
