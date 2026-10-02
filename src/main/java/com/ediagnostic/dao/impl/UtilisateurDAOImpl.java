package com.ediagnostic.dao.impl;

import java.util.Optional;

import com.ediagnostic.dao.UtilisateurDAO;
import com.ediagnostic.model.Utilisateur;
import com.ediagnostic.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class UtilisateurDAOImpl implements UtilisateurDAO {

    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            Utilisateur user = entityManager.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.email = :email AND u.actif = true", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void save(Utilisateur utilisateur) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(utilisateur);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }
}