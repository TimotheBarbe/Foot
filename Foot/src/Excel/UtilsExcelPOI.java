package Excel;

import java.io.File;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.*;

public class UtilsExcelPOI {

	private static Workbook workbook;
	private static String[][] matriceDistances;
	private static Workbook fichierDistances;

	// POI ne fournit pas d'acces a une colonne, il n'y a d'acces que pour les
	// lignes
	// Cette methode permet alors d'acceder a une colonne d'index indexColonne
	public static ArrayList<String> getColumn(int indexColonne,
			Workbook workbook) {
		ArrayList<String> colonne = new ArrayList<String>();
		Sheet sheet = workbook.getSheetAt(0);

		for (Row r : sheet) {
			Cell c = r.getCell(indexColonne);
			if (c != null) {
				colonne.add(c.toString());
			} else {
				colonne.add("-1");
			}
		}

		return colonne;
	}

	public static ArrayList<String> getAncienneColumn(int indexColonne,
			Workbook workbook) {
		ArrayList<String> colonne = new ArrayList<String>();
		Sheet sheet = workbook.getSheetAt(0);

		for (Row r : sheet) {
			Cell c = r.getCell(indexColonne);
			if (c != null && !c.toString().equals("")) {
				colonne.add(c.toString());
			}
		}

		return colonne;
	}

	// POI ne fournit pas de methode donnant le nombre de colonnes
	// Cette methode permet de recuperer le nombre de colonnes d'une feuille
	public static int getNbColumns(Sheet sheet) {
		Row r = sheet.getRow(0);
		return r.getLastCellNum();
	}

	// POI ne fournit pas de methode permettnt de recuperer la matrice entiere
	// Cette methode permet de recuperer la matrice entiere d'une feuille
	public static String[][] getMatrice(Workbook workbook) {
		Sheet sheet = workbook.getSheetAt(0);
		int nbColonnes = getNbColumns(sheet);
		String[][] matrice = new String[nbColonnes][nbColonnes];

		int i=0;
		for (Row r : sheet) {
			if(i<nbColonnes){
				for(int j=0; j<nbColonnes; j++){
					Cell c = r.getCell(j);
					if (c != null) {
						matrice[i][j] = c.toString();
					} else {
						matrice[i][j] = "-1";
					}
				}
				i++;
			} else {
				break;
			}
		}

		return matrice;
	}

	// Cette methode donne la matrice extraite des distances
	public static double[][] getMatriceExtraite(ArrayList<Integer> clubs){
		double[][] matriceExtraite = new double[clubs.size()][clubs.size()];

		try{
			fichierDistances = WorkbookFactory.create(new File("Donnees/DistancesClubs.xlsx"));
			ArrayList<Integer> affiliation = getNumerosAffiliation();
			// Recuperation matrice distances
			matriceDistances = getMatrice(fichierDistances);

			for(int i=0; i<clubs.size(); i++){
				for(int j=i+1; j<clubs.size(); j++){
					matriceExtraite[i][j] = getDistance(clubs.get(i), clubs.get(j),
							affiliation, matriceDistances);
					matriceExtraite[i][j] = matriceExtraite[j][i];
				}
			}
			
		} catch(Exception e){

		}


		return matriceExtraite;
	}

	// Cette methode donne la distance d'un club a un autre
	public static double getDistance(int clubA, int clubB,
			ArrayList<Integer> affiliation, String[][] matriceDistances) {
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

		} 
		return distance;
	}


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

	// POI ne fournit pas d'acces a une cellule de par sa ligne et sa colonne
	// Cette methode permet alors d'acceder a la cellule de la ligne "ligne"
	// et de la colonne "colonne"
	public static String getCell(int ligne, int colonne, Workbook workbook) {
		return getColumn(colonne, workbook).get(ligne);
	}

	public static void main(String[] args) {
		File file = new File("Donnees/FichierDivision.xlsx");

		try {
			workbook = WorkbookFactory.create(file);
			Sheet sheet = workbook.getSheetAt(0);
			// On recupere les numeros d'affiliation des clubs
			ArrayList<String> colonne = getColumn(0, workbook);

			System.out.println(colonne);
			System.out.println(getNbColumns(sheet));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
