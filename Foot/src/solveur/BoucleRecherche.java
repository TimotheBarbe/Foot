package solveur;

import model.Desiderata;

public class BoucleRecherche {

	private int nbGroupe;
	private int[][] tabDistance;
	private Desiderata[] listeDesiderata;
	private long tempsMaxTotal;
	private long tempsMaxBoucle;
	private boolean afficher;
	private static long epsilon = 100;
	private int sommeDistFinal;
	private int sommeDistMax = 100000;
	private double pourcentage = 0.99;

	public BoucleRecherche(int nbGroupe, int[][] tabDistance,
			Desiderata[] listeDesiderata, long tempsMaxTotal,
			long tempsMaxBoucle, boolean afficher) {
		this.nbGroupe = nbGroupe;
		this.tabDistance = tabDistance;
		this.listeDesiderata = listeDesiderata;
		this.tempsMaxTotal = tempsMaxTotal;
		this.tempsMaxBoucle = tempsMaxBoucle;
		this.afficher = afficher;
	}

	public void run() {
		long debut = System.currentTimeMillis();
		long now = debut;
		

		while (now - debut < tempsMaxTotal && pourcentage < 1) {
			this.recherche();
			now = System.currentTimeMillis();
		}
		System.out.println("meilleur : " + sommeDistFinal);
	}
	
	public synchronized void recherche(){
		long startBoucle = System.currentTimeMillis();
		Recherche r = new Recherche(nbGroupe, tabDistance, listeDesiderata,
				tempsMaxBoucle, sommeDistMax, afficher);
		r.execute();
		long endBoucle = System.currentTimeMillis();
		if (startBoucle + tempsMaxBoucle > endBoucle - epsilon) {
			sommeDistFinal = r.sommeDist.getValue();
			sommeDistMax = (int) (r.sommeDist.getValue() * pourcentage);
		} else {
			pourcentage += 0.001;
		}

	}
}
