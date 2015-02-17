package tests;

import java.io.File;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import Excel.UtilsExcelPOI;
import model.Club;
import model.Division;
import model.Obs;
import presentation.FenetreAccueil;
import presentation.MainWindows;
import solveur.SolutionInitiale;

public class TestFenetreAccueil {

	private static Workbook fichierGPSEquipes;
	private static Workbook fichierDistances;
	private static Workbook fichierDivision;

	// Cette methode donne les autres clubs presents dans le fichier de la
	// division a partir d'un club
	public static int[] getOtherClubs(int club){

		// On recupere les numeros d'affiliation des clubs de la division
		ArrayList<String> affiliationDivision = UtilsExcelPOI.getColumn(0, fichierDivision);
		// Nombre de clubs = taille du fichier - 1 (ligne contenant le nom de la division)
		int nbClub = affiliationDivision.size()-1;

		int[] otherClubs = new int[nbClub-1];
		int j = 0;
		for(int i=1; i<affiliationDivision.size(); i++){
			if((int)Double.parseDouble(affiliationDivision.get(i))!=club){
				otherClubs[j] = (int)Double.parseDouble(affiliationDivision.get(i));
				j++;
			}
		}

		return otherClubs;
	}
	// Cette methode donne les distances d'un club par rapport a tous les autres
	public static double[] getDistances(int club, int[] otherClubs){
		double[] distances = new double[otherClubs.length];

		try {
			// Recuperation du classeur Excel (en lecture)
			fichierDistances = WorkbookFactory.create(new File("Donnees/DistancesClubs.xlsx"));

			// On recupere les numeros d'affiliation des clubs. Attention, la premiere case est vide.
			// NB : Le meme club est au meme index sur la ligne que sur la colonne.
			ArrayList<String> affiliationClubs = UtilsExcelPOI.getColumn(0, fichierDistances);

			// On recupere les index associes a chaque club ou numero d'affiliation
			int[] indexAllClubs = new int[otherClubs.length+1];
			int i = 0;
			int tmp = club;
			while(i!=indexAllClubs.length){
				for(int j=0; j<affiliationClubs.size(); j++){
					if(tmp==(int)Double.parseDouble(affiliationClubs.get(j))){
						indexAllClubs[i] = j;
						if(i!=otherClubs.length){
							tmp = otherClubs[i];
							i++;
						} else {
							i++;
						}
						break;
					}
				}
				
			}

			// On remplit les distances
			for(int j=0; j<distances.length; j++){
				distances[j] = Double.parseDouble(UtilsExcelPOI.getCell(indexAllClubs[j+1]+1,
						indexAllClubs[0]+1, fichierDistances));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return distances;
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
			fichierGPSEquipes = WorkbookFactory.create(new File("Donnees/CoordonneesGPSEquipes.xls"));

			// On recupere les numeros d'affiliation des clubs
			ArrayList<String> affiliationClubs = UtilsExcelPOI.getColumn(0, fichierGPSEquipes);

			// On recupere le nom des clubs
			ArrayList<String> nomClubs = UtilsExcelPOI.getColumn(1, fichierGPSEquipes);

			// On recupere la latitude des clubs
			ArrayList<String> latitudeClubs = UtilsExcelPOI.getColumn(2, fichierGPSEquipes);

			// On recupere la longitude des clubs
			ArrayList<String> longitudeClubs = UtilsExcelPOI.getColumn(3, fichierGPSEquipes);

			for(int i=1; i<affiliationClubs.size(); i++){
				if(numeroAffiliation==(int)Double.parseDouble(affiliationClubs.get(i))){
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
		while(fa.isAccueilOuvert()){
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

			// On recupere les numeros d'affiliation des clubs de la division
			ArrayList<String> affiliationDivision = UtilsExcelPOI.getColumn(0, fichierDivision);

			// Nombre de clubs = taille du fichier - 1 (ligne contenant le nom de la division)
			int nbClub = affiliationDivision.size()-1;
			// Nombre de groupe
			int nbGroupe = Integer.parseInt(fa.getNbGroupes());
			// On met a jour le nom de la division
			String nomDivision = affiliationDivision.get(0);

			Division d = new Division(nbGroupe, nomDivision);

			int clubCourant = 0;
			String[] infosClub = new String[3];
			int[] clubs = new int[nbClub];
			for(int i = 1; i <= nbClub; i++) {
				// Recuperation du numero d'affiliation du club courant
				clubCourant = (int)Double.parseDouble(affiliationDivision.get(i));
				clubs[i-1] = clubCourant;

				// Recuperation des infos du club
				infosClub = getInfosClubByNumber(clubCourant);

				double[] coordonneesGPS = {Double.parseDouble(infosClub[1]), Double.parseDouble(infosClub[2])};

				Club c = new Club(infosClub[0], clubCourant, coordonneesGPS);

				d.addClub(c);
			}

			double[][] tabDist = new double[nbClub][nbClub];
			clubCourant = 0;
			int[] otherClubs = new int[nbClub-1];
			double[] distances = new double[nbClub-1];
			int i = 0;
			while(i!=nbClub){
				clubCourant = clubs[i];
				otherClubs = getOtherClubs(clubCourant);
				distances = getDistances(clubCourant, otherClubs);
				int k = 0;
				for(int j=0; j<nbClub-1; j++){
					if(i==j){
						tabDist[i][k] = 0;
					} else {
						tabDist[i][j] = distances[k];
						k++;
					}
				}
				i++;
			}
			
			for(int m=0; m<nbClub; m++){
				for(int p=0; p<nbClub; p++){
					System.out.print(tabDist[m][p]+" ");
				}
				System.out.println();
			}

			SolutionInitiale si = new SolutionInitiale(nbGroupe, tabDist, nbClub);
			Obs obs = new Obs(d, si.getSolution(), tabDist);
			MainWindows test = new MainWindows(obs, nomDivision);

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
	}

}
