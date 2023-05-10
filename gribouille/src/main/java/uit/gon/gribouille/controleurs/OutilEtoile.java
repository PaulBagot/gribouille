package uit.gon.gribouille.controleurs;

import javafx.scene.input.MouseEvent;
import uit.gon.gribouille.modele.Etoile;
import uit.gon.gribouille.modele.Trace;

public class OutilEtoile extends Outil{

	public OutilEtoile(Controleur _controleur) {
		super(_controleur);
	}

	@Override
	public void onMousePress(MouseEvent event) {
		controleur.trace = new Trace(5, "noir", controleur.prevX.getValue(), controleur.prevY.getValue());
		controleur.dessin.addFigure(controleur.trace);
	}

	@Override
	public void onMouseDrag(MouseEvent event) {
		Etoile etoile = new Etoile(5, "noir", controleur.prevX.getValue(), controleur.prevY.getValue()); 	
	}

}
