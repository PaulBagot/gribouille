package uit.gon.gribouille.controleurs;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import uit.gon.gribouille.Dialogues;

public class MenusControleur {

	@FXML
	public ToggleGroup epaisseur;
	@FXML
	public ToggleGroup forme;
	
	private Controleur controller;

	public void setController(Controleur controller) {
		this.controller = controller;
	}
	@FXML
	public void onQuitte(ActionEvent evt) {
		if(controller.onQuitter()) {
			Platform.exit();
		}
		evt.consume();
	}
}
