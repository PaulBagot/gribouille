package fr.unicaen.iut.tp5;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Toggle;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ControleurDemineur implements Initializable{

	private ModeleDemineur modeleDemineur;
	private @FXML GridPane grille;
	private @FXML ToggleGroup difficulte;
	private @FXML TextField nbInconnues;
	private @FXML TextField nbMarques;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		modeleDemineur = new ModeleDemineur(10, 10, 10);
		nbInconnues.textProperty().bind(modeleDemineur.nbInconnuesProperty().asString());
		nbMarques.textProperty().bind(modeleDemineur.nbMarquesProperty().asString());
		
		difficulte.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				initGrille(difficulte.getSelectedToggle().getUserData().toString());
			}
		});
	}
	
	public void initGrille(String userData) {
		grille.getColumnConstraints().clear();
		grille.getRowConstraints().clear();
		int args[] = ModeleDemineur.parseUserData(userData);
		modeleDemineur.setTaille(args[0], args[1], args[2]);
		
		for(int i = 0; i < args[0]; i++) {
			grille.getRowConstraints().add(new RowConstraints(32));
		}
		for(int i = 0; i < args[1]; i++) {
			grille.getColumnConstraints().add(new ColumnConstraints(32));
		}
	}

}
