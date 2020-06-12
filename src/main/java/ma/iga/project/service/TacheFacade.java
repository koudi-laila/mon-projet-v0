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
import ma.iga.project.bean.Personne;
import ma.iga.project.bean.Tache;

/**
 *
 * @author asus
 */
@Stateless
public class TacheFacade extends AbstractFacade<Tache> {

    @PersistenceContext(unitName = "ma.iga_project_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public List<Tache> search(Tache tache, String note,Date dateDebutEstime,
    Date dateDebutReel,Date dateFinEstime,Date dateFintReel,Personne personne) {
        String query = initQuery();
        query += addConstraintLike("note",tache.getNote() );
        if (tache.getPersonne() != null) {
            query += addConstraint("personne.matricule", tache.getPersonne().getMatricule());
        }
        System.out.println("query = " + query);
        return em.createQuery(query).getResultList();
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TacheFacade() {
        super(Tache.class);
    }
    
}
