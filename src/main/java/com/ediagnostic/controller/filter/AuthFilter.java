package com.ediagnostic.controller.filter;

import java.io.IOException;

import com.ediagnostic.model.Utilisateur;
import com.ediagnostic.model.enums.Role;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/infirmier/*", "/generaliste/*", "/specialiste/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        Utilisateur user = (session != null) ? (Utilisateur) session.getAttribute("currentUser") : null;

        if (user == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?error=unauthorized");
            return;
        }

        String uri = httpRequest.getRequestURI();
        Role userRole = user.getRole();


        if (uri.contains("/infirmier/") && userRole != Role.INFIRMIER) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé au personnel infirmier");
            return;
        }

        if (uri.contains("/generaliste/") && userRole != Role.GENERALISTE) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé aux médecins généralistes");
            return;
        }

        if (uri.contains("/specialiste/") && userRole != Role.SPECIALISTE) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé aux médecins spécialistes");
            return;
        }

        chain.doFilter(request, response);
    }
}