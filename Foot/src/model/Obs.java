package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

/**
 * Observable représentant l'état du logiciel
 * 
 * @authors Timothé Barbe, Florent Euvrard, Cheikh Sylla
 *
 */
public class Obs extends Observable {

	public static final Integer CHANGEMENT_CLUB_COURANT = new Integer(1);
	public static final Integer CHANGEMENT_DESSIN = new Integer(2);
	public static final Integer CHANGEMENT_REPONSE_SOLVEUR = new Integer(3);

	private Division div;
	private int[] reponseSolveur;
	private int zoom;
	private Point coinZoom;
	private int indiceSurvole;
	private Club clubSelection;
	private int indiceJListClubSelection;
	private boolean[] tableVisible;
	private double[][] tabDist;
	private ArrayList<Desiderata> desiderata;

	/**
	 * @param div
	 *            division courante
	 * @param reponseSolveur
	 *            solution donnée par le solveur
	 * @param tabDist
	 *            matrice des distances de la division
	 */
	public Obs(Division div, int[] reponseSolveur, double[][] tabDist) {
		super();
		this.div = div;
		this.reponseSolveur = reponseSolveur;
		this.zoom = 1;
		this.coinZoom = new Point(0, 0);
		this.tableVisible = new boolean[reponseSolveur.length];
		Arrays.fill(this.tableVisible, true);
		this.setIndiceSurvole(-1);
		this.setClubSelectionne(null);
		this.setIndiceJListClubSelection(-1);
		this.setTabDist(tabDist);
		this.setDesiderata(new ArrayList<Desiderata>());
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

	public int getIndiceSurvole() {
		return indiceSurvole;
	}

	public void setIndiceSurvole(int indiceSurvole) {
		this.indiceSurvole = indiceSurvole;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_DESSIN);
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

	/**
	 * @return liste ordonnée des clubs pour la JTable
	 */
	public ArrayList<String> getListForTable() {
		ArrayList<String> data = new ArrayList<String>();
		int clubRestant = this.getReponseSolveur().length;
		int indice = 0;
		while (clubRestant > 0) {
			String fin = getNbGroupeDansGroupe(indice) > 1 ? " clubs)"
					: " club)";
			data.add("Poule " + EquivalentLettre.getLettre(indice) + " ("
					+ getNbGroupeDansGroupe(indice) + fin);
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

	public double[][] getTabDist() {
		return tabDist;
	}

	public void setTabDist(double[][] tabDist) {
		this.tabDist = tabDist;
	}

	/**
	 * @param indiceClub
	 * @return le nombre de km parcouru par le club
	 */
	public int getDistParcourue(int indiceClub) {
		int rep = 0;
		int groupe = this.getReponseSolveur()[indiceClub];
		for (int i = 0; i < this.getDiv().getListe().size(); i++) {
			if (getReponseSolveur()[i] == groupe) {
				rep += getTabDist()[indiceClub][i];
			}
		}
		return rep;
	}

	private int getNbGroupeDansGroupe(int groupe) {
		int rep = 0;
		for (int i : this.getReponseSolveur()) {
			if (i == groupe) {
				rep++;
			}
		}
		return rep;
	}

	public boolean estRepartiHomogene() {
		int[] tableGroupe = new int[this.getDiv().getNbGroupe()];
		for (int i = 0; i < tableGroupe.length; i++) {
			tableGroupe[i] = this.getNbGroupeDansGroupe(i);
		}

		int max = 0, min = 1000;
		for (int i : tableGroupe) {
			if (i < min) {
				min = i;
			}
			if (i > max) {
				max = i;
			}
		}
		return !(max - min > 1);
	}

	public ArrayList<Desiderata> getDesiderata() {
		return desiderata;
	}

	public void setDesiderata(ArrayList<Desiderata> desiderata) {
		this.desiderata = desiderata;
	}

	/**
	 * @param i
	 *            indice du desiderata dans la liste
	 * @return true si le desiderata est respecté, false sinon
	 */
	public boolean desiderataOk(int i) {
		Desiderata d = this.getDesiderata().get(i);
		Club c1 = this.getDiv().getClubById(d.getClub1());
		Club c2 = this.getDiv().getClubById(d.getClub2());
		if (d.getOp().equals("=")) {
			return getReponseSolveur()[getDiv().getListe().indexOf(c1)] == getReponseSolveur()[getDiv()
					.getListe().indexOf(c2)];
		}
		if (d.getOp().equals("!=")) {
			return getReponseSolveur()[getDiv().getListe().indexOf(c1)] != getReponseSolveur()[getDiv()
					.getListe().indexOf(c2)];
		}
		return false;
	}

	/**
	 * @return nombre de desiderata respectés
	 */
	public int nbDesiderataFail() {
		int rep = 0;
		for (int i = 0; i < this.getDesiderata().size(); i++) {
			if (!desiderataOk(i)) {
				rep++;
			}
		}
		return rep;
	}
}
