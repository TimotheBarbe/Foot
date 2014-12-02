package tests;

import java.util.ArrayList;

import model.Club;
import model.Division;
import model.Ville;
import abstraction.MainWindows;

public class TestIHM {

	public static void main(String[] args) {

		Ville v1 = new Ville(0, "Nantes", 200, 300);
		Ville v2 = new Ville(1, "Paris", 400, 250);
		Ville v3 = new Ville(1, "Lyon", 250, 250);
		Ville v4 = new Ville(1, "Marseilles", 600, 75);

		Club c1 = new Club("FCN", 0, v1);
		Club c2 = new Club("PSG", 1, v2);
		Club c3 = new Club("Olympique lyonnais", 2, v3);
		Club c4 = new Club("OM", 3, v4);
		
		Division d = new Division(2);
		d.addClub(c1);
		d.addClub(c2);
		d.addClub(c3);
		d.addClub(c4);

		MainWindows test = new MainWindows(d);
	}
}
