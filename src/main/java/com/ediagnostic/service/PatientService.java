package com.ediagnostic.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ediagnostic.model.Infirmier;
import com.ediagnostic.model.Patient;
import com.ediagnostic.model.SigneVital;

public interface PatientService {
    Optional<Patient> rechercherParNumSecu(String numSecu);
    Optional<Patient> trouverParId(Long id);
    Patient enregistrerNouveauPatient(Patient patient, SigneVital signeVital, Infirmier infirmier);
    void ajouterSignesVitauxEtMettreEnAttente(Long patientId, SigneVital signeVital, Infirmier infirmier);
    List<Patient> getPatientsDuJour(LocalDate date);
    List<Patient> getPatientsEnAttente();
}