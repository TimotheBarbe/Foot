package controle;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;

import model.Club;
import model.Obs;

public class ControleFrameClub implements ItemListener, Observer {

	private Obs obs;
	private Club c;
	private JComboBox<Integer> comboGroupe;

	public ControleFrameClub(Obs obs, Club c, JComboBox<Integer> comboGroupe) {
		this.obs = obs;
		this.c = c;
		this.comboGroupe = comboGroupe;
	}

	public void itemStateChanged(ItemEvent e) {
		int[] tmp = obs.getReponseSolveur();
		tmp[obs.getDiv().getListe().indexOf(c)] = (int) e.getItem() - 1;
		this.obs.setReponseSolveur(tmp);
		this.obs.setClubSelectionne(c);
		this.obs.setIndiceSurvole(obs.getDiv().getListe().indexOf(c));
	}

	public void update(Observable o, Object message) {
		Integer iMessage = (Integer) message;
		if (iMessage == Obs.CHANGEMENT_REPONSE_SOLVEUR) {
			int groupe = obs.getReponseSolveur()[obs.getDiv().getListe()
					.indexOf(c)];
			comboGroupe.setSelectedItem(groupe + 1);
		}
	}

}