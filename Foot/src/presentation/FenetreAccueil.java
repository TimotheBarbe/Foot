package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jxl.Workbook;

public class FenetreAccueil extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Workbook workbook;
	private String cheminFichierDivision ="";
	private String nbGroupes;
	private boolean accueilOuvert = true;
	
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
		
		//JButton importer = new JButton("Charger...");
		centre.add(division, BorderLayout.WEST);
		centre.add(cheminDivision, BorderLayout.CENTER);
		//centre.add(importer, BorderLayout.EAST);
		nord.add(centre, BorderLayout.SOUTH);
		
		JPanel sud = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton continuer = new JButton("Continuer");
		continuer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					FenetreAccueil.this.setNbGroupes(texteNbGroupes.getText());
					Integer.parseInt(getNbGroupes());
					
					try{
						FenetreAccueil.this.setCheminFichierDivision("Donnees/"+cheminDivision.getText());
						setWorkbook(Workbook.getWorkbook(new File(cheminFichierDivision)));
						
						accueilOuvert = false;
					} catch (Exception e){
						JOptionPane.showMessageDialog(null, "Le fichier \""+FenetreAccueil.this.getCheminFichierDivision()
								+"\" est introuvable");
					}
					
				} catch (Exception e){
					JOptionPane.showMessageDialog(null, "Le format du nombre de groupes est incorrect");
				}
			}
	     });      
		
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

	public String getNbGroupes() {
		return nbGroupes;
	}

	public void setNbGroupes(String nbGroupes) {
		this.nbGroupes = nbGroupes;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public boolean isAccueilOuvert() {
		return accueilOuvert;
	}

	public void setAccueilOuvert(boolean accueilOuvert) {
		this.accueilOuvert = accueilOuvert;
	}
	
}
