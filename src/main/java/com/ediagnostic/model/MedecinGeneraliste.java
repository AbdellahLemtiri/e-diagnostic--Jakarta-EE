package com.ediagnostic.model;

import com.ediagnostic.model.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "medecins_generalistes")
public class MedecinGeneraliste extends Utilisateur {

    @Column(name = "matricule_ordre ", nullable = false)
    String matriculeOrdre;

    public MedecinGeneraliste() {
        super();
        this.setRole(Role.GENERALISTE);

    }

    public MedecinGeneraliste(String nom, String prenom, String email, String motDePasse, String telephone,
            String matriculeOrdre, Role role) {
        super(nom, prenom, email, motDePasse, telephone, role);
        this.matriculeOrdre = matriculeOrdre;
    }

    public String getMatriculeOrdre() {
        return matriculeOrdre;
    }

    public void setMatriculeOrdre(String matriculeOrdre) {
        this.matriculeOrdre = matriculeOrdre;
    }
}