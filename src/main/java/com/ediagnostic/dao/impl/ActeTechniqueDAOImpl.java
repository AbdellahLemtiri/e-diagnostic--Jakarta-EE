package com.ediagnostic.dao.impl;

import java.util.List;
import java.util.Optional;

import com.ediagnostic.dao.ActeTechniqueDAO;
import com.ediagnostic.model.ActeTechnique;
import com.ediagnostic.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ActeTechniqueDAOImpl implements ActeTechniqueDAO {

    @Override
    public List<ActeTechnique> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT a FROM ActeTechnique a", ActeTechnique.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<ActeTechnique> findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(ActeTechnique.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public void save(ActeTechnique acteTechnique) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(acteTechnique);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}