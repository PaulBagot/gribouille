package uit.gon.gribouille;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uit.gon.gribouille.modele.Dessin;
import javafx.scene.canvas.Canvas;
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
        GribouilleController gribouilleController = fxmlLoader.getController();
        gribouilleController.setDessin(dessin);
        
        stage.setScene(scene);
        stage.setMinHeight(scene.getHeight());
        stage.setMinWidth(scene.getWidth());
        stage.show();
        stage.setOnCloseRequest( e -> {
        	if(!Dialogues.confirmation()) {
        		e.consume();
        	}
        });
    }

    public static void main(String[] args) {
        launch();
    }

}