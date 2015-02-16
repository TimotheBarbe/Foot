package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Obs;
import controle.ControleBoutonExportCarte;

public class ChoixExportCarte extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Obs obs;
	JCheckBox carteTotal;
	JCheckBox cartesGroupe;
	JCheckBox carteSelection;

	public ChoixExportCarte(Obs obs) {
		super("Choix des exports");
		this.obs = obs;
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setVisible(true);
		this.build();
		this.pack();
		this.setLocationRelativeTo(null);
	}

	private void build() {
		// JBOX
		JPanel panelBoxes = new JPanel();
		panelBoxes.setLayout(new BoxLayout(panelBoxes, BoxLayout.Y_AXIS));
		carteTotal = new JCheckBox("La carte avec tous les groupes");
		carteTotal.setSelected(true);
		cartesGroupe = new JCheckBox("Les cartes par groupe");
		cartesGroupe.setSelected(true);
		carteSelection = new JCheckBox("La carte des éléments sélectionnés");
		panelBoxes.add(carteTotal);
		panelBoxes.add(cartesGroupe);
		panelBoxes.add(carteSelection);

		// JBUTTON
		JPanel panelBouton = new JPanel();
		panelBouton.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		JButton validation = new JButton("Exporter");
		panelBouton.add(validation);
		validation.addActionListener(this);

		this.add(panelBoxes);
		this.add(panelBouton);
	}

	public void actionPerformed(ActionEvent e) {
		this.dispose();
		ControleBoutonExportCarte exp = new ControleBoutonExportCarte(obs,
				carteTotal.isSelected(), cartesGroupe.isSelected(),
				carteSelection.isSelected());
	}
}
