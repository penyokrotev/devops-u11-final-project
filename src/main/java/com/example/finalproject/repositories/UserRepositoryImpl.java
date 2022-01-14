package com.example.finalproject.repositories;

import com.example.finalproject.models.SecurityUser;
import com.example.finalproject.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        }
    }

    @Override
    public User getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public User getByName(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User where email=:email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        }
    }

    @Override
    public SecurityUser getSecurityUserByEmail(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<SecurityUser> query = session.createQuery("FROM SecurityUser where username=:username", SecurityUser.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        }
    }

    @Override
    public void updateSecurityUser(SecurityUser securityUser) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(securityUser);
            transaction.commit();
        }
    }


    @Override
    public void create(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.save(user);
        }
    }


    @Override
    public void update(User newUser) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(newUser);
            transaction.commit();
        }
    }

}