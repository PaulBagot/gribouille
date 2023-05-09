package uit.gon.gribouille.controleurs;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import uit.gon.gribouille.Dialogues;
import uit.gon.gribouille.modele.Dessin;
import uit.gon.gribouille.modele.Figure;
import uit.gon.gribouille.modele.Trace;

public class Controleur implements Initializable{

	public Dessin dessin;
	public final SimpleDoubleProperty prevX = new SimpleDoubleProperty();
	public final SimpleDoubleProperty prevY = new SimpleDoubleProperty();
	public final SimpleIntegerProperty epaisseur = new SimpleIntegerProperty();
	public final SimpleObjectProperty<Color> couleur = new SimpleObjectProperty<Color>(Color.BLACK);
	public Trace trace;
	
	public @FXML MenusControleur menusController;
	public @FXML CouleursControleur couleursController;
	public @FXML DessinControleur dessinsController;
	public @FXML StatutControleur statutController;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menusController.setController(this);
		couleursController.setController(this);
		dessinsController.setController(this);
		statutController.setController(this);
		
		statutController.labelX.textProperty().bind(prevX.asString());
		statutController.labelY.textProperty().bind(prevY.asString());
		statutController.labelEpaisseur.textProperty().bind(epaisseur.asString());
		statutController.labelCouleur.textProperty().bind(couleur.asString());
		
		ChangeListener<Number> gestionnaire = new ChangeListener<Number>() {
			
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(trace != null) dessin.addFigure(trace);
				for(Figure fig : dessin.getFigures()) {
					for(int i = 1; i < fig.getPoints().size(); i++) {
						dessinsController.canvas.getGraphicsContext2D().strokeLine(fig.getPoints().get(i-1).getX(), fig.getPoints().get(i-1).getY(), fig.getPoints().get(i).getX(), fig.getPoints().get(i).getY());
					}
				}
				
			}
			
		};
		dessinsController.canvas.heightProperty().addListener(gestionnaire);
		dessinsController.canvas.widthProperty().addListener(gestionnaire);
	} 
	
	public boolean onQuitter() {
		return Dialogues.confirmation();
	}

	@FXML
	public void onMouseDragged(MouseEvent event) {
		dessinsController.canvas.getGraphicsContext2D().strokeLine(prevX.getValue(), prevY.getValue(), event.getX(), event.getY());
		trace.addPoint(event.getX(), event.getY());
		prevX.setValue(event.getX()); 
		prevY.setValue(event.getY());
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
	}
	
	public void setDessin(Dessin _dessin) {
		dessin = _dessin;
	}
	
}
