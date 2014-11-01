package model;

public class Ville {

	private int id;
	private double x;
	private double y;
	private String nom;

	/**
	 * Genere une ville
	 * 
	 * @param id
	 *            : id unique
	 * @param nom
	 * @param x
	 *            : position X sur la carte (IHM)
	 * @param y
	 *            : position Y sur la carte (IHM)
	 */
	public Ville(int id, String nom, double x, double y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.nom = nom;
	}

	/****************************************************************
	 * ---------------------- GETTER --------------------------------
	 ****************************************************************/

	public double getX() {
		return x;
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public double getY() {
		return y;
	}

	/****************************************************************
	 * ---------------------- SETTER --------------------------------
	 ****************************************************************/

	public void setY(double y) {
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setId(int i) {
		this.id = i;
	}

	/****************************************************************
	 * ---------------------- METHODES ------------------------------
	 ****************************************************************/

	public String toString() {
		return getNom() + " (" + getId() + ")";
	}

}
