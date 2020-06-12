/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.iga.project.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
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

    public List<Personne> search(Personne personne, String matricule, String cin,
            String nom, String prenom, Date dateNaissance, Date dateEmbauche, String adresse, String tel,
            String numeroMutuel, String numeroCnss,
            String nomConjoint) {
        String query = initQuery();
        query += addConstraintLike("matricule", personne.getMatricule());
        query += addConstraintLike("personne.cin", personne.getCin());
        query += addConstraintLike("personne.prenom", personne.getPrenom());
        query += addConstraintLike("personne.adresse", personne.getAdresse());
        query += addConstraintLike("personne.tel", personne.getTel());
        query += addConstraintLike("personne.numeroMutuel", personne.getNumeroMutuel());
        query += addConstraintLike("personne.nomConjoint", personne.getNomConjoint());
        query += addConstraintLike("personne.numeroCnss", personne.getNumeroCnss());

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
        System.out.println("query = " + query);
        return em.createQuery(query).getResultList();
    }
       public Personne searchBymatricule( String matricule) {
         Personne personne = findOne("matricule", matricule);
         return personne;
    }
    @Override
    public void create(Personne personne) {
        personne.setPassword(HashUtil.hashSHA(personne.getPassword()));
        super.create(personne);
    }

    public int seConnecter(String matricule, String password) {
        System.out.println("ani");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        ExternalContext ectx = context.getExternalContext();
        System.out.println(""+request);
        Personne personne = findOne("matricule", matricule);
        System.out.println("ani tani");
        if (personne == null) {
            return -1;
        } else if (!personne.getPassword().equals(HashUtil.hashSHA(password))) {
            return -2;
        } else {
            System.out.println("mrhba");
             try {
                request.login(matricule, HashUtil.hashSHA(password));
                
            } catch (ServletException ex) {
                Logger.getLogger(PersonneFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
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
