package uit.gon.gribouille.controleurs;

import javafx.scene.input.MouseEvent;
import uit.gon.gribouille.modele.Trace;

public class OutilCrayon extends Outil{

	public OutilCrayon(Controleur _controleur) {
		super(_controleur);
	}

	@Override
	public void onMousePress(MouseEvent event) {
		/*if(controleur.trace != null) {
			if(controleur.trace.getPoints().size() > 1) {
				controleur.dessin.addFigure(controleur.trace);
			}
		}*/
		controleur.trace = new Trace(5, "noir", controleur.prevX.getValue(), controleur.prevY.getValue());
		controleur.dessin.addFigure(controleur.trace);
	}

	@Override
	public void onMouseDrag(MouseEvent event) {
		controleur.dessinsController.canvas.getGraphicsContext2D().strokeLine(controleur.prevX.getValue(), controleur.prevY.getValue(), event.getX(), event.getY());
		controleur.trace.addPoint(event.getX(), event.getY());
	}

}
