package com.ediagnostic.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String prenom;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "num_securite_sociale", unique = true, nullable = false, length = 50)
    private String numSecuriteSociale;

    @Column(length = 20)
    private String telephone;

    @Column(length = 255)
    private String adresse;

    @Column(length = 100)
    private String mutuelle;

    @Column(columnDefinition = "TEXT")
    private String antecedents;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "traitements_en_cours", columnDefinition = "TEXT")
    private String traitementsEnCours;

    @Column(name = "en_attente", nullable = false)
    private boolean enAttente = true;

    @Column(name = "date_enregistrement", nullable = false)
    private LocalDateTime dateEnregistrement = LocalDateTime.now();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("datePrise DESC")
    private List<SigneVital> signesVitaux = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    private List<Consultation> consultations = new ArrayList<>();

    public Patient() {}

    public Patient(String nom, String prenom, LocalDate dateNaissance, String numSecuriteSociale,
                   String telephone, String adresse, String mutuelle) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.numSecuriteSociale = numSecuriteSociale;
        this.telephone = telephone;
        this.adresse = adresse;
        this.mutuelle = mutuelle;
        this.enAttente = true;
        this.dateEnregistrement = LocalDateTime.now();
    }

    public SigneVital getDernierSigneVital() {
        if (signesVitaux == null || signesVitaux.isEmpty()) {
            return null;
        }
        return signesVitaux.get(0);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getNumSecuriteSociale() { return numSecuriteSociale; }
    public void setNumSecuriteSociale(String numSecuriteSociale) { this.numSecuriteSociale = numSecuriteSociale; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getMutuelle() { return mutuelle; }
    public void setMutuelle(String mutuelle) { this.mutuelle = mutuelle; }

    public String getAntecedents() { return antecedents; }
    public void setAntecedents(String antecedents) { this.antecedents = antecedents; }

    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }

    public String getTraitementsEnCours() { return traitementsEnCours; }
    public void setTraitementsEnCours(String traitementsEnCours) { this.traitementsEnCours = traitementsEnCours; }

    public boolean isEnAttente() { return enAttente; }
    public void setEnAttente(boolean enAttente) { this.enAttente = enAttente; }

    public LocalDateTime getDateEnregistrement() { return dateEnregistrement; }
    public void setDateEnregistrement(LocalDateTime dateEnregistrement) { this.dateEnregistrement = dateEnregistrement; }

    public List<SigneVital> getSignesVitaux() { return signesVitaux; }
    public void setSignesVitaux(List<SigneVital> signesVitaux) { this.signesVitaux = signesVitaux; }

    public List<Consultation> getConsultations() { return consultations; }
    public void setConsultations(List<Consultation> consultations) { this.consultations = consultations; }
}