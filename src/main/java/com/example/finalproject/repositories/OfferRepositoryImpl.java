package com.example.finalproject.repositories;

import com.example.finalproject.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class OfferRepositoryImpl implements OfferRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public OfferRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<CarBrand> getAllCarBrands() {
        try (Session session = sessionFactory.openSession()) {
            Query<CarBrand> query = session.createQuery("FROM CarBrand", CarBrand.class);
            return query.list();
        }
    }

    @Override
    public List<CarModel> getAllCarModels() {
        try (Session session = sessionFactory.openSession()) {
            Query<CarModel> query = session.createQuery("FROM CarModel", CarModel.class);
            return query.list();
        }
    }

    @Override
    public AgeAndCoef getAgeAndCoef() {
        try (Session session = sessionFactory.openSession()) {
            return session.get(AgeAndCoef.class, 1);
        }
    }

    @Override
    public BigDecimal getAccidentCoef() {
        try (Session session = sessionFactory.openSession()) {
            return session.get(AccidentCoef.class, 1).getAccidentCoef();
        }
    }

    @Override
    public BigDecimal getTaxAmount() {
        try (Session session = sessionFactory.openSession()) {
            return session.get(TaxAmount.class, 1).getTaxAmount();
        }
    }

    @Override
    public List<OfferMulticriteria> getMulticriterias() {
        try (Session session = sessionFactory.openSession()) {
            Query<OfferMulticriteria> query = session.createQuery("FROM OfferMulticriteria", OfferMulticriteria.class);
            return query.list();
        }
    }

    @Override
    public void updateMulticriteria(OfferMulticriteria offerMulticriteria) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(offerMulticriteria);
            transaction.commit();
        }
    }


    @Override
    public void create(Offer offer) {
        try (Session session = sessionFactory.openSession()) {
            session.save(offer);
        }
    }

    @Override
    public Offer getByUUID(String guid) {
        try (Session session = sessionFactory.openSession()) {
            Query<Offer> query = session.createQuery("FROM Offer WHERE offer_guid=:guid", Offer.class);
            query.setParameter("guid", guid);
            return query.getSingleResult();
        }
    }

    @Override
    public void updateMulticriteriaInformation(MulticriteriaHistory multicriteriaHistory, CurrentMulticriteria currentMulticriteria) {
        try (Session session = sessionFactory.openSession()) {
            session.save(multicriteriaHistory);
            Transaction transaction = session.beginTransaction();
            session.update(currentMulticriteria);
            transaction.commit();
        }
    }

    @Override
    public CurrentMulticriteria getCurrentMulticriteria() {
        try (Session session = sessionFactory.openSession()) {
            Query<CurrentMulticriteria> query = session.createQuery("FROM CurrentMulticriteria", CurrentMulticriteria.class);
            return query.getSingleResult();
        }
    }


}
