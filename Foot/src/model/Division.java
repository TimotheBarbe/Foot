package model;

import java.util.ArrayList;
import java.util.Observable;

public class Division extends Observable {

	// liste des clubs
	public ArrayList<Club> liste;

	// nombre de groupes de la division
	public int nombreDeGroupe;
	public static final Integer CHANGEMENT_CLUB = new Integer(1);

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
		this.setChanged();
		this.notifyObservers(CHANGEMENT_CLUB);
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
		this.setChanged();
		this.notifyObservers(CHANGEMENT_CLUB);
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
			this.setChanged();
			this.notifyObservers(CHANGEMENT_CLUB);
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
