package com.example.crudrest.dao;

import com.example.crudrest.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getRoles() {
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
}
