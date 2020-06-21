/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.iga.project.service;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ma.iga.project.bean.Absence;
import ma.iga.project.vo.AbsenceChefVo;

/**
 *
 * @author asus
 */
@Stateless
public class AbsenceFacade extends AbstractFacade<Absence> {

    @PersistenceContext(unitName = "ma.iga_project_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public List<Absence>findByPersonneMatricule(String matricule){
    List<Absence>absences=em.createQuery("SELECT * FROM absence a WHERE a.personne.matricule='"+matricule+"'").getResultList();
    return absences;
    }
    

//     public void generatePdf() throws JRException, IOException{
//        Map<String,Object> params= new HashMap();
//        params.put("matricule", "01/12/2019");
//        params.put("dateDebut", "1");
//        params.put("dateFin","31/12/2020" );
//         PdfUtil.generatePdf(findAll(), params, "Fiche absences", "/ma/iga/project/jasper/ficheAbsence.jasper");
//    }
    public List<AbsenceChefVo> findAbsenceGroupeByTypeAbsence() {
        String requette = "SELECT new ma.iga.project.vo.AbsenceChefVo(a.personne,a.typeAbsence,COUNT(a.id)) "
                + "FROM Absence a GROUP BY a.typeAbsence ";
        requette += "";
        List<AbsenceChefVo> lst = em.createQuery(requette).getResultList();
        return lst;
    }

    public List<AbsenceChefVo> findAbsenceGroupeBySectionTravail() {
        Query q = em.createQuery("SELECT new ma.iga.project.vo.AbsenceChefVo(a.personne ,a.typeAbsence ,COUNT(a.id)) "
                + "FROM Absence a GROUP BY a.personne.sectionTravail.libelle");

        List<AbsenceChefVo> lst = q.getResultList();
        return lst;
    }

    public List<Absence> search(Absence absence, Date dateDebut) {
        String query = initQuery();
        query += addConstraintDate("dateDebut", dateDebut);

        query += addConstraintLike("description", absence.getDescription());
        if (absence.getPersonne() != null) {
            query += addConstraint("personne.matricule", absence.getPersonne().getMatricule());
        }
        if (absence.getMotifAbsence() != null) {
            query += addConstraint("motifAbsence.libelle", absence.getMotifAbsence().getLibelle());
        }
        if (absence.getTypeAbsence() != null) {
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
