package presentation;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Club;
import model.EquivalentLettre;
import model.Obs;
import controle.ControleFrameClub;

/**
 * Fenetre contenant les informations specifiques d'un club
 * 
 * @authors Timothé Barbe, Florent Euvrard, Cheikh Sylla
 *
 */
public class FrameClub extends JFrame {

	private static final long serialVersionUID = 1L;

	private Club c;
	private Obs obs;

	/**
	 * Cree et initialiste une nouvelle fenetre
	 * 
	 * @param c
	 *            club a afficher
	 * @param obs
	 *            etat du logiciel
	 */
	public FrameClub(Club c, Obs obs) {
		this.c = c;
		this.obs = obs;
		this.setMinimumSize(new Dimension(200, 100));
		this.setTitle(c.toString());
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.build();
		this.pack();
		this.setLocationRelativeTo(null);
	}

	/**
	 * Contruit la fenetre
	 */
	private void build() {
		// label
		JLabel nom = new JLabel(c.toString());
		nom.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		// combo box
		int groupe = obs.getReponseSolveur()[obs.getDiv().getListe().indexOf(c)];
		JComboBox<String> comboGroupe = new JComboBox<String>();
		for (int i = 0; i < obs.getDiv().getNbGroupe(); i++) {
			comboGroupe.addItem(EquivalentLettre.getLettre(i));
		}
		comboGroupe.setSelectedItem(EquivalentLettre.getLettre(groupe));
		comboGroupe.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		// distance
		JLabel distance = new JLabel("Distance : "
				+ obs.getDistParcourue(obs.getDiv().getListe().indexOf(c))+" km");
		distance.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		ControleFrameClub controleFrame = new ControleFrameClub(obs, c,
				comboGroupe, distance);
		comboGroupe.addItemListener(controleFrame);
		obs.addObserver(controleFrame);

		this.add(nom);
		this.add(comboGroupe);
		this.add(distance);
	}
}
