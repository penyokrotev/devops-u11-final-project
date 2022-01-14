package com.example.finalproject.repositories;

import com.example.finalproject.models.ConfirmationToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ConfirmationTokenRepositoryImpl implements ConfirmationTokenRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public ConfirmationTokenRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void save(ConfirmationToken confirmationToken) {
        try (Session session = sessionFactory.openSession()) {
            session.save(confirmationToken);
        }
    }

    @Override
    public ConfirmationToken getByConfirmationToken(String confirmationToken) {
        try (Session session = sessionFactory.openSession()) {
            Query<ConfirmationToken> query = session.createQuery("FROM ConfirmationToken where confirmationToken=:confirmationToken", ConfirmationToken.class);
            query.setParameter("confirmationToken", confirmationToken);
            return query.getSingleResult();
        }
    }
}
