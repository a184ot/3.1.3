package com.example.crudrest.dao;

import com.example.crudrest.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getAllRole();
    Role getRoleByName(String roleName);
    void addRole(Role role);
    void deleteRole(Long id);
    void editRole(Role role);
    Role getRoleById(Long id);
}
