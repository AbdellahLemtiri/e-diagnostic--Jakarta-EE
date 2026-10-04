package com.ediagnostic.dao;

import java.util.List;
import java.util.Optional;

import com.ediagnostic.model.Patient;

public interface PatientDAO {
    Optional<Patient> findByNumSecuriteSociale(String numSecu);
    Optional<Patient> findById(Long id);
    Patient save(Patient patient);
    void update(Patient patient);
    List<Patient> findAllPatientsDuJour();
    List<Patient> findPatientsEnAttente();
}