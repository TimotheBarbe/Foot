package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import model.Obs;
import controle.ControleImage;
import controle.ControleJBoxAfficherNom;
import controle.ControleJListClub;
import controle.ControleJListDistance;

public class MainWindows extends JFrame {

	private static final long serialVersionUID = 1L;
	private static int LABEL_SIZE = 12;
	private Obs obs;

	public MainWindows(Obs obs) {
		this.setTitle("Foot");
		this.obs = obs;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.getContentPane().setLayout(new BorderLayout());
		this.setResizable(false);
		this.setVisible(true);

		this.creerCarte();
		this.creerGauche();
		this.creerBas();
		this.pack();
	}

	private void creerCarte() {
		JPanel panelCarte = new JPanel(new BorderLayout());

		panelCarte.setPreferredSize(new Dimension(800, 600));
		panelCarte.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

		AfficheImage affIm = new AfficheImage("carte_region.jpg", obs);
		panelCarte.add(affIm, BorderLayout.CENTER);
		ControleImage cimg = new ControleImage(affIm);

		this.obs.addObserver(cimg);
		this.getContentPane().add(panelCarte, BorderLayout.CENTER);
	}

	private void creerGauche() {
		JPanel gauche = new JPanel(new BorderLayout());

		// LISTE DES CLUBS
		JPanel panelListe = new JPanel(new BorderLayout());
		panelListe.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		panelListe.setPreferredSize(new Dimension(200, 600));
		// titre
		JLabel labelClub = new JLabel("Clubs");
		labelClub.setFont(new Font(labelClub.getFont().getName(), Font.BOLD,
				LABEL_SIZE));
		panelListe.add(labelClub, BorderLayout.NORTH);
		// liste
		JScrollPane listeClub = this.getJListClub();
		panelListe.add(listeClub, BorderLayout.CENTER);

		// LISTE DES DISTANCE
		JPanel panelDist = new JPanel(new BorderLayout());
		panelDist.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		panelDist.setPreferredSize(new Dimension(70, 600));
		// titre
		JLabel labelDist = new JLabel("Distances");
		labelDist.setFont(new Font(labelDist.getFont().getName(), Font.BOLD,
				LABEL_SIZE));
		panelDist.add(labelDist, BorderLayout.NORTH);
		// liste
		JScrollPane jsp = this.getJListDist();
		panelDist.add(jsp, BorderLayout.CENTER);

		gauche.add(panelListe, BorderLayout.CENTER);
		gauche.add(panelDist, BorderLayout.EAST);
		this.getContentPane().add(gauche, BorderLayout.WEST);
	}

	private void creerBas() {
		JPanel panelBas = new JPanel(new BorderLayout());

		// AFFICHER NOMS
		JPanel panelWest = new JPanel(new BorderLayout());
		// tickbox
		JCheckBox tickAfficherNom = new JCheckBox();
		panelWest.add(tickAfficherNom, BorderLayout.WEST);
		ControleJBoxAfficherNom controleboxtest = new ControleJBoxAfficherNom(
				this.obs);
		tickAfficherNom.addActionListener(controleboxtest);
		// titre
		JLabel labelAfficherNom = new JLabel("Afficher les noms des clubs?");
		labelAfficherNom.setFont(new Font(labelAfficherNom.getFont().getName(),
				Font.BOLD, LABEL_SIZE));
		panelWest.add(labelAfficherNom, BorderLayout.CENTER);

		// BOUTONS
		JPanel panelEast = new JPanel(new BorderLayout());
		panelEast.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		// export solution
		JButton exportsolution = new JButton("Exporter la solution");
		panelEast.add(exportsolution, BorderLayout.WEST);
		// espace vide
		panelEast.add(Box.createRigidArea(new Dimension(5, 0)),
				BorderLayout.CENTER);
		// export carte
		JButton exportCarte = new JButton("Exporter la carte");
		panelEast.add(exportCarte, BorderLayout.EAST);

		panelBas.add(panelWest, BorderLayout.WEST);
		panelBas.add(panelEast, BorderLayout.EAST);
		this.getContentPane().add(panelBas, BorderLayout.SOUTH);
	}

	private JScrollPane getJListClub() {
		JList<String> listeClub = new JList<String>();
		Vector<String> data = new Vector<String>();
		int clubRestant = obs.getReponseSolveur().length;
		int indice = 0;
		while (clubRestant > 0) {
			data.add("Poule " + (indice + 1));
			for (int  i = 0; i < obs.getReponseSolveur().length; i++) {
				if (this.obs.getReponseSolveur()[i] == indice) {
					data.add("  " + this.obs.getDiv().getListe().get(i));
					clubRestant--;
				}
			}
			indice++;
		}
		listeClub.setListData(data);
		listeClub.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Controleur
		ControleJListClub cjlist = new ControleJListClub(obs, listeClub);
		listeClub.addListSelectionListener(cjlist);
		this.obs.addObserver(cjlist);
		return new JScrollPane(listeClub);
	}

	private JScrollPane getJListDist() {
		JList<String> listeDist = new JList<String>();
		Vector<String> data = new Vector<String>();
		for (int i = 0; i < obs.getDiv().getNbGroupe()
				+ obs.getDiv().getNbClub(); i++) {
			data.add(" " + (double) Math.round(Math.random() * 50 * 10) / 10);
		}
		listeDist.setListData(data);
		listeDist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeDist.setEnabled(false);
		ControleJListDistance cjlist = new ControleJListDistance(obs, listeDist);
		this.obs.addObserver(cjlist);
		return new JScrollPane(listeDist);
	}
}
