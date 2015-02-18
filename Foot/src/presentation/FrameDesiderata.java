package presentation;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Obs;
import controle.ControleBoutonDesiderata;

public class FrameDesiderata extends JFrame {

	private static final long serialVersionUID = 1L;
	private Obs obs;

	public FrameDesiderata(Obs obs) {
		super("Desiderata");
		this.obs = obs;
		this.setMinimumSize(new Dimension(200, 100));
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.build();
		this.pack();
		this.setLocationRelativeTo(null);
	}

	private void build() {
		int nbFail = obs.nbDesiderataFail();

		if (nbFail == 0) {
			JLabel titre = new JLabel("Aucun desiderata non valide");
			titre.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			this.add(titre);
		} else {
			JLabel titre = new JLabel(nbFail + " desiderata non valides :");
			titre.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			this.add(titre);
			for (int i = 0; i < obs.getDesiderata().size(); i++) {
				if (!obs.desiderataOk(i)) {
					JLabel desi = new JLabel(" - "
							+ obs.getDesiderata().get(i).toString());
					desi.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
					this.add(desi);
				}
			}
		}
		JButton charger = new JButton("Charger un fichier de desiderata", new ImageIcon(
				"Donnees/icone_excel.png"));
		charger.addActionListener(new ControleBoutonDesiderata(this, obs));
		this.add(charger);
	}
}
