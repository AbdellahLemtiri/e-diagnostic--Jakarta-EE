package com.ediagnostic.dao;

import java.util.List;

import com.ediagnostic.model.SigneVital;

public interface  SigneVitalDAO {
    void save(SigneVital signeVital);
    List<SigneVital> findByPatientId(Long patientId);
}
