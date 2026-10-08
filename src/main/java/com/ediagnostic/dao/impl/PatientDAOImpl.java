package com.ediagnostic.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.ediagnostic.dao.PatientDAO;
import com.ediagnostic.model.Patient;
import com.ediagnostic.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class PatientDAOImpl implements PatientDAO {

    @Override
    public Optional<Patient> findByNumSecuriteSociale(String numSecu) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            Patient patient = entityManager.createQuery(
                    "SELECT p FROM Patient p WHERE p.numSecuriteSociale = :numSecu", Patient.class)
                    .setParameter("numSecu", numSecu)
                    .getSingleResult();
            return Optional.of(patient);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Patient> findById(Long id) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(Patient.class, id));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Patient save(Patient patient) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(patient);
            tx.commit();
            return patient;
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(Patient patient) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.merge(patient);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Patient> findAllPatientsDuJour() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

            return entityManager.createQuery(
                    "SELECT DISTINCT p FROM Patient p " +
                            "LEFT JOIN FETCH p.signesVitaux " +
                            "WHERE p.dateEnregistrement BETWEEN :start AND :end " +
                            "ORDER BY p.dateEnregistrement ASC",
                    Patient.class)
                    .setParameter("start", startOfDay)
                    .setParameter("end", endOfDay)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Patient> findPatientsEnAttente() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            return entityManager.createQuery(
                    "SELECT DISTINCT p FROM Patient p " +
                            "LEFT JOIN FETCH p.signesVitaux " +
                            "WHERE p.enAttente = true " +
                            "ORDER BY p.dateEnregistrement ASC",
                    Patient.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }
}