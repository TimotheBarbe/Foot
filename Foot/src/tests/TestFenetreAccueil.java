package tests;

import java.io.File;
import java.util.ArrayList;

import model.Club;
import model.Division;
import model.Obs;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import presentation.FenetreAccueil;
import presentation.MainWindows;
import solveur.SolutionInitiale;
import Excel.UtilsExcelPOI;

public class TestFenetreAccueil {

	private static Workbook fichierGPSEquipes;
	private static Workbook fichierDistances;
	private static Workbook fichierDivision;
	private static String[][] matriceDistances;

	// Cette methode permet d'obtenir les numeros d'affiliation de tous les
	// clubs.
	public static ArrayList<Integer> getNumerosAffiliation() {
		ArrayList<Integer> numerosDAffiliation = new ArrayList<Integer>();
		try {
			// Recuperation du classeur Excel (en lecture)
			fichierDistances = WorkbookFactory.create(new File("Donnees/DistancesClubs.xlsx"));

			// On recupere les numeros d'affiliation des clubs.
			// NB : Le meme club est au meme index sur la ligne que sur la
			// colonne.
			ArrayList<String> affiliationClubs = UtilsExcelPOI.getColumn(0, fichierDistances);
			for (String s : affiliationClubs) {
				numerosDAffiliation.add((int) Double.parseDouble(s));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return numerosDAffiliation;
	}

	// Cette methode donne tous les clubs presents dans le fichier de la
	// division
	public static int[] getClubsDivision() {

		// On recupere les numeros d'affiliation des clubs de la division
		ArrayList<String> affiliationDivision = UtilsExcelPOI.getColumn(0, fichierDivision);
		// Nombre de clubs = taille du fichier - 1 (ligne contenant le nom de la
		// division)
		int nbClub = affiliationDivision.size() - 1;

		int[] clubs = new int[nbClub];
		for (int i = 1; i < affiliationDivision.size(); i++) {
			clubs[i - 1] = (int) Double.parseDouble(affiliationDivision.get(i));
		}

		return clubs;
	}

	// Cette methode donne la distance d'un club a un autre
	public static double getDistance(int clubA, int clubB, ArrayList<Integer> affiliation) {
		double distance = 0;
		if (clubA == clubB) {
			return 0;
		} else {
			int a = 0;
			int b = 0;
			for (int i = 0; i < affiliation.size(); i++) {
				if (clubA == affiliation.get(i)) {
					a = i;
				}
				if (clubB == affiliation.get(i)) {
					b = i;
				}
			}
			if (a > b) {
				distance = Double.parseDouble(matriceDistances[a][b]);
			} else {
				distance = Double.parseDouble(matriceDistances[b][a]);
			}

			return distance;
		}

	}

	// Cette methode retourne les 3 infos d'un club de par son numero
	// d'affiliation
	// Ces infos sont retournees sous forme de tableau
	// Index 0 --> Nom du club
	// Index 1 --> Latitude du club
	// Index 2 --> Longitude du club
	public static String[] getInfosClubByNumber(int numeroAffiliation) {
		String[] infos = new String[3];

		try {
			// Recuperation du classeur Excel (en lecture)
			fichierGPSEquipes = WorkbookFactory.create(new File("Donnees/CoordonneesGPSEquipes.xls"));

			// On recupere les numeros d'affiliation des clubs
			ArrayList<String> affiliationClubs = UtilsExcelPOI.getColumn(0, fichierGPSEquipes);

			// On recupere le nom des clubs
			ArrayList<String> nomClubs = UtilsExcelPOI.getColumn(1, fichierGPSEquipes);

			// On recupere la latitude des clubs
			ArrayList<String> latitudeClubs = UtilsExcelPOI.getColumn(2, fichierGPSEquipes);

			// On recupere la longitude des clubs
			ArrayList<String> longitudeClubs = UtilsExcelPOI.getColumn(3, fichierGPSEquipes);

			for (int i = 1; i < affiliationClubs.size(); i++) {
				if (numeroAffiliation == (int) Double.parseDouble(affiliationClubs.get(i))) {
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

	public static void main(String[] args) {
		FenetreAccueil fa = new FenetreAccueil();

		// Boucle permettant d'attendre la fin de la saisie des parametres
		while (fa.isAccueilOuvert()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// On ferme la fenetre d'accueil
		fa.setVisible(false);

		// On commence le traitement de la division
		try {
			// Recuperation du fichier de la division
			fichierDivision = WorkbookFactory.create(new File(fa.getCheminFichierDivision()));

			// Recuperation du classeur Excel (en lecture)
			fichierDistances = WorkbookFactory.create(new File("Donnees/DistancesClubs.xlsx"));
			// Recuperation matrice distances
			matriceDistances = UtilsExcelPOI.getMatrice(fichierDistances);

			// On recupere les numeros d'affiliation des clubs de la division
			ArrayList<String> affiliationDivision = UtilsExcelPOI.getColumn(0, fichierDivision);

			// Nombre de clubs = taille du fichier - 1 (ligne contenant le nom
			// de la division)
			int nbClub = affiliationDivision.size() - 1;
			// Nombre de groupe
			int nbGroupe = Integer.parseInt(fa.getNbGroupes());
			// On met a jour le nom de la division
			String nomDivision = affiliationDivision.get(0);

			Division d = new Division(nbGroupe, nomDivision);

			int clubCourant = 0;
			String[] infosClub = new String[3];
			int[] clubs = new int[nbClub];
			for (int i = 1; i <= nbClub; i++) {
				// Recuperation du numero d'affiliation du club courant
				clubCourant = (int) Double.parseDouble(affiliationDivision.get(i));
				clubs[i - 1] = clubCourant;

				// Recuperation des infos du club
				infosClub = getInfosClubByNumber(clubCourant);

				double[] coordonneesGPS = { Double.parseDouble(infosClub[1]), Double.parseDouble(infosClub[2]) };

				Club c = new Club(infosClub[0], clubCourant, coordonneesGPS);

				d.addClub(c);
			}

			ArrayList<Integer> affiliation = getNumerosAffiliation();
			double[][] tabDist = new double[nbClub][nbClub];
			clubCourant = 0;
			int[] affiliationClubs = getClubsDivision();
			for (int i = 0; i < nbClub; i++) {
				for (int j = i + 1; j < nbClub; j++) {
					tabDist[i][j] = getDistance(affiliationClubs[i], affiliationClubs[j], affiliation);
					tabDist[j][i] = tabDist[i][j];
				}
			}

			SolutionInitiale si = new SolutionInitiale(nbGroupe, tabDist, nbClub);
			Obs obs = new Obs(d, si.getSolution(), tabDist);
			MainWindows test = new MainWindows(obs, nomDivision);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
