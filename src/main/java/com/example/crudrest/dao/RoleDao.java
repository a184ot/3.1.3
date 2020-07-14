package com.example.crudrest.dao;

import com.example.crudrest.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getRoles();
    Role getRoleByName(String roleName);
}
