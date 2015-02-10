package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
		JButton aideNbGroupes = new JButton("?");
		aideNbGroupes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Ce champ définit le nombre de groupes souhaité dans la"
						+ " répartition des clubs. \n"
						+ " Il doit être de type entier et ne doit pas contenir d'espace.");
			}
	     });

		haut.add(nbGroupes, BorderLayout.WEST);
		haut.add(texteNbGroupes, BorderLayout.CENTER);
		haut.add(aideNbGroupes, BorderLayout.EAST);
		nord.add(haut, BorderLayout.NORTH);
		nord.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JPanel centre = new JPanel();
		BoxLayout layout = new BoxLayout(centre, BoxLayout.X_AXIS);
		centre.setLayout(layout);
		JLabel division = new JLabel("Division : ");
		JTextField cheminDivision = new JTextField();
		JButton importFichier = new JButton("...");
		importFichier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser dialogue = new JFileChooser(new File("."));
				PrintWriter sortie;
				File fichier;
				
				if (dialogue.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
				    fichier = dialogue.getSelectedFile();
				    cheminDivision.setText(fichier.getPath());
				    cheminFichierDivision = fichier.getPath();
				    try{
				    	sortie = new PrintWriter (new FileWriter(fichier.getPath(), true));
					    sortie.close();
				    } catch (Exception e){
				    	
				    }
				}
			}
	     });
		JButton aideFichierDivision = new JButton("?");
		aideFichierDivision.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Ce champ définit l'adresse du fichier de la division. Le fichier "
						+ "doit impérativement \n avoir l'extension \".xls\" et non, en particulier, l'extension \".xlsx\".");
			}
	     });
		
		centre.add(division);
		centre.add(cheminDivision);
		centre.add(importFichier);
		centre.add(aideFichierDivision);
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
						FenetreAccueil.this.setCheminFichierDivision(cheminDivision.getText());
						setWorkbook(Workbook.getWorkbook(new File(cheminFichierDivision)));
						
						accueilOuvert = false;
					} catch (Exception e){
						JOptionPane.showMessageDialog(null, "Le fichier \""+cheminFichierDivision+
								"\" est introuvable", "Alerte", JOptionPane.ERROR_MESSAGE);
					}
					
				} catch (Exception e){
					JOptionPane.showMessageDialog(null, "Le format du nombre de groupes est incorrect", "Alerte",
							JOptionPane.ERROR_MESSAGE);
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
