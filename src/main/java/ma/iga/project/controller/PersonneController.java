package ma.iga.project.controller;

import ma.iga.project.bean.Personne;
import ma.iga.project.controller.util.JsfUtil;
import ma.iga.project.controller.util.JsfUtil.PersistAction;
import ma.iga.project.service.PersonneFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import ma.iga.project.bean.Absence;
import ma.iga.project.bean.Retard;

@Named("personneController")
@SessionScoped
public class PersonneController implements Serializable {

    @EJB
    private ma.iga.project.service.PersonneFacade ejbFacade;
    @EJB
    private ma.iga.project.service.AbsenceFacade ejbAbsence;
    @EJB
    private ma.iga.project.service.RetardFacade ejbRetard;
    
    private List<Absence>absences=new ArrayList<>();
     private List<Retard>retards=new ArrayList<>();
     private List<Personne>personnes=new ArrayList<>();
    
    private List<Personne> items = null;
    private Personne selected;
   
    private Personne personneSearch = new Personne();
    private Date dateEmbaucheMin;
    private Date dateEmbaucheMax;
    private Date dateNaissanceMin;
    private Date dateNaissanceMax;
    private  int cp;
  

    public void search(){
        items=ejbFacade.search(personneSearch,dateEmbaucheMax,dateEmbaucheMin,dateNaissanceMax,dateNaissanceMin);
    }
    
    public void findByPersonneMatricule(String matricule){
        absences=ejbAbsence.findByPersonneMatricule(matricule);
        retards=ejbRetard.findByPersonneMatricule(matricule);
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

    
    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public Date getDateEmbaucheMin() {
        return dateEmbaucheMin;
    }

    public void setDateEmbaucheMin(Date dateEmbaucheMin) {
        this.dateEmbaucheMin = dateEmbaucheMin;
    }

    public Date getDateEmbaucheMax() {
        return dateEmbaucheMax;
    }

    public void setDateEmbaucheMax(Date dateEmbaucheMax) {
        this.dateEmbaucheMax = dateEmbaucheMax;
    }

    public Date getDateNaissanceMin() {
        return dateNaissanceMin;
    }

    public void setDateNaissanceMin(Date dateNaissanceMin) {
        this.dateNaissanceMin = dateNaissanceMin;
    }

    public Date getDateNaissanceMax() {
        return dateNaissanceMax;
    }

    public void setDateNaissanceMax(Date dateNaissanceMax) {
        this.dateNaissanceMax = dateNaissanceMax;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    public List<Retard> getRetards() {
        return retards;
    }

    public void setRetards(List<Retard> retards) {
        this.retards = retards;
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
