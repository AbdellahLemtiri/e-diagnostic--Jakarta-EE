package com.ediagnostic.dao;

import java.util.List;
import java.util.Optional;

import com.ediagnostic.model.Consultation;

public interface ConsultationDAO {
    Consultation save(Consultation consultation);
    void update(Consultation consultation);
    Optional<Consultation> findById(Long id);
    List<Consultation> findByGeneralisteId(Long generalisteId);
}