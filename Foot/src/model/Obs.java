package model;

import java.awt.Point;
import java.util.Observable;

public class Obs extends Observable {

	public static final Integer CHANGEMENT_CLUB = new Integer(1);
	public static final Integer CHANGEMENT_ZOOM = new Integer(2);
	private Division div;
	private int[] reponseSolveur;
	private int zoom;
	private Point coinZoom;

	public Obs(Division div, int[] reponseSolveur) {
		super();
		this.div = div;
		this.reponseSolveur = reponseSolveur;
		this.zoom = 1;
		this.coinZoom = new Point(0, 0);
	}

	public Division getDiv() {
		return div;
	}

	public void setDiv(Division div) {
		this.div = div;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_CLUB);
	}

	public int[] getReponseSolveur() {
		return reponseSolveur;
	}

	public void setReponseSolveur(int[] reponseSolveur) {
		this.reponseSolveur = reponseSolveur;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		if (zoom > 0) {
			this.zoom = zoom;
			this.setChanged();
			this.notifyObservers(CHANGEMENT_ZOOM);
		}
	}

	public Point getCoinZoom() {
		return coinZoom;
	}

	public void setCoinZoom(Point centreZoom) {
		this.coinZoom = centreZoom;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_ZOOM);
	}

	public void changeGroupe(int id, int newGroupe) {
		this.reponseSolveur[id] = newGroupe;
		this.setChanged();
		this.notifyObservers(CHANGEMENT_CLUB);
	}

}
