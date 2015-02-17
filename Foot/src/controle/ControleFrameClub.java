package controle;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import model.Club;
import model.EquivalentLettre;
import model.Obs;

public class ControleFrameClub implements ItemListener, Observer {

	private Obs obs;
	private Club c;
	private JComboBox<String> comboGroupe;
	private JLabel distance;

	public ControleFrameClub(Obs obs, Club c, JComboBox<String> comboGroupe,
			JLabel distance) {
		this.obs = obs;
		this.c = c;
		this.comboGroupe = comboGroupe;
		this.distance = distance;
	}

	public void itemStateChanged(ItemEvent e) {
		int[] tmp = obs.getReponseSolveur();
		String lettre = (String) e.getItem();
		int groupe = EquivalentLettre.getIndice(lettre);
		if (groupe >= 0) {
			tmp[obs.getDiv().getListe().indexOf(c)] = groupe;
			this.obs.setReponseSolveur(tmp);
			this.obs.setClubSelectionne(c);
			this.obs.setIndiceSurvole(obs.getDiv().getListe().indexOf(c));
		}
	}

	public void update(Observable o, Object message) {
		Integer iMessage = (Integer) message;
		if (iMessage == Obs.CHANGEMENT_REPONSE_SOLVEUR) {
			int groupe = obs.getReponseSolveur()[obs.getDiv().getListe()
					.indexOf(c)];
			comboGroupe.setSelectedItem(EquivalentLettre.getLettre(groupe));
			this.distance.setText("Distance : "
					+ obs.getDistParcourue(obs.getDiv().getListe().indexOf(c)));
		}
	}

}
