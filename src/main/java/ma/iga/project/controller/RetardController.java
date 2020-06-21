package ma.iga.project.controller;

import ma.iga.project.bean.Retard;
import ma.iga.project.controller.util.JsfUtil;
import ma.iga.project.controller.util.JsfUtil.PersistAction;
import ma.iga.project.service.RetardFacade;

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

@Named("retardController")
@SessionScoped
public class RetardController implements Serializable {

    @EJB
    private ma.iga.project.service.RetardFacade ejbFacade;

    private List<Retard> items = null;
    private Retard selected;
    private Retard retardSearch = new Retard();
    private Float nombreHeureMin;
    private Float nombreHeureMax;
    private Date dateDebut;

    public void search() {
        items = ejbFacade.search(retardSearch, null, null, dateDebut);
    }

    public RetardController() {
    }

    public Retard getRetardSearch() {
        return retardSearch;
    }

    public void setRetardSearch(Retard retardSearch) {
        this.retardSearch = retardSearch;
    }

    public Retard getSelected() {
        if (selected == null) {
            selected = new Retard();
        }
        return selected;
    }

    public void setSelected(Retard selected) {
        this.selected = selected;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RetardFacade getFacade() {
        return ejbFacade;
    }

    public Retard prepareCreate() {
        selected = new Retard();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RetardCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RetardUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RetardDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Retard> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public RetardFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(RetardFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Float getNombreHeureMin() {
        return nombreHeureMin;
    }

    public void setNombreHeureMin(Float nombreHeureMin) {
        this.nombreHeureMin = nombreHeureMin;
    }

    public Float getNombreHeureMax() {
        return nombreHeureMax;
    }

    public void setNombreHeureMax(Float nombreHeureMax) {
        this.nombreHeureMax = nombreHeureMax;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
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

    public Retard getRetard(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Retard> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Retard> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Retard.class)
    public static class RetardControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RetardController controller = (RetardController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "retardController");
            return controller.getRetard(getKey(value));
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
            if (object instanceof Retard) {
                Retard o = (Retard) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Retard.class.getName()});
                return null;
            }
        }

    }

}
