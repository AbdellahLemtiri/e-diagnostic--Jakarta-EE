package com.ediagnostic.model;

import com.ediagnostic.model.enums.StatutConsultation;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "consultations")
public class Consultation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_consultation", nullable = false)
    private LocalDateTime dateConsultation = LocalDateTime.now();

    @Column(nullable = false)
    private String motif;

    @Column(name = "examen_clinique", columnDefinition = "TEXT")
    private String examenClinique;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(columnDefinition = "TEXT")
    private String diagnostic;

    @Column(columnDefinition = "TEXT")
    private String ordonnance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 35)
    private StatutConsultation statut = StatutConsultation.EN_COURS;

    @Column(name = "cout_base", nullable = false)
    private Double coutBase = 150.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generaliste_id", nullable = false)
    private MedecinGeneraliste generaliste;

    @ManyToMany
    @JoinTable(
        name = "consultation_actes",
        joinColumns = @JoinColumn(name = "consultation_id"),
        inverseJoinColumns = @JoinColumn(name = "acte_id")
    )
    private List<ActeTechnique> actesTechniques = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_tele_expertise_id")
    private DemandeTeleExpertise demandeTeleExpertise;

    public Consultation() {}

    public Consultation(String motif, Patient patient, MedecinGeneraliste generaliste) {
        this.motif = motif;
        this.patient = patient;
        this.generaliste = generaliste;
        this.coutBase = 150.0;
        this.statut = StatutConsultation.EN_COURS;
        this.dateConsultation = LocalDateTime.now();
    }

    public Double calculerCoutTotal() {
        double totalActes = (actesTechniques == null) ? 0.0 :
            actesTechniques.stream()
                           .mapToDouble(ActeTechnique::getTarif)
                           .sum();

        double tarifExpertise = 0.0;
        if (demandeTeleExpertise != null && demandeTeleExpertise.getSpecialiste() != null) {
            tarifExpertise = demandeTeleExpertise.getSpecialiste().getTarifExpertise();
        }

        return this.coutBase + totalActes + tarifExpertise;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateConsultation() { return dateConsultation; }
    public void setDateConsultation(LocalDateTime dateConsultation) { this.dateConsultation = dateConsultation; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public String getExamenClinique() { return examenClinique; }
    public void setExamenClinique(String examenClinique) { this.examenClinique = examenClinique; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }

    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }

    public String getOrdonnance() { return ordonnance; }
    public void setOrdonnance(String ordonnance) { this.ordonnance = ordonnance; }

    public StatutConsultation getStatut() { return statut; }
    public void setStatut(StatutConsultation statut) { this.statut = statut; }

    public Double getCoutBase() { return coutBase; }
    public void setCoutBase(Double coutBase) { this.coutBase = coutBase; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public MedecinGeneraliste getGeneraliste() { return generaliste; }
    public void setGeneraliste(MedecinGeneraliste generaliste) { this.generaliste = generaliste; }

    public List<ActeTechnique> getActesTechniques() { return actesTechniques; }
    public void setActesTechniques(List<ActeTechnique> actesTechniques) { this.actesTechniques = actesTechniques; }

    public DemandeTeleExpertise getDemandeTeleExpertise() { return demandeTeleExpertise; }
    public void setDemandeTeleExpertise(DemandeTeleExpertise demandeTeleExpertise) { this.demandeTeleExpertise = demandeTeleExpertise; }
}