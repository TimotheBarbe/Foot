package tests;

import java.awt.geom.Point2D;
import java.util.Random;

import model.Club;
import model.Desiderata;
import model.Division;
import model.Obs;
import presentation.MainWindows;
import solveur.Recherche;
import solveur.RecuitSimule;
import solveur.SolutionInitiale;

public class TestTotal {

	public static void main(String[] args) {
		int nbClub = 100;
		int nbGroupe = 10;
		String nomDivision = "Nom de la division";
		
		Division d = new Division(nbGroupe, nomDivision);
		Point2D.Double[] tabPoint = new Point2D.Double[nbClub];
		Random rdm = new Random(0);
		for (int i = 0; i < nbClub; i++) {
			tabPoint[i] = new Point2D.Double((47.2 + 0.01 * rdm.nextInt(60)),
					-2.4 + 0.01 * rdm.nextInt(100));

			double[] coordonneesGPS = { tabPoint[i].getX(), tabPoint[i].getY() };

			Club c = new Club("Club " + i, i, coordonneesGPS);
			d.addClub(c);
		}
		double[][] tabDist = new double[nbClub][nbClub];
		for (int i = 0; i < nbClub; i++) {
			for (int j = 0; j < nbClub; j++) {
				tabDist[i][j] = 00 * tabPoint[i].distance(tabPoint[j]);
			}
		}

		Desiderata[] tabDesiderata = new Desiderata[0];

		// Recherche r = new Recherche(nbGroupe, tabDist, tabDesiderata,
		// 6 * 60000, 30000, true);
		// r.execute();
		// int[] tabSolution = r.getTabSolution();
		
		// int nbMinuntes = 1;
		// RecuitSimule RS = new RecuitSimule(nbGroupe, tabDist, null,
		// nbMinuntes * 60000, nbMinuntes * 800, 10);
		// int[] tabSolution = RS.meilleurSolution;

		SolutionInitiale si = new SolutionInitiale(nbGroupe, tabDist, nbClub);
		Obs obs = new Obs(d, si.getSolution(), tabDist);

		MainWindows test = new MainWindows(obs, nomDivision);
	}
}
