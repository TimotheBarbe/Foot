package model;

public class EquivalentLettre {

	public static String[] lettres = new String[] { "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };

	public static String getLettre(int i) {
		return lettres[i];
	}

	public static int getIndice(String lettre) {
		int reponse = -1;
		for (int i = 0; i < lettres.length; i++) {
			if (lettre.equals(lettres[i])) {
				reponse = i;
			}
		}
		return reponse;
	}
}