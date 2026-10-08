package com.ediagnostic.dao.impl;

import com.ediagnostic.dao.CreneauDAO;
import com.ediagnostic.model.Creneau;
import com.ediagnostic.model.enums.StatutCreneau;
import com.ediagnostic.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CreneauDAOImpl implements CreneauDAO {

    @Override
    public List<Creneau> findCreneauxBySpecialiste(Long specialisteId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Creneau c WHERE c.specialiste.id = :specId " +
                            "AND c.dateHeureDebut > :now ORDER BY c.dateHeureDebut ASC",
                    Creneau.class)
                    .setParameter("specId", specialisteId)
                    .setParameter("now", LocalDateTime.now())
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Creneau> findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Creneau.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Creneau creneau) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(creneau);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}