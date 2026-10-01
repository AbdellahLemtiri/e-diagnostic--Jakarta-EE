package com.ediagnostic.model;

import com.ediagnostic.model.enums.Role;
import com.ediagnostic.model.enums.SpecialiteMedicale;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medecins_specialistes")
public class MedecinSpecialiste extends Utilisateur {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SpecialiteMedicale specialite;

    @Column(name = "tarif_expertise", nullable = false)
    private Double tarifExpertise;

    @Column(name = "duree_consultation_min", nullable = false)
    private Integer dureeConsultationMin = 30;

    @OneToMany(mappedBy = "specialiste", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Creneau> creneaux = new ArrayList<>();

    public MedecinSpecialiste() {
        super();
        this.setRole(Role.SPECIALISTE);
    }

    public MedecinSpecialiste(String nom, String prenom, String email, String motDePasse, String telephone,
                              SpecialiteMedicale specialite, Double tarifExpertise) {
        super(nom, prenom, email, motDePasse, telephone, Role.SPECIALISTE);
        this.specialite = specialite;
        this.tarifExpertise = tarifExpertise;
        this.dureeConsultationMin = 30;
    }

    public SpecialiteMedicale getSpecialite() { return specialite; }
    public void setSpecialite(SpecialiteMedicale specialite) { this.specialite = specialite; }

    public Double getTarifExpertise() { return tarifExpertise; }
    public void setTarifExpertise(Double tarifExpertise) { this.tarifExpertise = tarifExpertise; }

    public Integer getDureeConsultationMin() { return dureeConsultationMin; }
    public void setDureeConsultationMin(Integer dureeConsultationMin) { this.dureeConsultationMin = dureeConsultationMin; }

    public List<Creneau> getCreneaux() { return creneaux; }
    public void setCreneaux(List<Creneau> creneaux) { this.creneaux = creneaux; }
}