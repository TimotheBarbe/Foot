package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.EquivalentLettre;
import model.Obs;

public class ControleJPopMenu implements ActionListener {

	private Obs obs;
	private int numeroClub;

	public ControleJPopMenu(Obs obs, int numeroClub) {
		this.obs = obs;
		this.numeroClub = numeroClub;
	}

	public void actionPerformed(ActionEvent e) {
		int[] reponseSolveur = obs.getReponseSolveur();
		reponseSolveur[numeroClub] = EquivalentLettre.getIndice((String) (e
				.getActionCommand()));
		obs.setReponseSolveur(reponseSolveur);
	}

}
