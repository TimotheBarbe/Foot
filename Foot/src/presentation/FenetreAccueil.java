package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import controle.ControleBoutonImportExcel;

/**
 * Fenetre d'acceil du logiciel
 * 
 * @authors Timothé Barbe, Florent Euvrard, Cheikh Sylla
 *
 */
public class FenetreAccueil extends JFrame {

	private static final long serialVersionUID = 1L;

	private Workbook workbook;
	private boolean accueilOuvert = true;

	private String cheminFichierDivision = "";
	private String nomDivision;
	private String nbGroupes;

	public FenetreAccueil() {
		this.setTitle("District de Loire Atlantique");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());

		this.creerCentre();

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void creerCentre() {
		// NORD
		JPanel nord = new JPanel(new BorderLayout());

		JPanel haut = new JPanel();
		haut.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		BoxLayout layoutHaut = new BoxLayout(haut, BoxLayout.X_AXIS);
		haut.setLayout(layoutHaut);
		JLabel division = new JLabel("Division : ");
		JTextField cheminDivision = new JTextField();
		cheminDivision.setPreferredSize(new Dimension(200, 30));
		JButton importFichier = new JButton(new ImageIcon("Donnees/load.png"));
		importFichier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser dialogue = new JFileChooser(new File("."));
				FileFilter filter = new FileNameExtensionFilter("xls, xlsx",
						new String[] { "xls", "xlsx" });
				dialogue.setFileFilter(filter);
				dialogue.setFileSelectionMode(JFileChooser.FILES_ONLY);
				PrintWriter sortie;
				File fichier;

				if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					fichier = dialogue.getSelectedFile();
					cheminDivision.setText(fichier.getPath());
					cheminFichierDivision = fichier.getPath();
					try {
						sortie = new PrintWriter(new FileWriter(fichier
								.getPath(), true));
						sortie.close();
					} catch (Exception e) {

					}
				}
			}
		});
		JButton aideFichierDivision = new JButton("?");
		aideFichierDivision.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BoiteDeDialogue
						.info("Ce champ définit l'adresse du fichier de la division. Le fichier "
								+ "doit impérativement \n avoir l'extension \".xls\" ou l'extension \".xlsx\".");
			}
		});

		haut.add(division);
		haut.add(cheminDivision);
		haut.add(importFichier);
		haut.add(aideFichierDivision);
		nord.add(haut, BorderLayout.NORTH);

		JPanel centreDuNord = new JPanel(new BorderLayout());
		centreDuNord.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		JLabel nbGroupes = new JLabel("Nombre de groupes : ");
		JTextField texteNbGroupes = new JTextField();
		JButton aideNbGroupes = new JButton("?");
		aideNbGroupes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BoiteDeDialogue
						.info("Ce champ définit le nombre de groupes souhaité dans la"
								+ " répartition des clubs. \n"
								+ " Il doit être de type entier et ne doit pas contenir d'espace.");
			}
		});

		centreDuNord.add(nbGroupes, BorderLayout.WEST);
		centreDuNord.add(texteNbGroupes, BorderLayout.CENTER);
		centreDuNord.add(aideNbGroupes, BorderLayout.EAST);
		nord.add(centreDuNord, BorderLayout.SOUTH);

		// CENTRE
		JPanel centre = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton validationCreer = new JButton("Créer");
		validationCreer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					FenetreAccueil.this.setCheminFichierDivision(cheminDivision
							.getText());
					CharSequence xls = ".xls";
					if (!cheminFichierDivision.contains(xls)) {
						BoiteDeDialogue.error("Le fichier \""
								+ cheminFichierDivision
								+ "\" n'a pas l'extension xls ou xlsx.");
					} else {
						setWorkbook(WorkbookFactory.create(new File(
								cheminFichierDivision)));

						try {
							FenetreAccueil.this.setNbGroupes(texteNbGroupes
									.getText());
							int nbGroupes = Integer.parseInt(getNbGroupes());
							if (nbGroupes <= 0) {
								BoiteDeDialogue
										.error("Le nombre de groupes ne peut pas être négatif ou nul.");
							} else {
								accueilOuvert = false;
							}

						} catch (Exception e) {
							BoiteDeDialogue
									.error("Le format du nombre de groupes est incorrect");
						}
					}

				} catch (Exception e) {
					BoiteDeDialogue.error("Le fichier \""
							+ cheminFichierDivision + " est introuvable");
				}
			}
		});

		centre.add(validationCreer);

		// SUD
		JPanel sud = new JPanel();
		BoxLayout layoutSud = new BoxLayout(sud, BoxLayout.X_AXIS);
		sud.setLayout(layoutSud);
		sud.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		JLabel charger = new JLabel("ou : ");
		JButton importsolution = new JButton("Importer une solution",
				new ImageIcon("Donnees/icone_excel.png"));
		importsolution.addActionListener(new ControleBoutonImportExcel(this));

		sud.add(charger);
		sud.add(importsolution);
		
		this.getContentPane().add(nord, BorderLayout.NORTH);
		this.getContentPane().add(centre, BorderLayout.CENTER);
		this.getContentPane().add(sud, BorderLayout.SOUTH);
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

	public String getNomDivision() {
		return nomDivision;
	}

	public void setNomDivision(String nomDivision) {
		this.nomDivision = nomDivision;
	}

}
