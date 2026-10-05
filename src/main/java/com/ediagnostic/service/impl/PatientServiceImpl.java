package com.ediagnostic.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ediagnostic.dao.PatientDAO;
import com.ediagnostic.dao.SigneVitalDAO;
import com.ediagnostic.dao.impl.PatientDAOImpl;
import com.ediagnostic.dao.impl.SigneVitalDAOImpl;
import com.ediagnostic.model.Infirmier;
import com.ediagnostic.model.Patient;
import com.ediagnostic.model.SigneVital;
import com.ediagnostic.service.PatientService;

import jakarta.persistence.EntityNotFoundException;

public class PatientServiceImpl implements PatientService {

    private final PatientDAO patientDAO;
    private final SigneVitalDAO signeVitalDAO;

    public PatientServiceImpl() {
        this.patientDAO = new PatientDAOImpl();
        this.signeVitalDAO = new SigneVitalDAOImpl();
    }

    public PatientServiceImpl(PatientDAO patientDAO, SigneVitalDAO signeVitalDAO) {
        this.patientDAO = patientDAO;
        this.signeVitalDAO = signeVitalDAO;

    }

    public Optional<Patient> rechercherParNumSecu(String numSecu) {
        if (numSecu == null || numSecu.trim().isEmpty()) {
            return Optional.empty();
        }
        return patientDAO.findByNumSecuriteSociale(numSecu.trim());
    }

    @Override
    public Optional<Patient> trouverParId(Long id) {
        return patientDAO.findById(id);
    }

    @Override
    public Patient enregistrerNouveauPatient(Patient patient, SigneVital signeVital, Infirmier infirmier) {
        patient.setEnAttente(true);
        patient.setDateEnregistrement(LocalDateTime.now());
        Patient savedPatient = patientDAO.save(patient);

        if (signeVital != null) {
            signeVital.setPatient(savedPatient);
            signeVital.setInfirmier(infirmier);
            signeVital.setDatePrise(LocalDateTime.now());
            signeVitalDAO.save(signeVital);
            savedPatient.getSignesVitaux().add(signeVital);
        }
        return savedPatient;
    }

    @Override
    public void ajouterSignesVitauxEtMettreEnAttente(Long patientId, SigneVital signeVital, Infirmier infirmier) {
        Patient patient = patientDAO.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException(" patient introuvalble !"));

        patient.setEnAttente(true);
        if (signeVital != signeVital) {
            signeVital.setPatient(patient);
            signeVital.setInfirmier(infirmier);
            signeVital.setDatePrise(LocalDateTime.now());
            signeVitalDAO.save(signeVital);
            patient.getSignesVitaux().add(signeVital);
            patient.setDateEnregistrement(LocalDateTime.now());
            patientDAO.update(patient);
        }
    }

    @Override
    public List<Patient> getPatientsDuJour(LocalDate date) {

        return patientDAO.findAllPatientsDuJour().stream()
                .filter(p -> p.getDateEnregistrement().toLocalDate().isEqual(date))
                .sorted(Comparator.comparing(Patient::getDateEnregistrement)).collect(Collectors.toList());
    }
    
    @Override
    public List<Patient> getPatientsEnAttente() {
        return patientDAO.findPatientsEnAttente();
    }
}
