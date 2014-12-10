package tests;

import model.Desiderata;
import model.GenerateDistances;
import solveur.Recherche;

public class TestRecherche {

	public static void main(String[] args) {
		int[][] tab = new int[][] { { 0, 20, 1, 5 }, { 20, 0, 10, 3 },
				{ 1, 10, 0, 1 }, { 5, 3, 1, 0 } };

		int[][] tab_moyen = new int[][] { { 0, 10, 5, 3, 5, 3, 20, 6, 9, 22 },
				{ 10, 0, 6, 3, 1, 20, 6, 8, 9, 30 },
				{ 5, 6, 0, 7, 10, 20, 1, 5, 8, 9 },
				{ 3, 3, 7, 0, 12, 2, 2, 5, 12, 10 },
				{ 5, 1, 10, 12, 0, 3, 8, 4, 2, 1 },
				{ 3, 20, 20, 2, 3, 0, 3, 2, 6, 10 },
				{ 20, 6, 1, 2, 8, 2, 0, 10, 1, 1 },
				{ 6, 8, 5, 5, 4, 2, 10, 0, 3, 12 },
				{ 9, 9, 8, 12, 2, 6, 1, 3, 0, 5 },
				{ 22, 30, 9, 10, 1, 10, 1, 12, 5, 0 } };

		Desiderata d1 = new Desiderata(0, 1, "=");
		Desiderata[] tabDesiderata = new Desiderata[] { d1 };

		int[][] tab_enorme = GenerateDistances.genererTableau(150, 100);

		long millis = System.currentTimeMillis();

		int nbg = 15;
		Recherche r = new Recherche(nbg, tab_enorme, tabDesiderata, 6 * 60000,
				125000, true);
		r.execute();

		System.out.println("temps : "+(System.currentTimeMillis() - millis) / 1000.0
				+ " s");
	}
}
