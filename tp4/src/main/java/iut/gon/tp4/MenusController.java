package iut.gon.tp4;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class MenusController {
	
	
	  private GrilleModel modele;
	  private Scores table;
	  @FXML
	  public void onMenuNouvelle(ActionEvent evt) {
	    modele.nouvellePartie();
	  }
	  
	  @FXML
	  public void onMenuTable(ActionEvent evt) {
		  try {
			  FXMLLoader fxmlLoader = new FXMLLoader(Morpion.class.getResource("table.fxml"));
			  Morpion.setRoot(fxmlLoader.load());
			  TableController tc = fxmlLoader.getController();
			  tc.setScores(table);
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	  }

	  @FXML
	  public void onMenuQuitter(ActionEvent evt) {
	    Platform.exit();
	  }
	  
	  @FXML
	  public void setParams(GrilleModel gm, Scores s) {
		  modele = gm;
		  table = s;
	  }
}
