package uit.gon.gribouille.controleurs;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DessinControleur implements Initializable{
	
	@FXML
	public Canvas canvas;
	@FXML
	public Pane pane;
	
	private Controleur controller;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		canvas.widthProperty().bind(pane.widthProperty());
		canvas.heightProperty().bind(pane.heightProperty());
	}
	
	public void efface(double x1, double y1, double x2, double y2) {
		canvas.getGraphicsContext2D().clearRect(x1, y1, x2, y2);
	}
	
	public void trace(double x1, double y1, double x2, double y2) {
		canvas.getGraphicsContext2D().strokeLine(x1, y1, x2, y2);
	}

	@FXML
	public void onMouseDragged(MouseEvent event) {
		controller.onMouseDragged(event);
	}

	@FXML
	public void onMousePressed(MouseEvent event) {
		controller.onMousePressed(event);
	}
	
	@FXML
	public void onMouseMoved(MouseEvent event) {
		controller.onMouseMoved(event);
	}
	
	public void setController(Controleur _controller) {
		controller = _controller;
	}
}
