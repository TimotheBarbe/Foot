package model;

import java.util.ArrayList;

/**
 * Une division est un ensemble de clubs, répartis en poules
 * 
 * @authors Timothé Barbe, Florent Euvrard, Cheikh Sylla
 *
 */
public class Division {

	// liste des clubs
	private ArrayList<Club> liste;

	// nombre de groupes de la division
	private int nombreDeGroupe;
	private String nom;

	/**
	 * Initialise une nouvelles division vide
	 * 
	 * @param nombreDeGroupe
	 * @param division
	 *            : nom de la division
	 */
	public Division(int nombreDeGroupe, String division) {
		this.liste = new ArrayList<Club>();
		this.nombreDeGroupe = nombreDeGroupe;
		this.nom = division;
	}

	public ArrayList<Club> getListe() {
		return liste;
	}

	public String getNom() {
		return nom;
	}

	public int getNbGroupe() {
		return nombreDeGroupe;
	}

	public void setListe(ArrayList<Club> liste) {
		this.liste = liste;
	}

	public void setNbGroupe(int n) {
		this.nombreDeGroupe = n;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

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
		return nom + " (" + getNbClub() + " clubs dans " + this.nombreDeGroupe
				+ " groupes)";
	}
}
