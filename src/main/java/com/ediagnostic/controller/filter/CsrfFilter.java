package com.ediagnostic.controller.filter;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(filterName = "CsrfFilter", urlPatterns = {"/*"})
public class CsrfFilter implements Filter {

    private static final String CSRF_TOKEN_SESSION = "CSRF_TOKEN";
    private static final String CSRF_TOKEN_PARAM = "_csrf";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(true);
        String sessionToken = (String) session.getAttribute(CSRF_TOKEN_SESSION);

        if (sessionToken == null) {
            sessionToken = generateNewToken();
            session.setAttribute(CSRF_TOKEN_SESSION, sessionToken);
        }

        request.setAttribute("csrfToken", sessionToken);

        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();
        if ("POST".equalsIgnoreCase(method) && !uri.endsWith("/login")) {
            String requestToken = httpRequest.getParameter(CSRF_TOKEN_PARAM);

            if (requestToken == null || !sessionToken.equals(requestToken)) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Requête rejetée : Jeton CSRF invalide ou manquant");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String generateNewToken() {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}