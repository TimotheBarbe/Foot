package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

/**
 * Observable regroupant tous les parametres caracterisant un etat du logiciel
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
	private int[][] tabDist;

	/**
	 * Cree et initialise un nouvel obs
	 * 
	 * @param div
	 *            : division
	 * @param reponseSolveur
	 *            : solution donnee par le solveur
	 * @param tabDist
	 *            : tableau des distances des clubs concernes
	 */
	public Obs(Division div, int[] reponseSolveur, int[][] tabDist) {
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
	}

	public Division getDiv() {
		return div;
	}

	/**
	 * Change la division de l'obs. Notifie les observers a
	 * CHANGEMENT_CLUB_COURANT
	 * 
	 * @param div
	 */
	public void setDiv(Division div) {
		this.div = div;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_CLUB_COURANT);
	}

	public int[] getReponseSolveur() {
		return reponseSolveur;
	}

	/**
	 * Change la solution de 'obs. Notifie les observers a
	 * CHANGEMENT_REPONSE_SOLVEUR
	 * 
	 * @param reponseSolveur
	 */
	public void setReponseSolveur(int[] reponseSolveur) {
		this.reponseSolveur = reponseSolveur;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_REPONSE_SOLVEUR);
	}

	/**
	 * @return le facteur de grossissement de la carte
	 */
	public int getZoom() {
		return zoom;
	}

	/**
	 * Change le zoom de l'obs. Notifie les obervers a CHANGEMENT_DESSIN
	 * 
	 * @param zoom
	 *            : facteur de grossissement (>0)
	 */
	public void setZoom(int zoom) {
		if (zoom > 0) {
			this.zoom = zoom;
			this.setChanged();
			this.notifyObservers(CHANGEMENT_DESSIN);
		}
	}

	/**
	 * @return les coordonnees du coin de la carte
	 */
	public Point getCoinZoom() {
		return coinZoom;
	}

	/**
	 * Change la valeur du coin. Notifie les oberservers a CHANGEMENT_DESSIN
	 * 
	 * @param centreZoom
	 */
	public void setCoinZoom(Point centreZoom) {
		this.coinZoom = centreZoom;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_DESSIN);
	}

	/**
	 * Change la solution du groupe d'indice id. Notifie les observers a
	 * CHANGEMENT_CLUB_COURANT
	 * 
	 * @param id
	 *            : position du club a modifier
	 * @param newGroupe
	 *            : nouveau groupe
	 */
	public void changeGroupe(int id, int newGroupe) {
		this.reponseSolveur[id] = newGroupe;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_CLUB_COURANT);
	}

	/**
	 * @return l'indice du club survole
	 */
	public int getIndiceSurvole() {
		return indiceSurvole;
	}

	/**
	 * Change l'indice du club survole. Notifie les observers a
	 * CHANGEMENT_DESSIN
	 * 
	 * @param indiceSurvole
	 */
	public void setIndiceSurvole(int indiceSurvole) {
		this.indiceSurvole = indiceSurvole;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_DESSIN);
	}

	/**
	 * @return le club selectionne
	 */
	public Club getClubSelectionne() {
		return clubSelection;
	}

	/**
	 * Change le club selectionne. Notifie les obervers a
	 * CHANGEMENT_CLUB_COURANT
	 * 
	 * @param clubS
	 *            : club selectionne
	 */
	public void setClubSelectionne(Club clubS) {
		this.clubSelection = clubS;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_CLUB_COURANT);
	}

	/**
	 * @return le tableau des clubs visibles sur la carte
	 */
	public boolean[] getTableVisible() {
		return tableVisible;
	}

	/**
	 * Change le tableau des clubs visibles. Notifie les observers a
	 * CHANGEMENT_DESSIN
	 * 
	 * @param tableVisible
	 */
	public void setTableVisible(boolean[] tableVisible) {
		this.tableVisible = tableVisible;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_DESSIN);
	}

	/**
	 * @return indice de la JTable selectionne
	 */
	public int getIndiceJListClubSelection() {
		return indiceJListClubSelection;
	}

	/**
	 * @param indiceJListClubSelection
	 *            indice selectionne de la JTable
	 */
	public void setIndiceJListClubSelection(int indiceJListClubSelection) {
		this.indiceJListClubSelection = indiceJListClubSelection;
	}

	/**
	 * @return la liste triee par groupes des clubs
	 */
	public ArrayList<String> getListForTable() {
		ArrayList<String> data = new ArrayList<String>();
		int clubRestant = this.getReponseSolveur().length;
		int indice = 0;
		while (clubRestant > 0) {
			String fin = getNbClubsDansGroupe(indice) > 1 ? " clubs)"
					: " club)";
			data.add("Poule " + EquivalentLettre.getLettre(indice) + " ("
					+ getNbClubsDansGroupe(indice) + fin);
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

	/**
	 * @return matrice des distances
	 */
	public int[][] getTabDist() {
		return tabDist;
	}

	/**
	 * @param tabDist
	 *            matrice des distances
	 */
	public void setTabDist(int[][] tabDist) {
		this.tabDist = tabDist;
	}

	/**
	 * @param indiceClub
	 *            indice du club
	 * @return distance parcourue par le club
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

	/**
	 * @param groupe
	 *            indice du groupe
	 * @return le nombre de clubs dans le groupe
	 */
	private int getNbClubsDansGroupe(int groupe) {
		int rep = 0;
		for (int i : this.getReponseSolveur()) {
			if (i == groupe) {
				rep++;
			}
		}
		return rep;
	}

	/**
	 * @return true si la solution est homogene (tailles de groupes egales +/- 1
	 *         ), false sinon
	 */
	public boolean estRepartiHomogene() {
		int[] tableGroupe = new int[this.getDiv().getNbGroupe()];
		for (int i = 0; i < tableGroupe.length; i++) {
			tableGroupe[i] = this.getNbClubsDansGroupe(i);
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
}
