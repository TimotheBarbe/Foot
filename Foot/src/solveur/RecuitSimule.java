package solveur;

import java.util.ArrayList;
import java.util.Collections;

import model.Desiderata;
import model.GenerateDistances;

public class RecuitSimule {
	private int nbGroupe;
	private int[][] tabDistance;
	private int[] clubs;
	private int nbClub;
	private int[] solution;
	private double[] distance;
	private double distanceTotale;
	private long tempsMaxTotal;
	private long tempsMaxBoucle;
	private int nbRestart;
	public int meilleurSolution[];
	private double meilleurDist;
	private static double alpha = 0.98; // coefficient de décroissance de la
										// température

	public int[] getClubs() {
		return clubs;
	}

	/**
	 * Renvoie dans clubs une solution aléatoire comportant nbGroupe de
	 * nbClub/nbGroupe ou nbClub/nbGroupe +1 clubs par groupe. Met à jour
	 * distance[] et distanceTotale
	 */
	public void solutionInitialeAleatoire() {
		ArrayList<Integer> ListSolIni = new ArrayList<>();

		for (int i = 0; i < nbClub; i++) {
			ListSolIni.add(i % nbGroupe);
		}
		Collections.shuffle(ListSolIni);
		for (int i = 0; i < nbClub; i++) {
			clubs[i] = ListSolIni.get(i);
		}

		majDistances();
	}

	/**
	 * met à jour la distance totale parcourue par l'ensemble des clubs, ainsi
	 * que celle parcourue par chaque club
	 */
	public void majDistances() {
		distanceTotale = 0;
		for (int i = 0; i < nbClub; i++) {
			distance[i] = 0;
		}

		for (int i = 0; i < nbClub; i++) {
			for (int j = 0; j < nbClub; j++) {
				if (i != j && clubs[i] == clubs[j]) {
					distance[i] += tabDistance[i][j];
				}
			}
		}

		for (int i = 0; i < nbClub; i++) {
			distanceTotale += distance[i];
		}
	}

	/**
	 * inverse 2 clubs a et b
	 * 
	 * @param a
	 * @param b
	 */
	public void inverserClubs(int a, int b) {
		int clubA = clubs[a];
		clubs[a] = clubs[b];
		clubs[b] = clubA;
	}

	// TODO pas encore de desiderata
	// TODO tab dist à mettre en double
	public RecuitSimule(int nbGroupe, int[][] tabDistance, Desiderata[] listeDesiderata, long tempsMaxTotal,
			long tempsMaxBoucle, int nbRestart) {

		this.nbGroupe = nbGroupe;
		this.tabDistance = tabDistance;
		this.nbClub = this.tabDistance.length;
		this.clubs = new int[nbClub];
		this.tempsMaxBoucle = tempsMaxBoucle;
		this.tempsMaxTotal = tempsMaxTotal;
		this.nbRestart = nbRestart;
		this.solution = new int[nbClub];
		this.distance = new double[nbClub];
		this.meilleurSolution = new int[nbClub];

		solutionInitialeAleatoire();
		meilleurDist = distanceTotale;

		for (int i = 0; i < nbClub; i++) {
			meilleurSolution[i] = clubs[i];
		}

		int restart = 0;
		while (restart < nbRestart) {

			double dateCourante = System.currentTimeMillis();
			double dateFin = dateCourante + tempsMaxTotal / nbRestart;

			double temperature = 1;
			int nbIter = 0;
			while (dateCourante < dateFin) {
				nbIter++;
				double dateCouranteBoucle = System.currentTimeMillis();
				double dateFinBoucle = dateCouranteBoucle + tempsMaxBoucle;

				int nbIterBoucle = 0;

				while (dateCouranteBoucle < dateFinBoucle) {
					nbIterBoucle++;

					double distanceTotaleOld = distanceTotale;
					int a = (int) (nbClub * Math.random());
					int b = a;
					while (a == b) {
						b = (int) (nbClub * Math.random());
					}

					inverserClubs(a, b);
					majDistances();

					double delta = distanceTotale - distanceTotaleOld;
					if (delta <= 0) {
						// oui

						if (meilleurDist > distanceTotale) {
							for (int i = 0; i < nbClub; i++) {
								meilleurSolution[i] = clubs[i];
								meilleurDist = distanceTotale;
							}
						}

						System.out.println(nbIter + "." + nbIterBoucle + " - Distance totale : " + distanceTotale
								+ ", Température : " + temperature);
					} else {
						// proba de oui
						double p = Math.random();
						double proba = Math.exp(-(0.001 * delta) / temperature);

						if (p < proba) {
							// on accepte
						} else {
							// on accepte pas
							inverserClubs(a, b);
							majDistances();
						}

						System.out.println(nbIter + "." + nbIterBoucle + " - Distance totale : " + distanceTotale
								+ ", Température : " + temperature + ", probabilité " + proba);
					}

					dateCouranteBoucle = System.currentTimeMillis();

				}

				System.out.println("Distance totale : " + distanceTotale);

				temperature *= alpha;
				dateCourante = System.currentTimeMillis();
			}
			for (int i = 0; i < nbClub; i++) {
				clubs[i] = meilleurSolution[i];
			}
			majDistances();
			System.out.println("Meilleur solution : " + distanceTotale);
			restart++;
		}
	}

	public static void main(String[] args) {
		int nbCl = 80;
		int distMaxInterClub = 100;

		int[][] tabDist = GenerateDistances.genererTableau(nbCl, distMaxInterClub);

		int nbGroupe = 10;

		RecuitSimule RS = new RecuitSimule(nbGroupe, tabDist, null, 60000, 800, 4);

	}
}
