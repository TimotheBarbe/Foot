package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FenetreAccueil extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String cheminFichierDivision ="";
	private int nbGoupes;
	
	public FenetreAccueil(){
		this.setPreferredSize(new Dimension(400, 150));
		this.setTitle("District de Loire Atlantique");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		
		this.creerCentre();
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void creerCentre(){
		JPanel nord = new JPanel(new BorderLayout());
		
		JPanel haut = new JPanel(new BorderLayout());
		JLabel nbGroupes = new JLabel("Nombre de groupes : ");
		JTextField texteNbGroupes = new JTextField();
		haut.add(nbGroupes, BorderLayout.WEST);
		haut.add(texteNbGroupes, BorderLayout.CENTER);
		nord.add(haut, BorderLayout.NORTH);
		nord.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JPanel centre = new JPanel(new BorderLayout());
		JLabel division = new JLabel("Division : ");
		JTextField cheminDivision = new JTextField();
		JButton importer = new JButton("Charger...");
		centre.add(division, BorderLayout.WEST);
		centre.add(cheminDivision, BorderLayout.CENTER);
		centre.add(importer, BorderLayout.EAST);
		nord.add(centre, BorderLayout.SOUTH);
		
		JPanel sud = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton continuer = new JButton("Continuer");
		sud.add(continuer);
		
		this.getContentPane().add(nord, BorderLayout.NORTH);
		this.getContentPane().add(sud, BorderLayout.CENTER);
	}
	
	public String getCheminFichierDivision() {
		return cheminFichierDivision;
	}
	public void setCheminFichierDivision(String cheminFichierDivision) {
		this.cheminFichierDivision = cheminFichierDivision;
	}
	public int getNbGoupes() {
		return nbGoupes;
	}
	public void setNbGoupes(int nbGoupes) {
		this.nbGoupes = nbGoupes;
	}
}
