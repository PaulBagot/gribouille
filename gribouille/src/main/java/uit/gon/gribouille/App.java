package uit.gon.gribouille;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private double prevX;
    private double prevY;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("CadreGribouille"), 640, 480);
        Canvas dessin = (Canvas) scene.lookup("Canvas");
        stage.setScene(scene);
        stage.setMinHeight(scene.getHeight());
        stage.setMinWidth(scene.getWidth());
        stage.show();
        stage.setOnCloseRequest( e -> {
        	if(!Dialogues.confirmation()) {
        		e.consume();
        	}
        });
        
        
        dessin.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
        	prevX = e.getX();
        	prevY = e.getY();
        });
        
        dessin.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
        	dessin.getGraphicsContext2D().strokeLine(prevX, prevY, e.getX(), e.getY());
        	prevX = e.getX();
            prevY = e.getY();
        });
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}