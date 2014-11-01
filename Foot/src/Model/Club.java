package Model;

public class Club {

	private String nom;

	// identifiant unique du club
	private int id;

	// ville du club
	private Ville v;

	/**
	 * Creation d'un club
	 * 
	 * @param nom
	 *            : nom du club
	 * @param id
	 *            : id unique du club
	 * @param v
	 *            : ville du club
	 * 
	 */
	public Club(String nom, int id, Ville v) {
		this.nom = nom;
		this.id = id;
		this.v = v;
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

	public Ville getVille() {
		return v;
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

	public void setVille(Ville v) {
		this.v = v;
	}

	/****************************************************************
	 * ---------------------- METHODES ------------------------------
	 ****************************************************************/

	public String toString() {
		return getNom() + " (" + getId() + ")";
	}
}
