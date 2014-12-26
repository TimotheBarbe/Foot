package controle;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Club;
import model.Obs;

public class ControleJListClub implements ListSelectionListener {

	private Obs obs;

	public ControleJListClub(Obs obs) {
		this.obs = obs;
	}

	public void valueChanged(ListSelectionEvent e) {
		String nom = ((JList) e.getSource()).getSelectedValue().toString();
		try {
			String id = nom.substring(nom.indexOf("(") + 1,
					nom.indexOf(")"));
			Club c = obs.getDiv().getClubById(Integer.parseInt(id));
			this.obs.setIndiceSurvole(obs.getDiv().getListe().indexOf(c));
		} catch (Exception ex) {

		}

	}

}
