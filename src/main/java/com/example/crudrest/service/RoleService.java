package com.example.crudrest.service;

import com.example.crudrest.model.Role;
import com.example.crudrest.model.User;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    Role getRoleByName(String roleName);
    void addRole(Role role);
    void deleteRole(Long id);
    void editRole(Role role);
    Role getRoleById(Long id);
    User getUserRolesByRolesId(User user, Long[] roles);
}