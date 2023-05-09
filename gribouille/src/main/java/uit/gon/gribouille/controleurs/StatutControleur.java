package uit.gon.gribouille.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StatutControleur {
	
	@FXML
	public Label labels;
	@FXML
	public Label labelX;
	@FXML
	public Label labelY;
	@FXML
	public Label labelEpaisseur;
	@FXML
	public Label labelCouleur;

	private Controleur controller;

	public void setController(Controleur controller) {
		this.controller = controller;
	}
}
