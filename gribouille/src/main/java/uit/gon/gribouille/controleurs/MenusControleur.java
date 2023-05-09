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
		formeGroupe.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if(newValue instanceof OutilEtoile) {
					controller.onCrayon();
				} else {
					controller.onEtoile();
				}
					
			}
			
		});
	}

	@FXML
	public void onQuitte(ActionEvent evt) {
		if(controller.onQuitter()) {
			Platform.exit();
		}
		evt.consume();
	}
	
	public void setController(Controleur controller) {
		this.controller = controller;
	}
}
