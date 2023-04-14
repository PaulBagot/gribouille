package uit.gon.gribouille;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Dialogues {

	public static boolean confirmation() {
		Alert a = new Alert(AlertType.CONFIRMATION, "Voulez-vous vraiment quitter ?");
		a.setTitle("Confirmation");
		if(a.showAndWait().get() == ButtonType.OK) {
			return true;
		}
		return false;
	}
	
}
