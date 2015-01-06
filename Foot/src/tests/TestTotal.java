package tests;

import java.awt.Point;
import java.util.Random;

import model.Club;
import model.Desiderata;
import model.Division;
import model.Obs;
import presentation.MainWindows;
import solveur.Recherche;

public class TestTotal {

	public static void main(String[] args) {
		int nbClub = 100;
		int nbGroupe = 10;
		Division d = new Division(nbGroupe);
		Point[] tabPoint = new Point[nbClub];
		Random rdm = new Random(0);
		for (int i = 0; i < nbClub; i++) {
			tabPoint[i] = new Point((int) (50 + rdm.nextInt(700)), (int) (50 + rdm.nextInt(500)));

			double[] coordonneesGPS = { tabPoint[i].getX(), tabPoint[i].getY() };

			Club c = new Club("Club " + i, i, coordonneesGPS);
			d.addClub(c);
		}
		int[][] tabDist = new int[nbClub][nbClub];
		for (int i = 0; i < nbClub; i++) {
			for (int j = 0; j < nbClub; j++) {
				tabDist[i][j] = (int) (tabPoint[i].distance(tabPoint[j]));
			}
		}

		Desiderata[] tabDesiderata = new Desiderata[0];

		Recherche r = new Recherche(nbGroupe, tabDist, tabDesiderata, 6 * 60000, 300000, true);
		r.execute();
		int[] tabSolution = r.getTabSolution();
		Obs obs = new Obs(d, tabSolution);

		MainWindows test = new MainWindows(obs);
	}
}
