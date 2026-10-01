package com.ediagnostic.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "actes_techniques")
public class ActeTechnique implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String libelle;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(nullable = false)
    private Double tarif;

    @Column(columnDefinition = "TEXT")
    private String description;

    public ActeTechnique() {}

    public ActeTechnique(String libelle, String code, Double tarif, String description) {
        this.libelle = libelle;
        this.code = code;
        this.tarif = tarif;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Double getTarif() { return tarif; }
    public void setTarif(Double tarif) { this.tarif = tarif; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}