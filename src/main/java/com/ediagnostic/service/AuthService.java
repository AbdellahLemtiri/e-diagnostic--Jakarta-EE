package com.ediagnostic.service;

import java.util.Optional;

import com.ediagnostic.model.Utilisateur;

public interface AuthService {
    Optional<Utilisateur> login(String email, String motDePasse);
}