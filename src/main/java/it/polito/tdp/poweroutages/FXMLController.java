/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
    @FXML
    void doRun(ActionEvent event) {
    	txtResult.clear();
    	if(cmbNerc.getValue()==null) {
    		this.txtResult.setText("Inserire un Nerc per piacere");
    		return;
    	}
    	Nerc n=this.cmbNerc.getValue();
    	
    	String hours=this.txtHours.getText();
    	String years=this.txtYears.getText();
    	
    	if(years.isEmpty()) {
    		this.txtResult.setText("Inserire anni max per piacere");
    		return;
    	}
    	
    	int x;
    	try {
    		 x=Integer.parseInt(years);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Anni non corrispondono a un numero");
    		return;
    	}
    	
    	if(hours.isEmpty()) {
    		this.txtResult.setText("Inserire ore max per piacere");
    		return;
    	}
    	int y;
    	try {
    		 y=Integer.parseInt(hours);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Ore non corrispondono a un numero");
    		return;
    	}
    	
    	
    	List<PowerOutage> soluzione=this.model.calcolaWorstCase(n, x, y);
    	
    	if(soluzione==null) {
    		this.txtResult.setText("Problemi nel DB");
    	}
    	
    	StringBuilder sb=new StringBuilder();
    	sb.append(String.format("%-20s ", "Tot people affected: "));
    	sb.append(String.format("%5d", this.model.calcolaTotalePersone(soluzione)));
    	sb.append("\n");
    	sb.append(String.format("%-20s ", "Tot hours of outage: "));
    	sb.append(String.format("%5d", this.model.calcolaTempoTotale(soluzione)));
    	sb.append("\n");
    	
    	
    	for(PowerOutage p: soluzione) {
    		sb.append(String.format("%-4d ", p.getInizio().getYear()));
    		sb.append(String.format("%10s ", p.getInizio().toString()));
    		sb.append(String.format("%10s ", p.getFine().toString()));
    		sb.append(String.format("%4d ", (int)(p.getInizio().until(p.getFine(), ChronoUnit.HOURS))));
    		sb.append(String.format("%9d ", p.getCustomers_affected()));
    		sb.append("\n");
    	}
    	this.txtResult.appendText(sb.toString());
    	
    }
    
   

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<Nerc> lista=this.model.getNercList();
    	cmbNerc.getItems().addAll(lista);
    }
}
