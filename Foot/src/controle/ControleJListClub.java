package controle;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Club;
import model.Obs;

public class ControleJListClub implements ListSelectionListener, Observer {

	private Obs obs;
	private JList<String> listeClub;

	public ControleJListClub(Obs obs, JList<String> listeClub) {
		this.obs = obs;
		this.listeClub = listeClub;
	}

	public void valueChanged(ListSelectionEvent e) {
		// TODO : faire un test propre
		try {
			String nom = listeClub.getSelectedValue().toString();
			String id = nom.substring(nom.indexOf("(") + 1, nom.indexOf(")"));
			Club c = obs.getDiv().getClubById(Integer.parseInt(id));
			this.obs.setClubSelectionne(c);
			this.obs.setIndiceSurvole(obs.getDiv().getListe().indexOf(c));
		} catch (Exception ex) {

		}
	}

	public void update(Observable o, Object message) {
		Integer iMessage = (Integer) message;
		if (iMessage == Obs.CHANGEMENT_CLUB_COURANT) {
			if (obs.getClubSelectionne() != null) {
				for (int i = 0; i < listeClub.getModel().getSize(); i++) {
					if (listeClub.getModel().getElementAt(i)
							.equals("  " + obs.getClubSelectionne())) {
						listeClub.setSelectedIndex(i);
						obs.setIndiceJListClubSelection(i);
					}
				}
			}
			listeClub.ensureIndexIsVisible(listeClub.getSelectedIndex());
		}
		if (iMessage == Obs.CHANGEMENT_REPONSE_SOLVEUR) {
			this.refreshJListClub();
		}
	}

	private void refreshJListClub() {
		Vector<String> data = new Vector<String>();
		int clubRestant = obs.getReponseSolveur().length;
		int indice = 0;
		while (clubRestant > 0) {
			data.add("Poule " + (indice + 1));
			for (int i = 0; i < obs.getReponseSolveur().length; i++) {
				if (this.obs.getReponseSolveur()[i] == indice) {
					data.add("  " + this.obs.getDiv().getListe().get(i));
					clubRestant--;
				}
			}
			indice++;
		}
		listeClub.setListData(data);
	}
}
