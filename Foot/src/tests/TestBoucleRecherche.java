package tests;

import model.Desiderata;
import model.GenerateDistances;
import solveur.BoucleRecherche;

public class TestBoucleRecherche {

	public static void main(String[] args) {
		Desiderata d1 = new Desiderata(0, 1, "!=");
		Desiderata[] tabDesiderata = new Desiderata[] { d1 };

		int[][] tab_enorme = GenerateDistances.genererTableau(100, 20);

		long millis = System.currentTimeMillis();

		int nbg = 10;
		BoucleRecherche r = new BoucleRecherche(nbg, tab_enorme, tabDesiderata, 5 * 60000, 60000,
				true);
		r.run();

		System.out.println("temps : " + (System.currentTimeMillis() - millis)
				/ 1000.0 + " s");
	}
}
