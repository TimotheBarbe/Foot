package controle;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JList;

import model.Obs;

public class ControleJListDistance implements Observer {

	private Obs obs;
	private JList<String> listeDistance;

	public ControleJListDistance(Obs obs, JList<String> listeDistance) {
		this.obs = obs;
		this.listeDistance = listeDistance;
	}

	public void update(Observable o, Object message) {
		Integer iMessage = (Integer) message;
		if (iMessage == Obs.CHANGEMENT_CLUB_COURANT
				|| iMessage == Obs.CHANGEMENT_REPONSE_SOLVEUR) {
			if (obs.getIndiceJListClubSelection() >= 0) {
				listeDistance.setSelectedIndex(obs
						.getIndiceJListClubSelection());
				listeDistance.ensureIndexIsVisible(listeDistance
						.getSelectedIndex());
			}
		}

	}

}
