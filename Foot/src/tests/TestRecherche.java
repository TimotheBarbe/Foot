package tests;

import solveur.Recherche;

public class TestRecherche {

	public static void main(String[] args) {
		int nbg = 2;
		double[][] tab = new double[][] { { 0, 2, 1, 5 }, { 2, 0, 10, 3 },
				{ 1, 10, 0, 1 }, { 5, 3, 1, 0 } };

		Recherche r = new Recherche(nbg, tab);
		r.lancer();
		System.out.println(r.toString());
	}
}
