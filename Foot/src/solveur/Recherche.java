package solveur;

import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.constraints.LogicalConstraintFactory;
import solver.variables.BoolVar;
import solver.variables.IntVar;
import solver.variables.VariableFactory;
import solver.variables.view.BoolConstantView;

public class Recherche {

	private int nbClub;
	private int nbGroupe;
	private int tailleGroupe;
	private int[][] tabDistance;
	private int[][] solution;

	public Recherche(int nbGroupe, int[][] tabDistance) {
		this.nbGroupe = nbGroupe;
		this.tabDistance = tabDistance;
		this.nbClub = this.tabDistance.length;
		this.tailleGroupe = (int) Math.ceil((double) nbClub / nbGroupe);
	}

	public void lancer() {
		Solver s = new Solver();

		// solution au pb : si club[2]=3, le 2eme club est dans la poule 3
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

		// Somme des distances pour chaque poule

		IntVar zero = VariableFactory.bounded("zero", 0, 0, s);
		IntVar un = VariableFactory.bounded("un", 1, 1, s);

		for (int j = 0; j < nbClub; j++) {
			IntVar[] d = VariableFactory
					.boolArray("memeGroupe " + j, nbClub, s);
			for (int i = 0; i < d.length; i++) {
				s.post(LogicalConstraintFactory.ifThenElse(
						IntConstraintFactory.arithm(club[j], "=", club[i]),
						IntConstraintFactory.arithm(d[i], "=", un),
						IntConstraintFactory.arithm(d[i], "=", zero)));
			}
		}

		// IntVar rep = VariableFactory.bounded("distance[club[3]]", 0, 11, s);
		// s.post(IntConstraintFactory.element(rep, tabDistance[0], club[3]));

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
