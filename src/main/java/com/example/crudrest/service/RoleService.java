package com.example.crudrest.service;

import com.example.crudrest.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    Role getRoleByName(String roleName);
}