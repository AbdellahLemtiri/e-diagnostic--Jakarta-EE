package com.ediagnostic.service.impl;

import java.util.Optional;

import com.ediagnostic.dao.UtilisateurDAO;
import com.ediagnostic.dao.impl.UtilisateurDAOImpl;
import com.ediagnostic.model.Utilisateur;
import com.ediagnostic.service.AuthService;
import com.ediagnostic.util.auth.PasswordUtil;

public class AuthServiceImpl implements AuthService {

    private final UtilisateurDAO utilisateurDAO;

    public AuthServiceImpl() {
        this.utilisateurDAO = new UtilisateurDAOImpl();
    }

    public AuthServiceImpl(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    @Override
    public Optional<Utilisateur> login(String email, String motDePasse) {
        if (email == null || motDePasse == null || email.trim().isEmpty() || motDePasse.trim().isEmpty()) {
            return Optional.empty();
        }

        Optional<Utilisateur> optUser = utilisateurDAO.findByEmail(email.trim().toLowerCase());
        if (optUser.isEmpty()) {
            return Optional.empty();
        }

        Utilisateur user = optUser.get();
        if (PasswordUtil.checkPassword(motDePasse, user.getMotDePasse())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}