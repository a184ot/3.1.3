package com.example.crudrest.service;

import com.example.crudrest.dao.RoleDao;
import com.example.crudrest.model.Role;
import com.example.crudrest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getRoles() {
        return roleDao.getAllRole();
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleDao.getRoleByName(roleName);
    }

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }

    @Override
    public void deleteRole(Long id) {
        roleDao.deleteRole(id);
    }

    @Override
    public void editRole(Role role) {
        roleDao.editRole(role);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public User getUserRolesByRolesId(User user, Long[] roles) {
        Set<Role> updatedRoles = new HashSet<>();
        for (Long id : roles) {
            updatedRoles.add(getRoleById(id));
        }
        user.setRole(updatedRoles);
        return user;
    }
}

