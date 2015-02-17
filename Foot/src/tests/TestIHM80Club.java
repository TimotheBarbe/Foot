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
import presentation.MainWindows;

public class TestIHM80Club {

	private static Workbook workbook;

	public static int getNumeroLastLigne(int sheet, int colonne) {
		return workbook.getSheet(sheet).getColumn(colonne).length;
	}

	public static int getNumeroLastColonne(int sheet, int ligne) {
		return workbook.getSheet(sheet).getRow(ligne).length;
	}

	public static String[][] getAllTab(int sheet) {
		String[][] tab = new String[getNumeroLastLigne(sheet, 0)][getNumeroLastColonne(
				sheet, 0)];
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				tab[i][j] = workbook.getSheet(sheet).getCell(i, j)
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

	public static void main(String[] args) {
		workbook = null;
		try {
			/* Recuperation du classeur Excel (en lecture) */
			workbook = Workbook.getWorkbook(new File(
					"Donnees/CoordonneesGPSEquipes.xls"));

			// On prend la premiere feuille du fichier qui contient les
			// coordonnees
			Sheet sheet = workbook.getSheet(0);

			// On recupere les numeros d'affiliation des clubs
			Cell[] affiliationClubs = sheet.getColumn(0);

			// On recupere le nom des clubs
			Cell[] nomClubs = sheet.getColumn(1);

			// On recupere la latitude des clubs
			Cell[] latitudeClubs = sheet.getColumn(2);

			// On recupere la longitude des clubs
			Cell[] longitudeClubs = sheet.getColumn(3);

			int nbClub = 80;
			int nbGroupe = 10;
			String nomDivision = "Nom de la division";
			
			Division d = new Division(nbGroupe, nomDivision);
			int[] reponseSolveur = new int[nbClub];

			for (int i = 1; i <= nbClub; i++) {
				double[] coordonneesGPS = {
						Double.parseDouble(latitudeClubs[i].getContents()),
						Double.parseDouble(longitudeClubs[i].getContents()) };

				Club c = new Club(nomClubs[i].getContents(),
						Integer.parseInt(affiliationClubs[i].getContents()),
						coordonneesGPS);

				d.addClub(c);
				reponseSolveur[i - 1] = (int) (nbGroupe * Math.random());
			}
			double[][] tabDist = new double[nbClub][nbClub];
			for (double[] row : tabDist)
				Arrays.fill(row, (int) (Math.random() * 12) + 1);
			for (int i = 0; i < nbClub; i++) {
				tabDist[i][i] = 0;
			}
			Obs obs = new Obs(d, reponseSolveur, tabDist);

			MainWindows test = new MainWindows(obs, nomDivision);

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				/* On ferme le worbook pour liberer la memoire */
				workbook.close();
			}
		}
	}
}
