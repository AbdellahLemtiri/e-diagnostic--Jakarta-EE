package com.ediagnostic.dao.impl;

import com.ediagnostic.dao.SpecialisteDAO;
import com.ediagnostic.model.MedecinSpecialiste;
import com.ediagnostic.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

public class SpecialisteDAOImp implements SpecialisteDAO {

    @Override

    public List<MedecinSpecialiste> findAll() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT s FROM MedecinSpecialiste s where actfi = true ", MedecinSpecialiste.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<MedecinSpecialiste> findById(Long id) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(MedecinSpecialiste.class, id));
        } finally {
            entityManager.close();
        }
    }

}
