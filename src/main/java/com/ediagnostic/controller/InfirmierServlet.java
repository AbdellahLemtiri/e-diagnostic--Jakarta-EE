package com.ediagnostic.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ediagnostic.model.Infirmier;
import com.ediagnostic.model.Patient;
import com.ediagnostic.model.SigneVital;
import com.ediagnostic.service.PatientService;
import com.ediagnostic.service.impl.PatientServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/infirmier/enregistrer".equals(path)) {
            enregistrerPatientOuSignes(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/infirmier/accueil");
        }

    }

    private void rechercherPatient(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String numSecu = req.getParameter("numSecu");
        if (numSecu != null && !numSecu.trim().isEmpty()) {
            Optional<Patient> patientOpt = patientService.rechercherParNumSecu(numSecu);
            if (patientOpt.isPresent()) {
                req.setAttribute("patientExistant", patientOpt.get());
            } else {
                req.setAttribute("patientNonTrouve", true);
                req.setAttribute("numSecuRecherche", numSecu);
            }
        }
        req.getRequestDispatcher("/WEB-INF/views/infirmier/accueil-patient.jsp").forward(req, resp);
    }

    private void afficherFileAttente(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Patient> patients = patientService.getPatientsDuJour(LocalDate.now());
        req.setAttribute("patientsDuJour", patients);
        req.getRequestDispatcher("/WEB-INF/views/infirmier/file-attente.jsp").forward(req, resp);
    }

    private void enregistrerPatientOuSignes(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        Infirmier infirmier = (session != null) ? (Infirmier) session.getAttribute("currentUser") : null;

        String patientIdStr = req.getParameter("patientId");

        String tension = req.getParameter("tension");
        Integer frequenceCardiaque = parseInteger(req.getParameter("frequenceCardiaque"));
        Double temperature = parseDouble(req.getParameter("temperature"));
        Integer frequenceRespiratoire = parseInteger(req.getParameter("frequenceRespiratoire"));
        Double poids = parseDouble(req.getParameter("poids"));
        Double taille = parseDouble(req.getParameter("taille"));

        SigneVital signeVital = new SigneVital(tension, frequenceCardiaque, temperature,
                frequenceRespiratoire, poids, taille, null, infirmier);

        if (patientIdStr != null && !patientIdStr.trim().isEmpty()) {
            Long patientId = Long.parseLong(patientIdStr);
            patientService.ajouterSignesVitauxEtMettreEnAttente(patientId, signeVital, infirmier);
        } else {
            String nom = req.getParameter("nom");
            String prenom = req.getParameter("prenom");
            LocalDate dateNaissance = LocalDate.parse(req.getParameter("dateNaissance"));
            String numSecu = req.getParameter("numSecu");
            String telephone = req.getParameter("telephone");
            String adresse = req.getParameter("adresse");
            String mutuelle = req.getParameter("mutuelle");

            Patient patient = new Patient(nom, prenom, dateNaissance, numSecu, telephone, adresse, mutuelle);
            patient.setAntecedents(req.getParameter("antecedents"));
            patient.setAllergies(req.getParameter("allergies"));
            patient.setTraitementsEnCours(req.getParameter("traitementsEnCours"));

            patientService.enregistrerNouveauPatient(patient, signeVital, infirmier);
        }

        resp.sendRedirect(req.getContextPath() + "/infirmier/file-attente?success=registered");
    }

    private Integer parseInteger(String val) {
        return (val != null && !val.trim().isEmpty()) ? Integer.parseInt(val.trim()) : null;
    }

    private Double parseDouble(String val) {
        return (val != null && !val.trim().isEmpty()) ? Double.parseDouble(val.trim()) : null;
    }
}
