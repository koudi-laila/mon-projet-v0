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
import ma.iga.project.bean.Retard;

/**
 *
 * @author asus
 */
@Stateless
public class RetardFacade extends AbstractFacade<Retard> {

    @PersistenceContext(unitName = "ma.iga_project_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public List<Retard>findByPersonneMatricule(String matricule){
    List<Retard>retards=em.createQuery("SELECT * FROM retard r WHERE r.personne.matricule='"+matricule+"'").getResultList();
    return retards;
    }
    
    
    public List<Retard> search(Retard retard, Float nombreHeureMin, Float nombreHeureMax,
            Date dateRetard) {
        String query = initQuery();
         query+=addConstraintDate("dateRetard", dateRetard);
        query += addConstraintLike("description", retard.getDescription());
        if (retard.getPersonne() != null) {
            query += addConstraint("personne.matricule", retard.getPersonne().getMatricule());
        }
        if (retard.getMotifRetard() != null) {
            query += addConstraint("motifRetard.libelle", retard.getMotifRetard().getLibelle());
        }
        System.out.println("query = " + query);
        return em.createQuery(query).getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RetardFacade() {
        super(Retard.class);
    }

}
