package controle;

import java.util.Observable;
import java.util.Observer;

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
		String nom = listeClub.getSelectedValue().toString();
		try {
			String id = nom.substring(nom.indexOf("(") + 1, nom.indexOf(")"));
			Club c = obs.getDiv().getClubById(Integer.parseInt(id));
			this.obs.setClubSelectionne(c);
			this.obs.setIndiceSurvole(obs.getDiv().getListe().indexOf(c));
		} catch (Exception ex) {

		}
	}

	public void update(Observable o, Object message) {
		Integer iMessage = (Integer) message;
		if (iMessage == Obs.CHANGEMENT_CLUB) {
			if (obs.getClubSelectionne() != null) {
				for (int i = 0; i < listeClub.getModel().getSize(); i++) {
					if (listeClub.getModel().getElementAt(i)
							.equals("  " + obs.getClubSelectionne())) {
						listeClub.setSelectedIndex(i);
					}
				}
			}
		}
		listeClub.ensureIndexIsVisible(listeClub.getSelectedIndex());
	}
}
