package com.ediagnostic.model;

import com.ediagnostic.model.enums.PrioriteExpertise;
import com.ediagnostic.model.enums.StatutExpertise;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "demandes_tele_expertise")
public class DemandeTeleExpertise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_demande", nullable = false)
    private LocalDateTime dateDemande = LocalDateTime.now();

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(name = "donnees_analyses", columnDefinition = "TEXT")
    private String donneesAnalyses;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PrioriteExpertise priorite = PrioriteExpertise.NORMALE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutExpertise statut = StatutExpertise.EN_ATTENTE;

    @Column(name = "avis_expert", columnDefinition = "TEXT")
    private String avisExpert;

    @Column(columnDefinition = "TEXT")
    private String recommandations;

    @Column(name = "date_reponse")
    private LocalDateTime dateReponse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialiste_id", nullable = false)
    private MedecinSpecialiste specialiste;

    @OneToOne
    @JoinColumn(name = "creneau_id", nullable = false)
    private Creneau creneau;

    public DemandeTeleExpertise() {
    }

    public DemandeTeleExpertise(String question, String donneesAnalyses, PrioriteExpertise priorite,
            MedecinSpecialiste specialiste, Creneau creneau) {
        this.question = question;
        this.donneesAnalyses = donneesAnalyses;
        this.priorite = priorite;
        this.specialiste = specialiste;
        this.creneau = creneau;
        this.statut = StatutExpertise.EN_ATTENTE;
        this.dateDemande = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDonneesAnalyses() {
        return donneesAnalyses;
    }

    public void setDonneesAnalyses(String donneesAnalyses) {
        this.donneesAnalyses = donneesAnalyses;
    }

    public PrioriteExpertise getPriorite() {
        return priorite;
    }

    public void setPriorite(PrioriteExpertise priorite) {
        this.priorite = priorite;
    }

    public StatutExpertise getStatut() {
        return statut;
    }

    public void setStatut(StatutExpertise statut) {
        this.statut = statut;
    }

    public String getAvisExpert() {
        return avisExpert;
    }

    public void setAvisExpert(String avisExpert) {
        this.avisExpert = avisExpert;
    }

    public String getRecommandations() {
        return recommandations;
    }

    public void setRecommandations(String recommandations) {
        this.recommandations = recommandations;
    }

    public LocalDateTime getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(LocalDateTime dateReponse) {
        this.dateReponse = dateReponse;
    }

    public MedecinSpecialiste getSpecialiste() {
        return specialiste;
    }

    public void setSpecialiste(MedecinSpecialiste specialiste) {
        this.specialiste = specialiste;
    }

    public Creneau getCreneau() {
        return creneau;
    }

    public void setCreneau(Creneau creneau) {
        this.creneau = creneau;
    }
}