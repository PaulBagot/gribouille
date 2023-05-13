package fr.unicaen.iut.tp5;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Toggle;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class ControleurDemineur implements Initializable{

	private ModeleDemineur modeleDemineur =  new ModeleDemineur(10, 10, 10);
	private @FXML GridPane grille;
	private @FXML ToggleGroup difficulte;
	private @FXML TextField nbInconnues;
	private @FXML TextField nbMarques;
	private Background inconnu = new Background(new BackgroundFill(Color.ALICEBLUE, new CornerRadii(0.2), null));
	private Background libre = new Background(new BackgroundFill(Color.LIGHTGRAY, null, null));
	private Background echec = new Background(new BackgroundFill(Color.RED, null, null));
	private Background marquee = new Background(new BackgroundFill(Color.LEMONCHIFFON, null, null));
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		difficulte.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				initGrille(difficulte.getSelectedToggle().getUserData().toString());
			}
		});
	}
	
	@FXML
	public void onQuitter() {
		Platform.exit();
	}
	
	public void initGrille(String userData) {
		modeleDemineur = new ModeleDemineur(10, 10, 10);
		
		grille.getColumnConstraints().clear();
		grille.getRowConstraints().clear();
		grille.getChildren().clear();
		int args[] = ModeleDemineur.parseUserData(userData);
		modeleDemineur.setTaille(args[0], args[1], args[2]);
		
		for(int i = 0; i < args[0]; i++) {
			grille.getRowConstraints().add(new RowConstraints(32));
		}
		for(int i = 0; i < args[1]; i++) {
			grille.getColumnConstraints().add(new ColumnConstraints(32));
		}
		for(int i = 0; i < args[0]; i++) {
			for(int j = 0; j < args[1]; j++) {
				int line = i;
				int column  = j;
				Label label = new Label();
				label.setPrefSize(31, 31);
				label.setBackground(inconnu);
				label.setTextAlignment(TextAlignment.CENTER);
				label.textProperty().bind(modeleDemineur.texteProperty(i, j));
				
				label.setOnMouseClicked(e -> {
					if(!modeleDemineur.estPerdu()) {
						if(e.getButton() == MouseButton.PRIMARY) {
							if(!modeleDemineur.estRevelee(line, column)) {
								modeleDemineur.revele(line, column);
								if(label.getText() == "X") {
									label.setBackground(echec);
								} else {
									label.setBackground(libre);
								}
							}
						}
						if(e.getButton() == MouseButton.SECONDARY) {
							if(!modeleDemineur.estRevelee(line, column)) {
								if(modeleDemineur.estMarquee(line, column)) {
									modeleDemineur.marque(line, column);
									label.setBackground(inconnu);
								} else {
									modeleDemineur.marque(line, column);
									label.setBackground(marquee);
								}
							}
						}
					}
				});
				
				grille.add(label, i, j);

				nbInconnues.textProperty().bind(modeleDemineur.nbInconnuesProperty().asString());
				nbMarques.textProperty().bind(modeleDemineur.nbMarquesProperty().asString());
			}
		}
	}
}
