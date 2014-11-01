package solveur;

import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.variables.IntVar;
import solver.variables.VariableFactory;

public class Recherche {

	private int nbClub;
	private int nbGroupe;
	private int tailleGroupe;
	private double[][] tabDistance;
	private double[][] solution;

	public Recherche(int nbGroupe, double[][] tabDistance) {
		this.nbGroupe = nbGroupe;
		this.tabDistance = tabDistance;
		this.nbClub = this.tabDistance.length;
		this.tailleGroupe = (int) Math.ceil((double) nbClub / nbGroupe);
	}

	public void lancer() {
		Solver s = new Solver();

		// solution au pb : si groupe[2]=3, le 2eme groupe est dans la poule 3
		IntVar[] club = new IntVar[nbClub];

		for (int i = 0; i < nbClub; i++) {
			club[i] = VariableFactory.bounded("Club " + i + " ", 0,
					nbGroupe - 1, s);
		}

		// Pas plus de clubs que la cap max d'une poule
		for (int i = 0; i < nbGroupe; i++) {
			IntVar e = VariableFactory.bounded("E " + i, 0, tailleGroupe, s);
			s.post(IntConstraintFactory.count(i, club, e));
		}
		
		
		s.findSolution();
		System.out.println(s.toString());

		this.solution = tabDistance;
	}

	public String toString() {
		String rep = "";
		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution[i].length; j++) {
				rep += "  " + solution[i][j];
			}
			rep += " \n";
		}
		return rep;
	}
}
