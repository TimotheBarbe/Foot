package abstraction;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import model.Club;

public class MainWindows extends JFrame {

	private static final long serialVersionUID = 1L;
	private static int LABEL_SIZE = 12;
	private ArrayList<Club> listeClub;

	public MainWindows(ArrayList<Club> listeClub) {
		// this.setSize(800, 600);
		this.setTitle("Foot");
		this.listeClub = listeClub;
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

		panelCarte.add(new AfficheImage("C:\\carte_region.jpg", listeClub),
				BorderLayout.CENTER);
		this.getContentPane().add(panelCarte, BorderLayout.CENTER);
	}

	private void creerGauche() {
		JPanel panelListe = new JPanel(new BorderLayout());
		panelListe.setBorder(BorderFactory.createEmptyBorder(6, 6, 3, 3));
		panelListe.setPreferredSize(new Dimension(200, 600));
		
		JLabel labelClub = new JLabel("Clubs");
		labelClub.setFont(new Font(labelClub.getFont().getName(), Font.BOLD,
				LABEL_SIZE));
		panelListe.add(labelClub, BorderLayout.NORTH);

		JList<String> listeClub = new JList<String>();
		Vector<String> data = new Vector<String>();
		for (Club i : this.listeClub) {
			data.add(i.toString());
		}
		data.add(0, "-");
		listeClub.setListData(data);
		listeClub.setSelectedIndex(0);
		listeClub.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelListe.add(listeClub);
		
		this.getContentPane().add(panelListe, BorderLayout.WEST);
	}

}
