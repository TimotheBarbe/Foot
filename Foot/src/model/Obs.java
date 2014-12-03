package model;

import java.util.Observable;

public class Obs extends Observable {

	public static final Integer CHANGEMENT_CLUB = new Integer(1);
	private Division div;
	private int[] reponseSolveur;

	public Obs(Division div, int[] reponseSolveur) {
		super();
		this.div = div;
		this.reponseSolveur = reponseSolveur;
	}

	public Division getDiv() {
		return div;
	}

	public void setDiv(Division div) {
		this.div = div;
	}

	public int[] getReponseSolveur() {
		return reponseSolveur;
	}

	public void setReponseSolveur(int[] reponseSolveur) {
		this.reponseSolveur = reponseSolveur;
	}

	public void changeGroupe(int id, int newGroupe) {
		this.reponseSolveur[id] = newGroupe;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_CLUB);
	}

}
