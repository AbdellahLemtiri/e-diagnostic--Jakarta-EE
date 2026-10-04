package com.ediagnostic.dao.impl;

import java.util.List;

import com.ediagnostic.dao.SigneVitalDAO;
import com.ediagnostic.model.SigneVital;
import com.ediagnostic.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class SigneVitalDAOImpl implements SigneVitalDAO {
    @Override

    public void save(SigneVital signeVital) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.persist(signeVital);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }

    }

    @Override
    public List<SigneVital> findByPatientId(Long patientId) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            return entityManager.createQuery(
                    "SELECT s FROM SigneVital s WHERE s.patient.id = :patientId ORDER BY s.datePrise DESC",
                    SigneVital.class)
                    .setParameter("patientId", patientId)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }
}