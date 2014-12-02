package controle;

import java.util.Observable;
import java.util.Observer;

import presentation.AfficheImage;
import model.Division;

public class ControleImage implements Observer {

	private AfficheImage affIm;
	
	public ControleImage(AfficheImage affIm) {
		this.affIm = affIm;
	}

	public void update(Observable o, Object message) {
		Integer iMessage = (Integer) message;
		if (iMessage == Division.CHANGEMENT_CLUB) {
			affIm.repaint();
		}
	}
}
