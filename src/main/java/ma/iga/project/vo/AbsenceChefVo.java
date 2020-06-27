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
    
    public AbsenceChefVo() {
    }

    public AbsenceChefVo(Personne chef, TypeAbsence typeAbsence, Long nbrOccurence, Date dateDebut) {
        this.chef = chef;
        this.typeAbsence = typeAbsence;
        this.nbrOccurence = nbrOccurence;
        this.dateDebut = dateDebut;
    }
    
    

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
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

     
    @Override
    public String toString() {
        return "AbsenceChefVo{" + "chef=" + chef.getMatricule() + ", typeAbsence=" + typeAbsence.getLibelle()+ ", nbrOccurence=" + nbrOccurence +",Date "+ dateDebut .toString()+'}';
    }
    
    
    
}
