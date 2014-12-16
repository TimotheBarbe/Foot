package model;

public class Club {

	private String nom;

	// identifiant unique du club
	private int id;

	// coordonnes GPS
	private double[] coordonneesGPS;
	
	// coordonnees matricielles
	private double[] coordonneesMatricielles;

	/**
	 * Creation d'un club
	 * 
	 * @param nom
	 *            : nom du club
	 * @param id
	 *            : id unique du club
	 * @param coordonneesGPS
	 *            : coordonnees GPS du club
	 * 
	 */
	public Club(String nom, int id, double[] coordonnesGPS) {
		this.nom = nom;
		this.id = id;
		this.coordonneesGPS = coordonnesGPS;
		
		this.coordonneesMatricielles = this.getCoordMatriByGPS(coordonnesGPS);
	}

	/****************************************************************
	 * ---------------------- METHODES ------------------------------
	 ****************************************************************/

	public String toString() {
		return getNom() + " (" + getId() + ")";
	}
	
	/**
	 * 
	 * @param coordonneesGPS : les coordonnees GPS du club
	 * @return le calcul des coordonnees matricielles a partir des coordonnees GPS
	 */
	public double[] getCoordMatriByGPS(double[] coordonneesGPS){
		double[] coordonnesMatricielles = new double[2];
		
		return coordonnesMatricielles;
	}
	
	/****************************************************************
	 * ---------------------- GETTER --------------------------------
	 ****************************************************************/

	public String getNom() {
		return nom;
	}

	public int getId() {
		return id;
	}
	
	public double[] getCoordonneesGPS() {
		return coordonneesGPS;
	}

	public double[] getCoordonneesMatricielles() {
		return coordonneesMatricielles;
	}
	/****************************************************************
	 * ---------------------- SETTER --------------------------------
	 ****************************************************************/

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setCoordonneesGPS(double[] coordonneesGPS) {
		this.coordonneesGPS = coordonneesGPS;
	}
	
	public void setCoordonneesMatricielles(double[] coordonneesMatricielles) {
		this.coordonneesMatricielles = coordonneesMatricielles;
	}
}
