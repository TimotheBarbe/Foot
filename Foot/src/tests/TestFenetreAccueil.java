package tests;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import model.Club;
import model.Division;
import model.Obs;
import presentation.FenetreAccueil;
import presentation.MainWindows;

public class TestFenetreAccueil {
	
	private static Workbook baseDeDonnees = null;
	private static Workbook fichierDivision = null;

	public static int getNumeroLastLigne(int sheet, int colonne) {
		return baseDeDonnees.getSheet(sheet).getColumn(colonne).length;
	}

	public static int getNumeroLastColonne(int sheet, int ligne) {
		return baseDeDonnees.getSheet(sheet).getRow(ligne).length;
	}

	public static String[][] getAllTab(int sheet) {
		String[][] tab = new String[getNumeroLastLigne(sheet, 0)][getNumeroLastColonne(
				sheet, 0)];
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				tab[i][j] = baseDeDonnees.getSheet(sheet).getCell(i, j)
						.getContents();
			}
		}
		return tab;

	}

	public static String toString(String[][] tab) {
		String s = "";
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				s += tab[i][j] + " ";
			}
			s += "\n";
		}
		return s;
	}

	// Cette methode retourne les 3 infos d'un club de par son numero d'affiliation
	// Ces infos sont retournees sous forme de tableau
	// Index 0 --> Nom du club
	// Index 1 --> Latitude du club
	// Index 2 --> Longitude du club
	public static String[] getInfosClubByNumber(int numeroAffiliation){
		String[] infos = new String[3];
		
		try {
			// Recuperation du classeur Excel (en lecture)
			baseDeDonnees = Workbook.getWorkbook(new File("Donnees/CoordonneesGPSEquipes.xls"));

			// On prend la premiere feuille du fichier qui contient les coordonnees
			Sheet sheetBaseDeDonnees = baseDeDonnees.getSheet(0);

			// On recupere les numeros d'affiliation des clubs
			Cell[] affiliationClubs = sheetBaseDeDonnees.getColumn(0);

			// On recupere le nom des clubs
			Cell[] nomClubs = sheetBaseDeDonnees.getColumn(1);

			// On recupere la latitude des clubs
			Cell[] latitudeClubs = sheetBaseDeDonnees.getColumn(2);

			// On recupere la longitude des clubs
			Cell[] longitudeClubs = sheetBaseDeDonnees.getColumn(3);

			for(int i=1; i<affiliationClubs.length; i++){
				if(numeroAffiliation==Integer.parseInt(affiliationClubs[i].getContents())){
					infos[0] = nomClubs[i].getContents();
					infos[1] = latitudeClubs[i].getContents();
					infos[2] = longitudeClubs[i].getContents();
					break;
				}
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (baseDeDonnees != null) {
				/* On ferme le worbook pour liberer la memoire */
				baseDeDonnees.close();
			}
		}
		return infos;
	}
	
	public static void main(String[] args) {
		FenetreAccueil fa = new FenetreAccueil();
		
		// Boucle permettant d'attendre la fin de la saisie des parametres
		while(fa.isAccueilOuvert()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// On ferme la fenetre d'accueil
		fa.setVisible(false);
		
		// On commence le traitement de la division
		try {
			// Recuperation du fichier de la division
			fichierDivision = Workbook.getWorkbook(new File(fa.getCheminFichierDivision()));

			// On recupere egalement la feuille du fichier de la division
			Sheet sheetDivision = fichierDivision.getSheet(0);
			
			// On recupere les numeros d'affiliation des clubs de la division
			Cell[] affiliationDivision = sheetDivision.getColumn(0);

			// Nombre de clubs = taille du fichier - 1 (ligne contenant le nom de la division)
			int nbClub = affiliationDivision.length-1;
			// Nombre de groupe
			int nbGroupe = Integer.parseInt(fa.getNbGroupes());
			
			Division d = new Division(nbGroupe);
			int[] reponseSolveur = new int[nbClub];

			int clubCourant = 0;
			String[] infosClub = new String[3];
			for (int i = 1; i <= nbClub; i++) {
				// Recuperation du numero d'affiliation du club courant
				clubCourant = Integer.parseInt(affiliationDivision[i].getContents());
				
				// Recuperation des infos du club
				infosClub = getInfosClubByNumber(clubCourant);
				
				double[] coordonneesGPS = {Double.parseDouble(infosClub[1]), Double.parseDouble(infosClub[2])};

				Club c = new Club(infosClub[0], clubCourant, coordonneesGPS);

				d.addClub(c);
				reponseSolveur[i-1] = (int) (nbGroupe * Math.random());
			}
			
			int[][] tabDist = new int[nbClub][nbClub];
			for (int[] row : tabDist)
				Arrays.fill(row, (int) (Math.random() * 12) + 1);
			for (int i = 0; i < nbClub; i++) {
				tabDist[i][i] = 0;
			}
			Obs obs = new Obs(d, reponseSolveur, tabDist);

			MainWindows test = new MainWindows(obs, affiliationDivision[0].getContents());

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (baseDeDonnees != null) {
				/* On ferme le worbook pour liberer la memoire */
				baseDeDonnees.close();
			}
		}
	}
	
}
