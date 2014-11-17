package model;

public class Desiderata {

	private int club1;
	private int club2;
	private String op;

	public Desiderata(int club1, int club2, String op) {
		this.club1 = club1;
		this.club2 = club2;
		this.op = op;
	}

	public int getClub1() {
		return club1;
	}

	public void setClub1(int club1) {
		this.club1 = club1;
	}

	public int getClub2() {
		return club2;
	}

	public void setClub2(int club2) {
		this.club2 = club2;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

}
