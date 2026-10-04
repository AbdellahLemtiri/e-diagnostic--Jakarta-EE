package com.ediagnostic.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ediagnostic.dao.UtilisateurDAO;
import com.ediagnostic.model.Infirmier;
import com.ediagnostic.model.Utilisateur;
import com.ediagnostic.model.enums.Role;
import com.ediagnostic.service.impl.AuthServiceImpl;
import com.ediagnostic.util.auth.PasswordUtil;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UtilisateurDAO utilisateurDAO;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(utilisateurDAO);
    }

    @Test
    void testLoginSuccess() {
        String email = "infirmier@clinique.ma";
        String password = "password123";
        String hashedPassword = PasswordUtil.hashPassword(password);

        Infirmier mockUser = new Infirmier("Alami", "Fatima", email, hashedPassword, "0611223344", "INF-001");

        when(utilisateurDAO.findByEmail(email)).thenReturn(Optional.of(mockUser));

        Optional<Utilisateur> result = authService.login(email, password);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        assertEquals(Role.INFIRMIER, result.get().getRole());
        verify(utilisateurDAO, times(1)).findByEmail(email);
    }

    @Test
    void testLoginFailedWrongPassword() {
        String email = "infirmier@clinique.ma";
        String rightPassword = "password123";
        String wrongPassword = "wrongPassword";
        String hashedPassword = PasswordUtil.hashPassword(rightPassword);

        Infirmier mockUser = new Infirmier("Alami", "Fatima", email, hashedPassword, "0611223344", "INF-001");

        when(utilisateurDAO.findByEmail(email)).thenReturn(Optional.of(mockUser));

        Optional<Utilisateur> result = authService.login(email, wrongPassword);

        assertFalse(result.isPresent());
        verify(utilisateurDAO, times(1)).findByEmail(email);
    }

    @Test
    void testLoginFailedUserNotFound() {
        String email = "notfound@clinique.ma";
        when(utilisateurDAO.findByEmail(email)).thenReturn(Optional.empty());

        Optional<Utilisateur> result = authService.login(email, "password123");

        assertFalse(result.isPresent());
    }
}