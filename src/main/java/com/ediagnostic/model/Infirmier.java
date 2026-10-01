package com.ediagnostic.model;

import com.ediagnostic.model.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "infirmiers")
public class Infirmier extends Utilisateur {

    @Column(name = "matricule_pro", unique = true, length = 50)
    private String matriculePro;

    public Infirmier() {
        super();
        this.setRole(Role.INFIRMIER);
    }

    public Infirmier(String nom, String prenom, String email, String motDePasse, String telephone, String matriculePro) {
        super(nom, prenom, email, motDePasse, telephone, Role.INFIRMIER);
        this.matriculePro = matriculePro;
    }

    public String getMatriculePro() { return matriculePro; }
    public void setMatriculePro(String matriculePro) { this.matriculePro = matriculePro; }
}