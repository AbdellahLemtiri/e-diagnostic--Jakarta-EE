package com.ediagnostic.controller;

import java.io.IOException;
import java.util.Optional;

import com.ediagnostic.model.Utilisateur;
import com.ediagnostic.model.enums.Role;
import com.ediagnostic.service.AuthService;
import com.ediagnostic.service.impl.AuthServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AuthServlet", urlPatterns = {"/login", "/logout"})
public class AuthServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() throws ServletException {
        this.authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/logout".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login?logout=true");
            return;
        }

     
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("currentUser") != null) {
            Utilisateur user = (Utilisateur) session.getAttribute("currentUser");
            redirigerSelonRole(user.getRole(), request, response);
            return;
        } 

        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        Optional<Utilisateur> optUser = authService.login(email, motDePasse);

        if (optUser.isPresent()) {
            Utilisateur user = optUser.get();
            HttpSession session = request.getSession(true);
            session.setAttribute("currentUser", user);
            session.setAttribute("userRole", user.getRole().name());
            session.setAttribute("userName", user.getNomComplet());

            redirigerSelonRole(user.getRole(), request, response);
        } else {
            request.setAttribute("errorMessage", "Email ou mot de passe incorrect");
            request.setAttribute("emailValue", email);
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        }
    }

    private void redirigerSelonRole(Role role, HttpServletRequest req, HttpServletResponse resp) 
            throws IOException {
        String context = req.getContextPath();
        switch (role) {
            case INFIRMIER:
                resp.sendRedirect(context + "/infirmier/accueil");
                break;
            case GENERALISTE:
                resp.sendRedirect(context + "/generaliste/consultations");
                break;
            case SPECIALISTE:
                resp.sendRedirect(context + "/specialiste/dashboard");
                break;
            default:
                resp.sendRedirect(context + "/login");
                break;
        }
    }   
}