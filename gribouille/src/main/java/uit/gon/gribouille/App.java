package uit.gon.gribouille;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uit.gon.gribouille.controleurs.Controleur;
import uit.gon.gribouille.modele.Dessin;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
    	Dessin dessin = new Dessin();
    	stage.titleProperty().bind(dessin.nomDuFichierProperty());
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("CadreGribouille.fxml"));
        scene = new Scene(fxmlLoader.load(), 640, 480);
        Controleur controleur = fxmlLoader.getController();
        controleur.setDessin(dessin);
        
        stage.setScene(scene);
        stage.setMinHeight(scene.getHeight());
        stage.setMinWidth(scene.getWidth());
        stage.show();
        stage.setOnCloseRequest( e -> {
        	if(!Dialogues.confirmation()) {
        		e.consume();
        	}
        });
        stage.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
        	controleur.onKeyPressed(e.getText());
        	System.out.println(e.getText());
        });
    }

    public static void main(String[] args) {
        launch();
    }

}