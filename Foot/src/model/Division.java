package model;

import java.util.ArrayList;
import java.util.Observable;

public class Division {

	// liste des clubs
	public ArrayList<Club> liste;

	// nombre de groupes de la division
	public int nombreDeGroupe;

	public Division(int nombreDeGroupe) {
		this.liste = new ArrayList<Club>();
		this.nombreDeGroupe = nombreDeGroupe;
	}

	/****************************************************************
	 * ---------------------- GETTER --------------------------------
	 ****************************************************************/

	public ArrayList<Club> getListe() {
		return liste;
	}

	public int getNbGroupe() {
		return nombreDeGroupe;
	}

	/****************************************************************
	 * ---------------------- SETTER --------------------------------
	 ****************************************************************/

	public void setListe(ArrayList<Club> liste) {
		this.liste = liste;
	}

	public void setNbGroupe(int n) {
		this.nombreDeGroupe = n;
	}

	/****************************************************************
	 * ---------------------- METHODES ------------------------------
	 ****************************************************************/

	public int getNbClub() {
		return this.liste.size();
	}

	public void addClub(Club b) {
		this.liste.add(b);
	}

	public Club getClubById(int id) {
		for (Club c : liste) {
			if (c.getId() == id) {
				return c;
			}
		}
		throw new IllegalArgumentException("L'id " + id
				+ " n'existe pas dans la liste");
	}

	public void removeClub(int id) {
		if (!this.contains(id)) {
			throw new IllegalArgumentException("L'id " + id
					+ " n'existe pas dans la liste");
		} else {
			this.liste.remove(this.getClubById(id));

		}
	}

	private boolean contains(int id) {
		for (Club c : liste) {
			if (c.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return getNbClub() + " clubs dans " + this.nombreDeGroupe + " groupes";
	}
}
