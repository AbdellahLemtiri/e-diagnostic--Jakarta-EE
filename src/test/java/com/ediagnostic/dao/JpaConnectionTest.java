package com.ediagnostic.dao;

import com.ediagnostic.model.ActeTechnique;
import com.ediagnostic.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JpaConnectionTest {

    private static EntityManager em;

    @BeforeAll
    public static void setUp() {
        em = HibernateUtil.getEntityManager();
        assertNotNull(em, "L'EntityManager ma khassouch ykoun null");
    }

    @Test
    public void testPersistAndFindActeTechnique() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 1. Création d'une entité de test
        ActeTechnique acte = new ActeTechnique("Électrocardiogramme", "ECG-01", 200.0, "Test diagnostic cœur");
        em.persist(acte);
        tx.commit();

        // 2. Vérification de la persistance
        assertNotNull(acte.getId(), "L'ID khasso yt-généra automatiquement");

        // 3. Lecture depuis la base
        ActeTechnique found = em.find(ActeTechnique.class, acte.getId());
        assertNotNull(found);
        assertEquals("ECG-01", found.getCode());
    }

    @AfterAll
    public static void tearDown() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        HibernateUtil.shutdown();
    }
}