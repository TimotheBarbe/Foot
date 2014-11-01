package tests;

import model.Club;
import model.Division;
import model.Ville;

public class Test1 {

	public static void main(String[] args) {
		Ville v1 = new Ville(0, "Nantes", 5, 5);
		Ville v2 = new Ville(1, "Paris", 2, 5.5);
		
		Club c1 = new Club("FCN", 0, v1);
		Club c2 = new Club("PSG", 1, v2);
		Club c3 = new Club("RCN", 2, v1);
		
		Division d = new Division(2);
		d.addClub(c1);
		d.addClub(c2);
		d.addClub(c3);
		
		System.out.println(d);
		System.out.println("Club d'id 1 : "+ d.getClubById(1));
		
		d.removeClub(1);
		System.out.println(d);
		
	}

}
