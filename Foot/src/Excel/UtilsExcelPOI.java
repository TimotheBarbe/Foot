package Excel;

import java.io.File;
import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;

public class UtilsExcelPOI {

	private static Workbook workbook;


	// POI ne fournit pas d'acces a une colonne, il n'y a d'acces que pour les lignes
	// Cette methode permet alors d'acceder a une colonne d'index indexColonne
	public static ArrayList<String> getColumn(int indexColonne, Workbook workbook){
		ArrayList<String> colonne = new ArrayList<String>();
		Sheet sheet = workbook.getSheetAt(0);
		
		for(Row r : sheet) {
			Cell c = r.getCell(indexColonne);
			if(c != null) {
				colonne.add(c.toString());
			} else {
				colonne.add("-1");
			}
		}

		return colonne;
	}

	// POI ne fournit pas de methode donnant le nombre de colonnes
	// Cette methode permet de recuperer le nombre de colonnes d'une feuille
	public static int getNbColumns(Sheet sheet){
		Row r = sheet.getRow(0);
		return r.getLastCellNum();
	}
	
	// POI ne fournit pas de methode permettnt de recuperer la matrice entiere
	// Cette methode permet de recuperer la matrice entiere d'une feuille
	public static String[][] getMatrice(Workbook workbook){
		Sheet sheet = workbook.getSheetAt(0);
		int taille = getNbColumns(sheet);
		String[][] matrice = new String[taille][taille];
		
		return matrice;
	}

	// POI ne fournit pas d'acces a une cellule de par sa ligne et sa colonne
	// Cette methode permet alors d'acceder a la cellule de la ligne "ligne"
	// et de la colonne "colonne"
	public static String getCell(int ligne, int colonne, Workbook workbook){
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
