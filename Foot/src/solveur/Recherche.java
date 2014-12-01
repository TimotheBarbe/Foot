package solveur;

import model.Desiderata;
import solver.ResolutionPolicy;
import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.constraints.LogicalConstraintFactory;
import solver.search.limits.FailCounter;
import solver.search.loop.monitors.SMF;
import solver.search.loop.monitors.SearchMonitorFactory;
import solver.search.strategy.IntStrategyFactory;
import solver.search.strategy.strategy.AbstractStrategy;
import solver.trace.Chatterbox;
import solver.variables.IntVar;
import solver.variables.VariableFactory;

public class Recherche {

	private int nbClub;
	private int nbGroupe;
	private int tailleGroupe;
	private int[][] tabDistance;
	private int[] solution;
	private int[] distance;
	private Desiderata[] listeDesiderata;
	private long tempsMax;
	private int distMax = 450;

	public Recherche(int nbGroupe, int[][] tabDistance,
			Desiderata[] listeDesiderata, long tempsMax) {
		this.nbGroupe = nbGroupe;
		this.tabDistance = tabDistance;
		this.nbClub = this.tabDistance.length;
		this.tailleGroupe = (int) Math.ceil((double) nbClub / nbGroupe);
		this.solution = new int[nbClub];
		this.distance = new int[nbClub];
		this.listeDesiderata = listeDesiderata;
		this.tempsMax = tempsMax;
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
			IntVar e = VariableFactory.bounded("E " + i, tailleGroupe - 1,
					tailleGroupe, s);
			s.post(IntConstraintFactory.count(i, club, e));
		}

		// Somme des distances pour chaque poule

		IntVar zero = VariableFactory.fixed(0, s);
		IntVar un = VariableFactory.fixed(1, s);

		IntVar[] tabDist = new IntVar[nbClub];
		for (int j = 0; j < nbClub; j++) {
			IntVar[] d = VariableFactory
					.boolArray("memeGroupe " + j, nbClub, s);
			for (int i = 0; i < d.length; i++) {
				LogicalConstraintFactory.ifThenElse(
						IntConstraintFactory.arithm(club[j], "=", club[i]),
						IntConstraintFactory.arithm(d[i], "=", un),
						IntConstraintFactory.arithm(d[i], "=", zero));
			}
			IntVar distance = VariableFactory.bounded("distance" + j, 0, 10000,
					s);
			s.post(IntConstraintFactory.scalar(d, tabDistance[j], distance));
			tabDist[j] = distance;
		}

		IntVar sommeDist = VariableFactory.bounded("somme distance", 0, 100000,
				s);
		s.post(IntConstraintFactory.sum(tabDist, sommeDist));
		s.post(IntConstraintFactory.arithm(sommeDist, "<", this.distMax));
		// Desiderata
		for (Desiderata d : listeDesiderata) {
			s.post(IntConstraintFactory.arithm(club[d.getClub1()], d.getOp(),
					club[d.getClub2()]));
		}

		// Limitation en temps du probleme
		SearchMonitorFactory.limitTime(s, this.tempsMax);

		AbstractStrategy activity = IntStrategyFactory.activity(club,15012011);
		s.set(IntStrategyFactory.lastConflict(s,activity));
		SMF.geometrical(s,(nbClub)/2,2,new FailCounter(nbClub),Integer.MAX_VALUE);
		
		// Resolution
		
		Chatterbox.showStatisticsDuringResolution(s, 1000);
		//s.findOptimalSolution(ResolutionPolicy.MINIMIZE, sommeDist);
		s.findSolution();

		System.out.println(sommeDist.getValue());
		
		// Memorisation
		for (int i = 0; i < this.nbClub; i++) {
			this.solution[i] = club[i].getValue();
		}

		for (int i = 0; i < this.nbClub; i++) {
			this.distance[i] = tabDist[i].getValue();
		}
	}

	public String getSolution() {
		String rep = "Solution: ";
		for (int i = 0; i < solution.length; i++) {
			rep += "  " + solution[i];
		}
		return rep;
	}

	public String getDistance() {
		String rep = "Distances : ";
		for (int i = 0; i < distance.length; i++) {
			rep += "  " + distance[i];
		}
		return rep;
	}
}
