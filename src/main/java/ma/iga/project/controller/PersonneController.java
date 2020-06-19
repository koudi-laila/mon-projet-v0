package ma.iga.project.controller;

import ma.iga.project.bean.Personne;
import ma.iga.project.controller.util.JsfUtil;
import ma.iga.project.controller.util.JsfUtil.PersistAction;
import ma.iga.project.service.PersonneFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("personneController")
@SessionScoped
public class PersonneController implements Serializable {

    @EJB
    private ma.iga.project.service.PersonneFacade ejbFacade;
    private List<Personne> items = null;
    private Personne selected;
   
    private Personne personneSearch = new Personne();
    private String matricule;
    private String cin;
    private String nom;
    private String prenom;
    private String adresse;
    private String tel;
    private String numeroMutuel;
    private String numeroCnss;
    private String nomConjoint;
    private String password;
    private  int cp;
  

    public void search() {
        items=ejbFacade.search(personneSearch, matricule, cin, nom, prenom, null, null, adresse, tel, numeroMutuel, numeroCnss, nomConjoint);
    }
    
    public void authenticated(Personne p) {
        System.out.println("pffffffffffffffffff");
        cp=ejbFacade.seConnecter(p.getMatricule(),p.getPassword());
        
       
    }
    public PersonneController() {
    }

    
    public Personne getSelected() {
         if (selected == null) {
            selected = new Personne();
        }
        return selected;
    }

    public void setSelected(Personne selected) {
        this.selected = selected;
    }

    public Personne getPersonneSearch() {
        return personneSearch;
    }

    public void setPersonneSearch(Personne personneSearch) {
        this.personneSearch = personneSearch;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
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

    public String getNomConjoint() {
        return nomConjoint;
    }

    public void setNomConjoint(String nomConjoint) {
        this.nomConjoint = nomConjoint;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    
    private PersonneFacade getFacade() {
        return ejbFacade;
    }

    public Personne prepareCreate() {
        selected = new Personne();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PersonneCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonneUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PersonneDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Personne> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.CREATE) {
                    getFacade().create(selected);
                }else  if (persistAction == PersistAction.UPDATE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Personne getPersonne(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Personne> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Personne> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Personne.class)
    public static class PersonneControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PersonneController controller = (PersonneController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "personneController");
            return controller.getPersonne(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Personne) {
                Personne o = (Personne) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Personne.class.getName()});
                return null;
            }
        }

    }

}
