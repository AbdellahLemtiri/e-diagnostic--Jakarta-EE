package com.ediagnostic.dao;

import com.ediagnostic.model.Creneau;
import java.util.List;
import java.util.Optional;

public interface CreneauDAO {
    List<Creneau> findCreneauxBySpecialiste(Long specialisteId);
    Optional<Creneau> findById(Long id);
    void update(Creneau creneau);
}