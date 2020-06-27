package ma.iga.project.controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import ma.iga.project.bean.Absence;
import ma.iga.project.service.AbsenceFacade;

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
import ma.iga.project.controller.util.JsfUtil.PersistAction;
import ma.iga.project.vo.AbsenceChefVo;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.pie.PieChartOptions;

@Named("absenceController")
@SessionScoped
public class AbsenceController implements Serializable {

    @EJB
    private AbsenceFacade ejbFacade;
    private List<Absence> items = null;
    private Absence selected;
    private Absence searchabsence;
    private BarChartModel barModel ;
     private PieChartModel pieModel;
    private List<AbsenceChefVo> abs;
    private List<AbsenceChefVo> abs1;
    private Date dateDebut;
    public AbsenceController() {
        
    }
    public void createBarModel() {
        ChartData data = new ChartData();

       barModel = new BarChartModel();
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Nombre d absence par motif");
         abs=ejbFacade.findAbsenceGroupeByTypeAbsence(dateDebut);
         System.out.println("bien "+abs);
        List<Number> values = new ArrayList<>();
        for (int i=0;i<abs.size();i++) {
            values.add(abs.get(i).getNbrOccurence());
        }
        barDataSet.setData(values);


        data.addChartDataSet(barDataSet);

       List<String> labels = new ArrayList<>();
        for (int i=0;i<abs.size();i++) {
            labels.add(abs.get(i).getTypeAbsence().toString());
        }
        data.setLabels(labels);
        //Data
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Nombre d'absence par type absence");
        title.setFontColor("#2980B9");
        title.setFontStyle("bold");
         title.setFontSize(24);
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("right");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(12);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
        barModel.setOptions(options);
    }
     public void createPieModel(){
         
        pieModel = new PieChartModel();
        ChartData data = new ChartData();
        abs1 = ejbFacade.findAbsenceGroupeBySectionTravail(dateDebut);
       System.out.println("ani"+abs1);
        
         PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values1 = new ArrayList<>();
         for (int i=0;i<abs1.size();i++) {
            values1.add(abs1.get(i).getNbrOccurence());
        }
        dataSet.setData(values1);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(75, 192, 192)");
        bgColors.add("rgb(201, 205, 86)");
        bgColors.add("rgb(255, 203, 207)");
        bgColors.add("rgb(75, 162, 235)");
        dataSet.setBackgroundColor(bgColors);
        data.addChartDataSet(dataSet);
        
        List<String> labels1 = new ArrayList<>();
        for (int i=0;i<abs1.size();i++) {
            labels1.add(abs1.get(i).getChef().getSectionTravail().getLibelle());
        }
        data.setLabels(labels1);
        pieModel.setData(data);
     
     }

    public BarChartModel getBarModel() {
      if(barModel==null)
        createBarModel();
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public PieChartModel getPieModel() {
         if(pieModel==null){
        createPieModel();}
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }
    
    public void search() {
        items = ejbFacade.search(searchabsence, dateDebut);
      
    }
//     public void searchBarGraphique() {
//       abs= ejbFacade.findAbsenceGroupeByTypeAbsence(dateDebut);
//      
//    }

    //public void generatePDF() throws JRException, IOException {
    //      ejbFacade.generatePdf();
    //        FacesContext.getCurrentInstance().responseComplete();
    //        
    //    }
    public List<AbsenceChefVo> getAbs() {
        return abs;
    }

    public void setAbs(List<AbsenceChefVo> abs) {
        this.abs = abs;
    }

    public List<AbsenceChefVo> getAbs1() {
         if(abs1==null)
            abs1=ejbFacade.findAbsenceGroupeBySectionTravail(dateDebut);
        return abs1;
    }

    public void setAbs1(List<AbsenceChefVo> abs1) {
        this.abs1 = abs1;
    }

   

    public Absence getSearchabsence() {
        if(searchabsence==null)
            searchabsence=new Absence();
        return searchabsence;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    
    public Absence getSelected() {
        if (selected == null) {
            selected = new Absence();
        }
        return selected;
    }

    public void setSelected(Absence selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AbsenceFacade getFacade() {
        return ejbFacade;
    }

    public Absence prepareCreate() {
        selected = new Absence();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AbsenceCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AbsenceUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AbsenceDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Absence> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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

    public Absence getAbsence(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Absence> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Absence> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    private List<AbsenceChefVo> searchabsenceGraphe() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FacesConverter(forClass = Absence.class)
    public static class AbsenceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AbsenceController controller = (AbsenceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "absenceController");
            return controller.getAbsence(getKey(value));
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
            if (object instanceof Absence) {
                Absence o = (Absence) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Absence.class.getName()});
                return null;
            }
        }

    }

}
