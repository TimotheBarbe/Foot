package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import model.Obs;
import controle.ControleImage;
import controle.ControleJBoxTest;

public class MainWindows extends JFrame {

	private static final long serialVersionUID = 1L;
	private static int LABEL_SIZE = 12;
	private Obs obs;

	public MainWindows(Obs obs) {
		// this.setSize(800, 600);
		this.setTitle("Foot");
		this.obs = obs;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// this.setContentPane(new
		// AfficheImage("C:\\carte_region.jpg",listeClub));
		this.getContentPane().setLayout(new BorderLayout());
		this.setResizable(false);
		this.setVisible(true);

		this.creerCarte();
		this.creerGauche();
		this.pack();
	}

	private void creerCarte() {
		JPanel panelCarte = new JPanel(new BorderLayout());
		panelCarte.setPreferredSize(new Dimension(800, 600));
		panelCarte.setBorder(BorderFactory.createEmptyBorder(3, 6, 6, 6));

		AfficheImage affIm = new AfficheImage("carte_region.jpg",
				obs);
		panelCarte.add(affIm, BorderLayout.CENTER);
		ControleImage cimg = new ControleImage(affIm);
		this.obs.addObserver(cimg);

		this.getContentPane().add(panelCarte, BorderLayout.CENTER);
	}

	private void creerGauche() {
		JPanel panelListe = new JPanel(new BorderLayout());
		panelListe.setBorder(BorderFactory.createEmptyBorder(6, 6, 3, 3));
		panelListe.setPreferredSize(new Dimension(200, 600));

		// TITRE
		JLabel labelClub = new JLabel("Clubs");
		labelClub.setFont(new Font(labelClub.getFont().getName(), Font.BOLD,
				LABEL_SIZE));
		panelListe.add(labelClub, BorderLayout.NORTH);

		// LISTE CLUBS
		JList<String> listeClub = this.getJListClub();
		panelListe.add(listeClub, BorderLayout.CENTER);

		// TESTS
		JCheckBox test = new JCheckBox();
		panelListe.add(test, BorderLayout.SOUTH);
		ControleJBoxTest controleboxtest = new ControleJBoxTest(this.obs);
		test.addActionListener(controleboxtest);

		this.getContentPane().add(panelListe, BorderLayout.WEST);
	}

	private JList<String> getJListClub() {
		JList<String> listeClub = new JList<String>();
		Vector<String> data = new Vector<String>();
		int clubRestant = obs.getReponseSolveur().length;
		int indice = 0;
		while (clubRestant > 0) {
			data.add("Poule " + indice);
			for (int i = 0; i < obs.getReponseSolveur().length; i++) {
				if (this.obs.getReponseSolveur()[i] == indice) {
					data.add("  " + this.obs.getDiv().getListe().get(i));
					clubRestant--;
				}
			}
			indice++;
		}
		listeClub.setListData(data);
		listeClub.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return listeClub;
	}
}
