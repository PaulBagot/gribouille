package uit.gon.gribouille.controleurs;

import javafx.scene.input.MouseEvent;

public abstract class Outil {
	protected Controleur controleur;
	
	public Outil(Controleur _controleur) {
		controleur = _controleur;
	}
	
	public abstract void onMousePress(MouseEvent event);
	public abstract void onMouseDrag(MouseEvent event);
}
