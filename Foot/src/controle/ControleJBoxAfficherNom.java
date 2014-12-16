package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Obs;

public class ControleJBoxAfficherNom implements ActionListener {

	private Obs obs;

	public ControleJBoxAfficherNom(Obs obs) {
		this.obs = obs;
	}

	public void actionPerformed(ActionEvent e) {
		obs.setAfficherNom(!obs.isAfficherNom());
	}

}
