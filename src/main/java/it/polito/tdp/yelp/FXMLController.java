/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Adiacenza;
import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnDistante"
    private Button btnDistante; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcolaPercorso"
    private Button btnCalcolaPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCitta"
    private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

    @FXML // fx:id="cmbB1"
    private ComboBox<Business> cmbB1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbB2"
    private ComboBox<?> cmbB2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String citta=this.cmbCitta.getValue();
    	if(citta==null) {
    		this.txtResult.appendText("Selezionare una citta ");
    		return;
    	}
this.model.creaGrafo(citta);
    	
    	txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("# Vertici : " + this.model.nVertici() + "\n");
    	txtResult.appendText("# Archi : " + this.model.nArchi() + "\n");
    	this.cmbB1.getItems().clear();
    	this.cmbB1.getItems().addAll(this.model.getVertici());

    }

    @FXML
    void doCalcolaLocaleDistante(ActionEvent event) {
    	if(!model.grafoCreato())
    	{
    		this.txtResult.appendText("Creare prima il grafo!");
    		return;
    	}
    	Business b=this.cmbB1.getValue();
    	if(b==null)
    	{
    		this.txtResult.appendText("selezionare un locale dalla tendina ");
    		return;
    	}
    	
    	Adiacenza a=model.getAdiacenti(b);
    	this.txtResult.appendText(a.toString());
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {

    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDistante != null : "fx:id=\"btnDistante\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbB1 != null : "fx:id=\"cmbB1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbB2 != null : "fx:id=\"cmbB2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbCitta.getItems().addAll(this.model.citta());
    }
}
