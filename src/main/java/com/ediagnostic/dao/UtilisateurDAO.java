package com.ediagnostic.dao;

import java.util.Optional;

import com.ediagnostic.model.Utilisateur;

public interface UtilisateurDAO {
    Optional<Utilisateur> findByEmail(String email);
    void save(Utilisateur utilisateur);
}