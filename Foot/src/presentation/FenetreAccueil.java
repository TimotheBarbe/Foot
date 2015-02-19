package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

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

import model.Club;
import model.Division;
import model.Obs;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import solveur.SolutionInitiale;
import Excel.UtilsExcelPOI;
import controle.ControleBoutonImportExcel;

/**
 * Fenetre d'accueil du logiciel
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

	private static Workbook fichierGPSEquipes;
	private static Workbook fichierDivision;
	private static Workbook fichierDistances;

	public FenetreAccueil() {
		this.setTitle("GéoGroupFoot44");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		this.creerCentreEtNord();
		this.creerSud();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Construit le sud de la frame
	 */
	public void creerSud() {
		JPanel sud = new JPanel();
		BoxLayout layoutSud = new BoxLayout(sud, BoxLayout.X_AXIS);
		sud.setLayout(layoutSud);
		sud.setBorder(BorderFactory.createEmptyBorder(60, 6, 6, 6));
		JLabel charger = new JLabel("ou : ");
		JButton importsolution = new JButton("Importer une solution",
				new ImageIcon("Donnees/icone_excel.png"));
		importsolution.addActionListener(new ControleBoutonImportExcel(this));

		sud.add(charger);
		sud.add(importsolution);
		this.getContentPane().add(sud, BorderLayout.SOUTH);
	}

	/**
	 * Construit le centre et le nord de la frame
	 */
	public void creerCentreEtNord() {
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

		this.getContentPane().add(nord, BorderLayout.NORTH);
		this.getContentPane().add(centre, BorderLayout.CENTER);
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

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public boolean isAccueilOuvert() {
		return accueilOuvert;
	}

	/**
	 * Charge les fichiers excel
	 */
	public void launch() {
		while (this.isAccueilOuvert()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// On ferme la fenetre d'accueil
		this.setVisible(false);

		// On commence le traitement de la division
		try {
			// Recuperation du fichier de la division
			fichierDivision = WorkbookFactory.create(new File(this
					.getCheminFichierDivision()));
			fichierDistances = WorkbookFactory.create(new File(
					"Donnees/DistancesClubs.xlsx"));

			// On recupere les numeros d'affiliation des clubs de la division
			ArrayList<String> affiliationDivision = UtilsExcelPOI.getColumn(0,
					fichierDivision);

			// Nombre de clubs = taille du fichier - 1 (ligne contenant le nom
			// de la division)
			int nbClub = affiliationDivision.size() - 1;
			// Nombre de groupe
			int nbGroupe = Integer.parseInt(this.getNbGroupes());
			// On met a jour le nom de la division
			String nomDivision = affiliationDivision.get(0);

			Division d = new Division(nbGroupe, nomDivision);

			int clubCourant = 0;
			String[] infosClub = new String[3];
			int[] clubs = new int[nbClub];
			for (int i = 1; i <= nbClub; i++) {
				// Recuperation du numero d'affiliation du club courant
				clubCourant = (int) Double.parseDouble(affiliationDivision
						.get(i));
				clubs[i - 1] = clubCourant;

				// Recuperation des infos du club
				infosClub = getInfosClubByNumber(clubCourant);

				double[] coordonneesGPS = { Double.parseDouble(infosClub[1]),
						Double.parseDouble(infosClub[2]) };

				Club c = new Club(infosClub[0], clubCourant, coordonneesGPS);

				d.addClub(c);
			}

			ArrayList<Integer> affiliation = UtilsExcelPOI
					.getNumerosAffiliation();
			double[][] tabDist = new double[nbClub][nbClub];
			clubCourant = 0;
			int[] affiliationClubs = getClubsDivision();
			String[][] matriceDistances = UtilsExcelPOI
					.getMatrice(fichierDistances);

			for (int i = 0; i < nbClub; i++) {
				for (int j = i + 1; j < nbClub; j++) {
					tabDist[i][j] = UtilsExcelPOI.getDistance(
							affiliationClubs[i], affiliationClubs[j],
							affiliation, matriceDistances);
					tabDist[j][i] = tabDist[i][j];
				}
			}

			SolutionInitiale si = new SolutionInitiale(nbGroupe, tabDist,
					nbClub);
			Obs obs = new Obs(d, si.getSolution(), tabDist);
			MainWindows test = new MainWindows(obs, nomDivision);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int[] getClubsDivision() {

		// On recupere les numeros d'affiliation des clubs de la division
		ArrayList<String> affiliationDivision = UtilsExcelPOI.getColumn(0,
				fichierDivision);
		// Nombre de clubs = taille du fichier - 1 (ligne contenant le nom de la
		// division)
		int nbClub = affiliationDivision.size() - 1;

		int[] clubs = new int[nbClub];
		for (int i = 1; i < affiliationDivision.size(); i++) {
			clubs[i - 1] = (int) Double.parseDouble(affiliationDivision.get(i));
		}

		return clubs;
	}

	/**
	 * Cette methode retourne les 3 infos d'un club de par son numero
	 * d'affiliation Ces infos sont retournees sous forme de tableau : Index 0
	 * --> Nom du club ; Index 1 --> Latitude du club ; Index 2 --> Longitude du
	 * club
	 * 
	 * @param numeroAffiliation
	 * @return
	 */
	public static String[] getInfosClubByNumber(int numeroAffiliation) {
		String[] infos = new String[3];

		try {
			// Recuperation du classeur Excel (en lecture)
			fichierGPSEquipes = WorkbookFactory.create(new File(
					"Donnees/CoordonneesGPSEquipes.xls"));

			// On recupere les numeros d'affiliation des clubs
			ArrayList<String> affiliationClubs = UtilsExcelPOI.getColumn(0,
					fichierGPSEquipes);

			// On recupere le nom des clubs
			ArrayList<String> nomClubs = UtilsExcelPOI.getColumn(1,
					fichierGPSEquipes);

			// On recupere la latitude des clubs
			ArrayList<String> latitudeClubs = UtilsExcelPOI.getColumn(2,
					fichierGPSEquipes);

			// On recupere la longitude des clubs
			ArrayList<String> longitudeClubs = UtilsExcelPOI.getColumn(3,
					fichierGPSEquipes);

			for (int i = 1; i < affiliationClubs.size(); i++) {
				if (numeroAffiliation == (int) Double
						.parseDouble(affiliationClubs.get(i))) {
					infos[0] = nomClubs.get(i);
					infos[1] = latitudeClubs.get(i);
					infos[2] = longitudeClubs.get(i);
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return infos;
	}

}
