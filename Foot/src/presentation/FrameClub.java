package presentation;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Club;
import model.Obs;
import controle.ControleFrameClub;

public class FrameClub extends JFrame {

	private static final long serialVersionUID = 1L;

	private Club c;
	private Obs obs;

	public FrameClub(Club c, Obs obs) {
		this.c = c;
		this.obs = obs;

		this.setTitle(c.toString());
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setVisible(true);
		this.build();
		this.pack();
	}

	private void build() {
		// label
		JLabel nom = new JLabel(c.toString());
		nom.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		// combo box
		int groupe = obs.getReponseSolveur()[obs.getDiv().getListe().indexOf(c)];
		JComboBox<Integer> comboGroupe = new JComboBox<Integer>();
		for (int i = 0; i < obs.getDiv().getNbGroupe(); i++) {
			comboGroupe.addItem(i + 1);
		}
		comboGroupe.setSelectedItem(groupe + 1);
		comboGroupe.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		ControleFrameClub controleFrame = new ControleFrameClub(obs, c,
				comboGroupe);
		comboGroupe.addItemListener(controleFrame);
		obs.addObserver(controleFrame);
		;

		// distance
		JLabel distance = new JLabel("Distance : " + 15.5);
		distance.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		this.add(nom);
		this.add(comboGroupe);
		this.add(distance);
	}
}
