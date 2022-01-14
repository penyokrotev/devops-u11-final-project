package com.example.finalproject.repositories;

import com.example.finalproject.models.Policy;
import com.example.finalproject.models.StatusType;
import com.example.finalproject.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PolicyRepositoryImpl implements PolicyRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public PolicyRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public StatusType getStatusById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(StatusType.class, id);
        }
    }

    @Override
    public void createPolicy(Policy policy) {
        try (Session session = sessionFactory.openSession()) {
            session.save(policy);
        }
    }

    @Override
    public List<Policy> getPendingPolicies() {
        try (Session session = sessionFactory.openSession()) {
            Query<Policy> query = session.createNativeQuery("SELECT *\n" +
                    "FROM policies as p\n" +
                    "join status_type as st on p.status_id = st.status_id\n" +
                    "where p.status_id = 1;\n", Policy.class);
            return query.list();
        }
    }

    @Override
    public List<Policy> getAllPolicies() {
        try (Session session = sessionFactory.openSession()) {
            Query<Policy> query = session.createNativeQuery("SELECT * " +
                    "FROM policies p " +
                    "JOIN status_type st ON p.status_id = st.status_id", Policy.class);
            return query.list();
        }
    }

    @Override
    public Policy getPolicyById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Policy.class, id);
        }
    }

    @Override
    public List<Policy> getPendingPoliciesByUserId(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Policy> query = session.createNativeQuery(String.format("SELECT *\n" +
                    "FROM policies as p\n" +
                    "join user_details as ud on p.user_details_id = ud.user_details_id\n" +
                    "join status_type as st on p.status_id = st.status_id\n" +
                    "where p.status_id = 1 AND p.user_details_id = %d;\n",id), Policy.class);

            return query.list();
        }
    }

    @Override
    public void updatePolicy(Policy policy) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(policy);
            transaction.commit();
        }
    }

    @Override
    public List<Policy> getPoliciesByUserId(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Policy> query = session.createNativeQuery(String.format("SELECT *\n" +
                    "FROM policies as p\n" +
                    "join user_details as ud on p.user_details_id = ud.user_details_id\n" +
                    "where p.user_details_id = %d\n", id), Policy.class);
            return query.list();
        }
    }


    @Override
    public StatusType findPolicyByStatus(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<StatusType> query = session.createQuery("FROM StatusType " +
                    "WHERE status = :name ", StatusType.class)
                    .setParameter("name", name);
            List<StatusType> statusTypes = query.list();
            if (statusTypes.isEmpty()) {
                return null;
            }
            return statusTypes.get(0);
        }
    }

    @Override
    public User findPolicyByUsername(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User " +
                    "where email = :name ", User.class)
                    .setParameter("name", name);
            List<User> users = query.list();
            if (users.isEmpty()) {
                return null;
            }
            return users.get(0);
        }
    }

}
