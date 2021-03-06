package controle;

import java.util.Observable;
import java.util.Observer;

import model.Obs;
import presentation.AfficheImage;

public class ControleImage implements Observer {

	private AfficheImage affImage;

	public ControleImage(AfficheImage affImage) {
		this.affImage = affImage;
	}

	public void update(Observable o, Object message) {
		Integer iMessage = (Integer) message;
		if (iMessage == Obs.CHANGEMENT_CLUB_COURANT
				|| iMessage == Obs.CHANGEMENT_DESSIN
				|| iMessage == Obs.CHANGEMENT_REPONSE_SOLVEUR) {
			affImage.repaint();
		}
	}
}
