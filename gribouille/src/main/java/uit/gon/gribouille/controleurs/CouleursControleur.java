package uit.gon.gribouille.controleurs;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class CouleursControleur implements Initializable{

	@FXML
	public Rectangle rectangles;
	public Rectangle selectedRectangle;
	@FXML
	public ColorPicker colorPicker;
	@FXML
	public VBox vBox;
	private Controleur controller;
	
	public void setController(Controleur controller) {
		this.controller = controller;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vBox.setOnMouseClicked(e -> {
			if(e.getTarget() instanceof Rectangle) {
				if(selectedRectangle != null) {
					selectedRectangle.setArcWidth(5);
					selectedRectangle.setArcHeight(5);
					selectedRectangle.setStrokeWidth(1);
				}
				selectedRectangle =  (Rectangle) e.getTarget();
				selectedRectangle.setArcWidth(10);
				selectedRectangle.setArcHeight(10);
				selectedRectangle.setStrokeWidth(5);
				controller.setCouleur(selectedRectangle.getFill());
			}
		});
	}
}
