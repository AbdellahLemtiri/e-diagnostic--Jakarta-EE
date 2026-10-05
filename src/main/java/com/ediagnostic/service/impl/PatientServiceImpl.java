package com.ediagnostic.service.impl;

import java.util.Optional;

import com.ediagnostic.dao.PatientDAO;
import com.ediagnostic.dao.SigneVitalDAO;
import com.ediagnostic.dao.impl.PatientDAOImpl;
import com.ediagnostic.dao.impl.SigneVitalDAOImpl;
import com.ediagnostic.model.Patient;
import com.ediagnostic.service.PatientService;

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

    

}
