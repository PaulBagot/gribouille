package uit.gon.gribouille.controleurs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.WritableImage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.embed.swing.SwingFXUtils;
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
	public boolean controlKey = false;
	
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

		couleursController.colorPicker.valueProperty().addListener(new ChangeListener<Color>() {
			@Override
			public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
				couleur.set(newValue);
				couleursController.reinitialiseSelectedRectangle();
			}
		});
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
		if(!dessin.getEstModifie()) return Dialogues.confirmation();
		Alert a = new Alert(AlertType.CONFIRMATION, "sauvegarder avant de quitter?");
		a.setTitle("Confirmation");
		a.getButtonTypes().clear();
		a.getButtonTypes().add(ButtonType.CANCEL);
		a.getButtonTypes().add(ButtonType.NO);
		a.getButtonTypes().add(ButtonType.YES);
		ButtonType result = a.showAndWait().get();
		if(result == ButtonType.NO) return true;
		if(result == ButtonType.YES)
			return sauvegarde(dessinsController.canvas.getScene());
		return false;
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

	public void onKeyPressed(KeyEvent keyEvent, String key) {
		switch(key) {
			case "1" : setEpaisseur(1); break;
			case "2" : setEpaisseur(2); break;
			case "3" : setEpaisseur(3); break;
			case "4" : setEpaisseur(4); break;
			case "5" : setEpaisseur(5); break;
			case "6" : setEpaisseur(6); break;
			case "7" : setEpaisseur(7); break;
			case "8" : setEpaisseur(8); break;
			case "9" : setEpaisseur(9); break;
			default: break;
		}
		if(key.equals("z")) {
			if(controlKey)
				retourArriere();
		} else
			controlKey = false;
		if(keyEvent.getCode() == KeyCode.CONTROL)
			controlKey = true;
	}
	
	public boolean sauvegarde(Scene scene) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Sauvegarder");
		fileChooser.setInitialFileName("sans nom");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("fichier canvas", "*.canva"));
		File file = fileChooser.showSaveDialog(scene.getWindow());
		if(file != null) {
			dessin.sauveSous(file.getAbsolutePath());
			dessin.setNomDuFichier(file.getName());
			return true;
		}
		return false;
	}
	
	public void charge(Scene scene) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Charger");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("fichier canvas", "*.canva"));
		File file = fileChooser.showOpenDialog(scene.getWindow());
		if(file != null) {
			dessinsController.reinitialiseCanvas();
			dessin.setNomDuFichier(file.getName());
			trace = null;
			dessin.charge(file.getAbsolutePath());
			dessine();
		}
	}
	
	public void effacer() {
		dessin.getFigures().clear();
		trace = null;
		dessinsController.reinitialiseCanvas();
	}
	
	public void retourArriere() {
		if(dessin.getFigures().size() != 0)
			dessin.getFigures().remove(dessin.getFigures().size() - 1);
		trace = null;
		dessinsController.reinitialiseCanvas();
		dessine();
	}
	
	public void exporter(Scene scene) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("fichier jpeg", "*.jpg"));
		fileChooser.setTitle("Exporter");
		fileChooser.setInitialFileName("sans nom");
		File file = fileChooser.showSaveDialog(scene.getWindow());
		WritableImage image = dessinsController.canvas.snapshot(new SnapshotParameters(), null);
		if(file != null) {
			try {
				javax.imageio.ImageIO.write(SwingFXUtils.fromFXImage(image,null), "png", file);
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.showAndWait();
			}
		}
	}
}
