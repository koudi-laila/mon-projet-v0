/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.iga.project.service;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import ma.iga.project.bean.Personne;
import ma.iga.project.service.util.HashUtil;

/**
 *
 * @author asus
 */
@Stateless
public class PersonneFacade extends AbstractFacade<Personne> {

    @PersistenceContext(unitName = "ma.iga_project_war_1.0-SNAPSHOTPU")
    private EntityManager em;
     
    

    public List<Personne> search(Personne personne,  Date dateEmbaucheMin,Date dateEmbaucheMax,Date dateNaissanceMin,Date dateNaissanceMax) {
        String query = initQuery();
        query+=addConstraintMinMaxDate("dateEmbauche", dateEmbaucheMin, dateEmbaucheMax);
        query+=addConstraintMinMaxDate("dateNaissance", dateNaissanceMin, dateNaissanceMax);
        if (personne != null) {
            query += addConstraintLike("matricule", personne.getMatricule());
            query += addConstraintLike("cin", personne.getCin());
            query += addConstraintLike("prenom", personne.getPrenom());
            query += addConstraintLike("adresse", personne.getAdresse());
            query += addConstraintLike("tel", personne.getTel());
            query += addConstraintLike("numeroMutuel", personne.getNumeroMutuel());
            query += addConstraintLike("nomConjoint", personne.getNomConjoint());
            query += addConstraintLike("numeroCnss", personne.getNumeroCnss());
            if (personne.getSituationFamilliale() != null) {
                query += addConstraint("situationFamilliale.libelle", personne.getSituationFamilliale().getLibelle());
            }
            if (personne.getGroupeTravail() != null) {
                query += addConstraint("groupeTravail.libelle", personne.getProfession().getLibelle());
            }
            if (personne.getSectionTravail() != null) {
                query += addConstraint("sectionTravail.libelle", personne.getSectionTravail().getLibelle());
            }
            if (personne.getProfession() != null) {
                query += addConstraint("profession.libelle", personne.getProfession().getLibelle());
            }
        }

        System.out.println("query = " + query);
        return em.createQuery(query).getResultList();
    }

    public Personne searchBymatricule(String matricule) {
        Personne personne = findOne("matricule", matricule);
        return personne;
    }

    @Override
    public void create(Personne personne) {
        personne.setPassword(HashUtil.hashSHA(personne.getPassword()));
        super.create(personne);
    }

    public int seConnecter(String matricule, String password) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        ExternalContext ectx = context.getExternalContext();
        System.out.println("" + request);
        Personne personne = findOne("matricule", matricule);
        System.out.println("ani tani");
        if (personne == null) {
            return -1;
        } else if (!personne.getPassword().equals(HashUtil.hashSHA(password))) {
            return -2;
        } else {
            System.out.println("");
            return 1;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonneFacade() {
        super(Personne.class);
    }

}
