package com.ediagnostic.dao.impl;

import java.util.List;
import java.util.Optional;

import com.ediagnostic.dao.ConsultationDAO;
import com.ediagnostic.model.Consultation;
import com.ediagnostic.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ConsultationDAOImpl implements ConsultationDAO {

    @Override
    public Consultation save(Consultation consultation) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(consultation);
            tx.commit();
            return consultation;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Consultation consultation) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(consultation);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Consultation> findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Consultation.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Consultation> findByGeneralisteId(Long generalisteId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT c FROM Consultation c WHERE c.generaliste.id = :genId ORDER BY c.dateConsultation DESC", 
                Consultation.class)
                .setParameter("genId", generalisteId)
                .getResultList();
        } finally {
            em.close();
        }
    }
}