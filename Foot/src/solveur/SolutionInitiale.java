package solveur;

import java.util.ArrayList;
import java.util.Collections;

public class SolutionInitiale {
	private int nbGroupe;
	private double[][] tabDistance;
	private int[] clubs;
	private int nbClub;
	private int[] solution;
	private int distanceTotale;
	private int[] distance;

	public SolutionInitiale(int nbGroupe, double[][] tabDistance, int nbClub) {
		this.nbGroupe = nbGroupe;
		this.tabDistance = tabDistance;
		this.clubs = new int[nbClub];
		this.nbClub = nbClub;
		this.solution = new int[nbClub];

		ArrayList<ArrayList<Double>> ListDistance = new ArrayList<>();
		for (int i = 0; i < nbClub; i++) {
			ArrayList<Double> listTemp = new ArrayList<>();

			for (int j = 0; j < nbClub; j++) {
				listTemp.add(j, tabDistance[i][j]);
			}
			ListDistance.add(listTemp);
		}

		ArrayList<Integer> clubsAffecte = new ArrayList<>();
		// boucle d'affectation aux groupes
		for (int i = 0; i < this.nbGroupe; i++) {
			int nbClubEnRab = nbClub - nbGroupe * (int) (nbClub / nbGroupe);
			int nbVoisins = 0;
			if (i < nbClubEnRab) {
				nbVoisins = nbClub / nbGroupe;
			} else {
				nbVoisins = nbClub / nbGroupe - 1;
			}
			if (nbClub <= nbGroupe) {
				nbVoisins = 0;
			}
			double[][] tabDist = new double[ListDistance.size()][ListDistance
					.size()];
			for (int k = 0; k < ListDistance.size(); k++) {
				for (int j = 0; j < ListDistance.size(); j++) {
					tabDist[k][j] = ListDistance.get(k).get(j);
				}
			}

			int clubPlusLoin = clubPlusLoin(tabDist, clubsAffecte);
			clubs[clubPlusLoin] = i;
			clubsAffecte.add(clubPlusLoin);

			int[] voisins = nClubsPlusProche(nbVoisins, clubPlusLoin, tabDist);
			for (int j = 0; j < nbVoisins; j++) {
				clubs[voisins[j]] = i;
				clubsAffecte.add(voisins[j]);
			}

			// maj ListDist des clubs utilisés à -1
			for (int k : voisins) {
				for (int j = 0; j < nbClub; j++) {
					ListDistance.get(k).set(j, -0.00001);
					ListDistance.get(j).set(k, -0.00001);
				}
			}
			for (int j = 0; j < nbClub; j++) {
				ListDistance.get(clubPlusLoin).set(j, -0.00001);
				ListDistance.get(j).set(clubPlusLoin, -0.00001);
			}
		}
		solution = clubs;
		majDistances();
	}

	public int[] getSolution() {
		return this.solution;
	}

	/**
	 * 
	 * @param dist
	 * @return l'indice du club le plus éloigné (en moyenne)
	 */
	public int clubPlusLoin(double[][] dist, ArrayList<Integer> clubsAffecte) {
		int[] somLign = new int[nbClub];

		for (int i = 0; i < nbClub; i++) {
			for (int j = 0; j < nbClub; j++) {
				somLign[i] += dist[i][j];
			}
		}
		for (int i : clubsAffecte) {
			somLign[i] = -1;
		}
		return getIndiceDuMax(somLign);
	}

	public int getIndiceDuMax(int[] dist) {
		int indiceMax = 0;
		int max = dist[0];
		for (int i = 1; i < dist.length; i++) {
			if (max < dist[i]) {
				max = dist[i];
				indiceMax = i;
			}
		}
		return indiceMax;
	}

	public int getIndiceDuMin(int[] dist) {
		int indiceMin = 0;
		int min = dist[0];
		for (int i = 1; i < dist.length; i++) {
			if (min > dist[i]) {
				min = dist[i];
				indiceMin = i;
			}
		}
		return indiceMin;
	}

	/**
	 * 
	 * @param n
	 * @return les n clubs les plus proches d'un club
	 */
	public int[] nClubsPlusProche(int n, int club, double[][] distance) {
		ArrayList<Double> distVoisins = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			distVoisins.add((double) Integer.MAX_VALUE);
		}

		int[] list = new int[n];

		for (int i = 0; i < nbClub; i++) {
			if (i != club && distVoisins.get(n - 1) > distance[club][i]
					&& distance[club][i] > 0) {
				distVoisins.remove(n - 1);
				distVoisins.add(distance[club][i]);
				Collections.sort(distVoisins);

			}
		}
		// si toutes les dist sont différentes
		for (int i = 0; i < n; i++) {
			for (int cb = 0; cb < nbClub; cb++) {
				if (distVoisins.get(i) == distance[club][cb]
						&& !appartient(cb, list)) {
					list[i] = cb;
				}
			}
		}

		return list;
	}

	public boolean appartient(int n, int[] tab) {
		for (int i = 0; i < tab.length; i++) {
			if (tab[i] == n) {
				return true;
			}
		}
		return false;
	}

	/**
	 * met à jour la distance totale parcourue par l'ensemble des clubs, ainsi
	 * que celle parcourue par chaque club
	 */
	public void majDistances() {
		distanceTotale = 0;
		distance = new int[nbClub];
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

}
