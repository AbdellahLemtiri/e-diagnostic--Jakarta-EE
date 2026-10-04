package com.ediagnostic.dao;

import com.ediagnostic.model.ActeTechnique;
import java.util.List;
import java.util.Optional;

public interface ActeTechniqueDAO {
    List<ActeTechnique> findAll();
    Optional<ActeTechnique> findById(Long id);
    void save(ActeTechnique acteTechnique);
}