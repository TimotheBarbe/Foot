package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

public class Obs extends Observable {

	public static final Integer CHANGEMENT_CLUB_COURANT = new Integer(1);
	public static final Integer CHANGEMENT_DESSIN = new Integer(2);
	public static final Integer CHANGEMENT_REPONSE_SOLVEUR = new Integer(3);
	
	private Division div;
	private int[] reponseSolveur;
	private int zoom;
	private Point coinZoom;
	private boolean afficherNom;
	private int indiceSurvole;
	private Club clubSelection;
	private int indiceJListClubSelection;
	private boolean[] tableVisible;

	public Obs(Division div, int[] reponseSolveur) {
		super();
		this.div = div;
		this.reponseSolveur = reponseSolveur;
		this.zoom = 1;
		this.coinZoom = new Point(0, 0);
		this.tableVisible = new boolean[reponseSolveur.length];
		Arrays.fill(this.tableVisible, true);
		this.setAfficherNom(false);
		this.setIndiceSurvole(-1);
		this.setClubSelectionne(null);
		this.setIndiceJListClubSelection(-1);
	}

	public Division getDiv() {
		return div;
	}

	public void setDiv(Division div) {
		this.div = div;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_CLUB_COURANT);
	}

	public int[] getReponseSolveur() {
		return reponseSolveur;
	}

	public void setReponseSolveur(int[] reponseSolveur) {
		this.reponseSolveur = reponseSolveur;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_REPONSE_SOLVEUR);
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		if (zoom > 0) {
			this.zoom = zoom;
			this.setChanged();
			this.notifyObservers(CHANGEMENT_DESSIN);
		}
	}

	public Point getCoinZoom() {
		return coinZoom;
	}

	public void setCoinZoom(Point centreZoom) {
		this.coinZoom = centreZoom;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_DESSIN);
	}

	public void changeGroupe(int id, int newGroupe) {
		this.reponseSolveur[id] = newGroupe;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_CLUB_COURANT);
	}

	public boolean isAfficherNom() {
		return afficherNom;
	}

	public void setAfficherNom(boolean afficherNom) {
		this.afficherNom = afficherNom;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_CLUB_COURANT);
	}

	public int getIndiceSurvole() {
		return indiceSurvole;
	}

	public void setIndiceSurvole(int indiceSurvole) {
		this.indiceSurvole = indiceSurvole;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_CLUB_COURANT);
	}

	public Club getClubSelectionne() {
		return clubSelection;
	}

	public void setClubSelectionne(Club clubS) {
		this.clubSelection = clubS;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_CLUB_COURANT);
	}
	
	public boolean[] getTableVisible() {
		return tableVisible;
	}

	public void setTableVisible(boolean[] tableVisible) {
		this.tableVisible = tableVisible;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_DESSIN);
	}


	public int getIndiceJListClubSelection() {
		return indiceJListClubSelection;
	}

	public void setIndiceJListClubSelection(int indiceJListClubSelection) {
		this.indiceJListClubSelection = indiceJListClubSelection;
	}

	public ArrayList<String> getListForTable() {
		ArrayList<String> data = new ArrayList<String>();
		int clubRestant = this.getReponseSolveur().length;
		int indice = 0;
		while (clubRestant > 0) {
			data.add("Poule " + (indice + 1));
			for (int i = 0; i < this.getReponseSolveur().length; i++) {
				if (this.getReponseSolveur()[i] == indice) {
					data.add("  " + this.getDiv().getListe().get(i));
					clubRestant--;
				}
			}
			indice++;
		}
		return data;
	}
	
}
