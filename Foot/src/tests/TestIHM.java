package tests;

import model.Club;
import model.Division;
import model.Obs;
import model.Ville;
import presentation.MainWindows;

public class TestIHM {

	public static void main(String[] args) {
		int nbClub = 100;
		int nbGroupe = 10;
		Division d = new Division(nbGroupe);
		int[] reponseSolveur = new int[nbClub];
		for (int i = 0; i < nbClub; i++) {
			Ville v = new Ville(0, "v1", 50 + Math.random() * 700,
					50 + Math.random() * 500);
			Club c = new Club("Club " + i, i, v);
			d.addClub(c);
			reponseSolveur[i] = (int) (nbGroupe * Math.random());
		}

		Obs obs = new Obs(d, reponseSolveur);

		MainWindows test = new MainWindows(obs);
	}
}
