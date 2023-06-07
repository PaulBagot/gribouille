package uit.gon.gribouille.controleurs;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import uit.gon.gribouille.Dialogues;

public class MenusControleur implements Initializable{

	@FXML
	public ToggleGroup epaisseurGroupe;
	@FXML
	public ToggleGroup formeGroupe;
	@FXML
	public RadioMenuItem crayon;
	@FXML
	public RadioMenuItem etoile;
	
	private Controleur controller;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		formeGroupe.selectedToggleProperty().addListener(l -> {
			if(controller.outil instanceof OutilCrayon) {
				controller.onCrayon();
			} else {
				controller.onEtoile();;
			}
		});
		
		epaisseurGroupe.selectedToggleProperty().addListener(e -> {
			RadioMenuItem epaisseurToggle = (RadioMenuItem) epaisseurGroupe.getSelectedToggle();
			controller.setEpaisseur(Integer.parseInt(epaisseurToggle.getText()));
		});
	}

	@FXML
	public void onQuitte(ActionEvent evt) {
		if(controller.onQuitter()) {
			Platform.exit();
		}
		evt.consume();
	}
	
	@FXML
	public void onSauver(ActionEvent evt) {
		controller.sauvegarde(controller.dessinsController.canvas.getScene());
	}
	
	@FXML
	public void onCharger(ActionEvent evt) {
		controller.charge(controller.dessinsController.canvas.getScene());
	}
	
	@FXML
	public void onEffacer(ActionEvent evt) {
		controller.effacer();
	}
	
	@FXML
	public void onRetourArriere(ActionEvent evt) {
		controller.retourArriere();
	}
	
	@FXML
	public void onExporter(ActionEvent evt) {
		controller.exporter(controller.dessinsController.canvas.getScene());
	}
	
	public void setController(Controleur controller) {
		this.controller = controller;
	}
}
