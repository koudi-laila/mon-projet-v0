/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.iga.project.vo;

import java.util.Date;
import ma.iga.project.bean.Personne;
import ma.iga.project.bean.TypeAbsence;

/**
 *
 * @author asus
 */
public class AbsenceChefVo {
    private Personne chef;
    private TypeAbsence typeAbsence;
    private Long nbrOccurence;
    private Date dateDebut;
    private Date dateFin;

    
    public AbsenceChefVo() {
    }

    public AbsenceChefVo(Personne chef, TypeAbsence typeAbsence, Long nbrOccurence) {
        this.chef = chef;
        this.typeAbsence = typeAbsence;
        this.nbrOccurence = nbrOccurence;
    }

    public AbsenceChefVo(Personne chef, TypeAbsence typeAbsence, Long nbrOccurence, Date dateDebut, Date dateFin) {
        this.chef = chef;
        this.typeAbsence = typeAbsence;
        this.nbrOccurence = nbrOccurence;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Personne getChef() {
        return chef;
    }

    public void setChef(Personne chef) {
        this.chef = chef;
    }

    public Long getNbrOccurence() {
        return nbrOccurence;
    }

    public void setNbrOccurence(Long nbrOccurence) {
        this.nbrOccurence = nbrOccurence;
    }

    public TypeAbsence getTypeAbsence() {
        return typeAbsence;
    }

    public void setTypeAbsence(TypeAbsence typeAbsence) {
        this.typeAbsence = typeAbsence;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    
    @Override
    public String toString() {
        return "AbsenceChefVo{" + "chef=" + chef.getMatricule() + ", motifAbsence=" + typeAbsence.getLibelle() + ", nbrOccurence=" + nbrOccurence + '}';
    }
    
    
    
}
