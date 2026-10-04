package com.ediagnostic.dao;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ediagnostic.model.ActeTechnique;
import com.ediagnostic.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class JpaConnectionTest {

    private static EntityManager em;

    @BeforeAll
    public static void setUp() {
        em = HibernateUtil.getEntityManager();
        assertNotNull(em, "n'est pas null ");
    }

    @Test
    public void testPersistAndFindActeTechnique() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        String uniqueCode = "ECG-" + System.currentTimeMillis();
        ActeTechnique acte = new ActeTechnique("Électrocardiogramme", uniqueCode, 200.0, "Test diagnostic cœur");
        em.persist(acte);
        tx.commit();

        assertNotNull(acte.getId(), "automatiquement");

        ActeTechnique found = em.find(ActeTechnique.class, acte.getId());
        assertNotNull(found);
        assertEquals(uniqueCode, found.getCode());
    }

    @AfterAll
    public static void tearDown() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        HibernateUtil.shutdown();
    }
}