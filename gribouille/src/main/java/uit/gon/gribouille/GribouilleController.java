package uit.gon.gribouille;

import java.net.URL;

import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import uit.gon.gribouille.modele.Dessin;
import uit.gon.gribouille.modele.Figure;
import uit.gon.gribouille.modele.Trace;

public class GribouilleController implements Initializable {

	@FXML
	private Canvas canvas;
	@FXML
	private ColorPicker colorPicker;
	@FXML
	private Label Labels;

	@FXML
	private Pane pane;

	@FXML
	private Rectangle rectangles;

	@FXML
	private ToggleGroup epaisseur;
	@FXML
	private ToggleGroup forme;
	@FXML
	private Label labelX;
	@FXML
	private Label labelY;
	
	private Dessin dessin;
	private Trace trace;
	
	private SimpleDoubleProperty prevX;
	private SimpleDoubleProperty prevY;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prevX = new SimpleDoubleProperty();
		prevY = new SimpleDoubleProperty();
		canvas.widthProperty().bind(pane.widthProperty());
		canvas.heightProperty().bind(pane.heightProperty());
		
		ChangeListener<Number> gestionnaire = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(trace != null) {
					dessin.addFigure(trace);
				}
				for(Figure fig : dessin.getFigures()) {
					canvas.getGraphicsContext2D();
					for(int i = 1; i < fig.getPoints().size(); i++) {
						canvas.getGraphicsContext2D().strokeLine(fig.getPoints().get(i-1).getX(), fig.getPoints().get(i-1).getY(), fig.getPoints().get(i).getX(), fig.getPoints().get(i).getY());
					}
				}
				
			}
		};
		canvas.heightProperty().addListener(gestionnaire);
		canvas.widthProperty().addListener(gestionnaire);
	}

	@FXML
	public void onMouseDragged(MouseEvent event) {
		canvas.getGraphicsContext2D().strokeLine(prevX.getValue(), prevY.getValue(), event.getX(), event.getY());
		trace.addPoint(event.getX(), event.getY());
		prevX.setValue(event.getX()); 
		prevY.setValue(event.getY());
		labelX.setText(prevX.getValue()+"");
		labelY.setText(prevY.getValue()+"");
	}

	@FXML
	public void onMousePressed(MouseEvent event) {
		prevX.setValue(event.getX()); 
		prevY.setValue(event.getY());
		if(trace != null) {
			if(trace.getPoints().size() > 1) {
				dessin.addFigure(trace);
			}
		}
		trace = new Trace(5, "noir", prevX.getValue(), prevY.getValue());
	}
	
	@FXML
	public void onMouseMoved(MouseEvent event) {
		prevX.setValue(event.getX()); 
		prevY.setValue(event.getY());
		labelX.setText(prevX.getValue()+"");
		labelY.setText(prevY.getValue()+"");
	}
	
	public void setDessin(Dessin _dessin) {
		dessin = _dessin;
	}
}