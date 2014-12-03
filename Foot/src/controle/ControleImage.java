package controle;

import java.util.Observable;
import java.util.Observer;

import model.Obs;
import presentation.AfficheImage;

public class ControleImage implements Observer {

	private AfficheImage affIm;
	
	public ControleImage(AfficheImage affIm) {
		this.affIm = affIm;
	}

	public void update(Observable o, Object message) {
		Integer iMessage = (Integer) message;
		if (iMessage == Obs.CHANGEMENT_CLUB) {
			affIm.repaint();
		}
	}
}
