package com.example.crudrest.dao;

import com.example.crudrest.model.User;
import com.example.crudrest.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    PasswordEncoder passwordEncoder;

    private RoleService roleService;

    @Autowired
    public UserDaoImpl(RoleService roleService ,PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        entityManager.remove(user);
    }

    @Override
    public void editUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listAllUsers() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public User getUserByLogin(String email) {
        try{
            User user = entityManager
                    .createQuery(
                            "SELECT u from User u WHERE u.email = :email", User.class).
                            setParameter("email", email).getSingleResult();
            return user;
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class,id);
    }

    @Override
    public User findByUsername(String username) {
        return getUserByLogin(username);
    }

}
