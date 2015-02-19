package model;

import java.util.ArrayList;

public class Desiderata {

	private ArrayList<Club> listeClub;
	private int club1;
	private int club2;
	private String op;

	public Desiderata(int club1, int club2, String op, ArrayList<Club> listeClub)
			throws IllegalArgumentException {
		this.op = op;
		this.listeClub = listeClub;
		this.setClub1(club1);
		this.setClub2(club2);
	}

	public int getClub1() {
		return club1;
	}

	public void setClub1(int club1) throws IllegalArgumentException {
		boolean ok = false;
		for (Club c : this.listeClub) {
			if (c.getId() == club1) {
				this.club1 = club1;
				ok = true;
			}
		}
		if (!ok) {
			throw new IllegalArgumentException(club1
					+ " : id non compris dans la division");
		}
	}

	public int getClub2() {
		return club2;
	}

	public void setClub2(int club2) throws IllegalArgumentException {
		boolean ok = false;
		for (Club c : this.listeClub) {
			if (c.getId() == club1) {
				this.club2 = club2;
				ok = true;
			}
		}
		if (!ok) {
			throw new IllegalArgumentException(club2
					+ " : id non compris dans la division");
		}
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String toString() {
		return club1 + " " + op + " " + club2;
	}

}
