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
import javafx.scene.paint.Paint;
import uit.gon.gribouille.Dialogues;
import uit.gon.gribouille.modele.Dessin;
import uit.gon.gribouille.modele.Figure;
import uit.gon.gribouille.modele.Trace;

public class Controleur implements Initializable{

	public Dessin dessin;
	public final SimpleDoubleProperty prevX = new SimpleDoubleProperty();
	public final SimpleDoubleProperty prevY = new SimpleDoubleProperty();
	public final SimpleIntegerProperty epaisseur = new SimpleIntegerProperty(1);
	public final SimpleObjectProperty<Color> couleur = new SimpleObjectProperty<Color>(Color.BLACK);
	public Outil outil = new OutilCrayon(this);
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
		
		dessinsController.canvas.heightProperty().addListener(changeListener -> dessine());
		dessinsController.canvas.widthProperty().addListener(changeListener -> dessine());
	} 
	
	public void dessine() {
		if(trace != null) dessin.addFigure(trace);
		for(Figure fig : dessin.getFigures()) {
			dessinsController.canvas.getGraphicsContext2D().setLineWidth(fig.getEpaisseur());
			dessinsController.canvas.getGraphicsContext2D().setStroke(Color.valueOf(fig.getCouleur()));
			
			for(int i = 1; i < fig.getPoints().size(); i++) {
				dessinsController.canvas.getGraphicsContext2D().strokeLine(fig.getPoints().get(i-1).getX(), fig.getPoints().get(i-1).getY(), fig.getPoints().get(i).getX(), fig.getPoints().get(i).getY());
			}
		}
		dessinsController.setEpaisseur();
		dessinsController.setCouleur();
	}

	public boolean onQuitter() {
		return Dialogues.confirmation();
	}

	@FXML
	public void onMouseDragged(MouseEvent event) {
		outil.onMouseDrag(event);
		prevX.setValue(event.getX()); 
		prevY.setValue(event.getY());
	}

	@FXML
	public void onMousePressed(MouseEvent event) {
		prevX.setValue(event.getX()); 
		prevY.setValue(event.getY());
		outil.onMousePress(event);
	}
	
	@FXML
	public void onMouseMoved(MouseEvent event) {
		prevX.setValue(event.getX()); 
		prevY.setValue(event.getY());
	}
	
	public void setDessin(Dessin _dessin) {
		dessin = _dessin;
	}

	public void onCrayon() {
		outil = new OutilEtoile(this);
		statutController.labelOutil.setText("Etoile");
	}

	public void onEtoile() {
		outil = new OutilCrayon(this);
		statutController.labelOutil.setText("Crayon");
	}

	public void setEpaisseur(int _epaisseur) {
		epaisseur.set(_epaisseur);
	}
	
	public void setCouleur(Paint paint) {
		couleur.set((Color) paint);
	}
}
