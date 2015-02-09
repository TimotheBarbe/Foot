package tests;

import presentation.FenetreAccueil;

public class TestFenetreAccueil {

	public static void main(String[] args) {
		FenetreAccueil fa = new FenetreAccueil();
		System.out.println(fa.getCheminFichierDivision());
		System.out.println(fa.getNbGroupes());
	}
}
