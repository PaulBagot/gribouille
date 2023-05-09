package uit.gon.gribouille.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.shape.Rectangle;

public class CouleursControleur {

	@FXML
	public Rectangle rectangles;
	@FXML
	public ColorPicker colorPicker;
	
	private Controleur controller;
	
	
	
	public void setController(Controleur controller) {
		this.controller = controller;
	}
}
