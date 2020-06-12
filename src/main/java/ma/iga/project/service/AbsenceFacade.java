/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.iga.project.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ma.iga.project.bean.Absence;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author asus
 */
@Stateless
public class AbsenceFacade extends AbstractFacade<Absence> {

    @PersistenceContext(unitName = "ma.iga_project_war_1.0-SNAPSHOTPU")
    private EntityManager em;

//     public void generatePdf() throws JRException, IOException{
//        Map<String,Object> params= new HashMap();
//        params.put("dtd", "01/12/2019");
//        params.put("mat", "1");
//        params.put("dtf","31/12/2020" );
//        PdfUtil.generatePdf(findAll(), params, "Fiche absences", "/jasper/FicheAbsence.jasper");
//    }
     
    public List<Absence> search(Absence absence,Date dateDebut,Date dateFin,
            String description) {
        String query = initQuery();
        query += addConstraintLike("description", absence.getDescription());
        if (absence.getPersonne() != null) {
            query += addConstraint("personne.matricule", absence.getPersonne().getMatricule());
        }
        if (absence.getMotifAbsence()!= null) {
            query += addConstraint("motifAbsence.libelle", absence.getMotifAbsence().getLibelle());
        }
        if (absence.getTypeAbsence()!= null) {
            query += addConstraint("typeAbsence.libelle", absence.getTypeAbsence().getLibelle());
        }
        System.out.println("query = " + query);
        return em.createQuery(query).getResultList();
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AbsenceFacade() {
        super(Absence.class);
    }
    
}
