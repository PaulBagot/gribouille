package fr.unicaen.iut.tp5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainDemineur extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(new Label("Ok !"), 800, 600));
		stage.show();
	}
}
