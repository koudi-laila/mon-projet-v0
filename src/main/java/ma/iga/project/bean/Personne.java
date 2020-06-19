/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.iga.project.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author asus
 */
@Entity
public class Personne implements Serializable {

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String matricule;
    private String cin;
    private String nom;
    private String prenom;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    @Temporal(TemporalType.DATE)
    private Date dateEmbauche;
    private String adresse;
    private String tel;
    private String numeroMutuel;
    private String numeroCnss;
    @ManyToOne
    private SituationFamilliale situationFamilliale;
    private String nomConjoint;
    private int nbrEnfant;
    @ManyToOne
    private GroupeTravail groupeTravail;
    @ManyToOne
    private SectionTravail sectionTravail;
    @ManyToOne
    private Profession profession;
    @OneToMany(mappedBy = "personne")
    private List<Absence> absences;
    @OneToMany(mappedBy = "personne")
    private List<Retard> retards;
    @OneToMany(mappedBy = "personne")
    private List<Tache> taches;
    
    private String password;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Date getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(Date dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNumeroMutuel() {
        return numeroMutuel;
    }

    public void setNumeroMutuel(String numeroMutuel) {
        this.numeroMutuel = numeroMutuel;
    }

    public String getNumeroCnss() {
        return numeroCnss;
    }

    public void setNumeroCnss(String numeroCnss) {
        this.numeroCnss = numeroCnss;
    }

    public SituationFamilliale getSituationFamilliale() {
        return situationFamilliale;
    }

    public void setSituationFamilliale(SituationFamilliale situationFamilliale) {
        this.situationFamilliale = situationFamilliale;
    }

    public String getNomConjoint() {
        return nomConjoint;
    }

    public void setNomConjoint(String nomConjoint) {
        this.nomConjoint = nomConjoint;
    }

    public int getNbrEnfant() {
        return nbrEnfant;
    }

    public void setNbrEnfant(int nbrEnfant) {
        this.nbrEnfant = nbrEnfant;
    }

    public GroupeTravail getGroupeTravail() {
        return groupeTravail;
    }

    public void setGroupeTravail(GroupeTravail groupeTravail) {
        this.groupeTravail = groupeTravail;
    }

    public SectionTravail getSectionTravail() {
        return sectionTravail;
    }

    public void setSectionTravail(SectionTravail sectionTravail) {
        this.sectionTravail = sectionTravail;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public List<Retard> getRetards() {
        return retards;
    }

    public void setRetards(List<Retard> retards) {
        this.retards = retards;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personne)) {
            return false;
        }
        Personne other = (Personne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return matricule;
    }
    

}
