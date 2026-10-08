package com.ediagnostic.dao;

import com.ediagnostic.model.MedecinSpecialiste;
import java.util.List;
import java.util.Optional;

public interface SpecialisteDAO {

    List<MedecinSpecialiste> findAll();
    Optional<MedecinSpecialiste> findById(Long id);
}
