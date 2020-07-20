package com.example.crudrest.dao;

import com.example.crudrest.model.Role;
import com.example.crudrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;





    @Override
    public List<Role> getAllRole() {
        return entityManager.createQuery("from Role").getResultList();
    }

    @Override
    public Role getRoleByName(String roleName) {
        try{
            Role role =
                    entityManager.createQuery(
                            "SELECT u from Role u WHERE u.name = :name", Role.class).
                            setParameter("name", roleName).getSingleResult();
            return role;
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = getRoleById(id);
        entityManager.remove(role);
    }

    @Override
    public void editRole(Role role) {
        entityManager.merge(role);
    }

    @Override
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class,id);    }
}
