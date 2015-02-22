package model;

/**
 * Donne la position d'une lettre dans l'alphabet (et inversement)
 * 
 * @authors Timothé Barbe, Florent Euvrard, Cheikh Sylla
 *
 */
public class EquivalentLettre {

	public static String[] lettres = new String[] { "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD",
			"AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO",
			"AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ" };

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