package com.ediagnostic.controller;

import java.io.IOException;

import com.ediagnostic.service.PatientService;
import com.ediagnostic.service.impl.PatientServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "InfirmierServlet", urlPatterns = {
        "/infirmier/accueil",
        "/infirmier/rechercher",
        "/infirmier/enregistrer",
        "/infirmier/file-attente"
})

public class InfirmierServlet extends HttpServlet {

    private PatientService patientService;

    @Override
    public void init() throws ServletException {
        this.patientService = new PatientServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/infirmier/rechercher":
                rechercherPatient(request, response);
                break;
            case "/infirmier/file-attente":
                afficherFileAttente(request, response);
                break;
            case "/infirmier/accueil":
            default:
                request.getRequestDispatcher("/WEB-INF/views/infirmier/accueil-patient.jsp").forward(request, response);
                break;
        }
    }





    
}
