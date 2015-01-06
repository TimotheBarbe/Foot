package solveur;

import model.Desiderata;
import samples.AbstractProblem;
import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.constraints.LogicalConstraintFactory;
import solver.search.limits.FailCounter;
import solver.search.loop.monitors.SMF;
import solver.search.strategy.IntStrategyFactory;
import solver.search.strategy.strategy.AbstractStrategy;
import solver.trace.Chatterbox;
import solver.variables.IntVar;
import solver.variables.VariableFactory;

public class Recherche extends AbstractProblem {

	private int nbClub;
	private int nbGroupe;
	private int tailleGroupe;
	private int[][] tabDistance;
	private int[] solution;
	private int[] distance;
	private Desiderata[] listeDesiderata;
	private long tempsMax;
	private int distMax;
	private IntVar[] club;
	public IntVar sommeDist;
	private IntVar[] tabDist;
	private boolean afficher;

	public Recherche(int nbGroupe, int[][] tabDistance, Desiderata[] listeDesiderata, long tempsMax, int distMax,
			boolean afficher) {
		this.nbGroupe = nbGroupe;
		this.tabDistance = tabDistance;
		this.nbClub = this.tabDistance.length;
		this.tailleGroupe = (int) Math.ceil((double) nbClub / nbGroupe);
		this.solution = new int[nbClub];
		this.distance = new int[nbClub];
		this.listeDesiderata = listeDesiderata;
		this.tempsMax = tempsMax;
		this.afficher = afficher;
		this.distMax = distMax;
	}

	public String getSolution() {
		String rep = "Solution:  ";
		for (int i = 0; i < solution.length; i++) {
			if (solution[i] < 10) {
				rep += "    " + solution[i];
			} else {
				rep += "   " + solution[i];
			}
		}
		return rep;
	}

	public int[] getTabSolution() {
		return this.solution;
	}

	public String getDistance() {
		String rep = "Distances: ";
		for (int i = 0; i < distance.length; i++) {
			if (distance[i] < 100) {
				rep += "   " + distance[i];
			} else {
				rep += "  " + distance[i];
			}
		}
		return rep;
	}

	public void buildModel() {

		// solution au pb : si club[2]=3, le 2eme club est dans la poule 3

		club = new IntVar[nbClub];

		for (int i = 0; i < nbClub; i++) {
			club[i] = VariableFactory.bounded("Club " + i + " ", 0, nbGroupe - 1, solver);
		}

		// Pas plus de clubs que la cap max d'une poule

		for (int i = 0; i < nbGroupe; i++) {
			IntVar e = VariableFactory.bounded("E " + i, tailleGroupe - 1, tailleGroupe, solver);
			solver.post(IntConstraintFactory.count(i, club, e));
		}

		// Somme des distances pour chaque poule

		IntVar zero = VariableFactory.fixed(0, solver);
		IntVar un = VariableFactory.fixed(1, solver);

		tabDist = new IntVar[nbClub];
		IntVar[][] matriceGroupe = VariableFactory.boolMatrix("matriceGroupe", nbClub, nbClub, solver);

		for (int j = 0; j < nbClub; j++) {
			IntVar[] memeGroupe = VariableFactory.boolArray("memeGroupe " + j, nbClub, solver);
			for (int i = 0; i < memeGroupe.length; i++) {
				LogicalConstraintFactory.ifThenElse(IntConstraintFactory.arithm(club[j], "=", club[i]),
						IntConstraintFactory.arithm(memeGroupe[i], "=", un),
						IntConstraintFactory.arithm(memeGroupe[i], "=", zero));
			}
			matriceGroupe[j] = memeGroupe;
			IntVar distance = VariableFactory.bounded("distance" + j, 0, this.distMax, solver);
			solver.post(IntConstraintFactory.scalar(memeGroupe, tabDistance[j], distance));
			tabDist[j] = distance;
		}

		// Clubs dans des groupes différents s'ils ont particulièrement loin -
		// dist sup au rayonCercle
		int rayonCercle = 500;

		for (int i = 0; i < nbClub; i++) {
			for (int j = i + 1; j < nbClub; j++) {
				if (tabDistance[i][j] > rayonCercle) {
					solver.post(IntConstraintFactory.arithm(club[i], "!=", club[j]));
				}
			}
		}

		// Homogeneite distance intergroupe

		// for (int j = 0; j < nbClub; j++) {
		// IntVar sommeDistDuGroupe = VariableFactory.bounded(
		// "distance intergroupe" + j, 0, this.distMax, solver);
		// solver.post(IntConstraintFactory.scalar(matriceGroupe[j],
		// tabDistance[j], sommeDistDuGroupe));
		//
		// IntVar nFoisSommeDistDuGroupe = VariableFactory.bounded(
		// "n*distance intergroupe" + j, 0, this.distMax, solver);
		// solver.post(IntConstraintFactory.times(sommeDistDuGroupe,
		// this.tailleGroupe, nFoisSommeDistDuGroupe));
		//
		// IntVar diff = VariableFactory.bounded("diff" + j, -this.distMax,
		// this.distMax, solver);
		// IntVar[] tab = new IntVar[] { nFoisSommeDistDuGroupe, tabDist[j] };
		// int[] multi = new int[] { -1, 1 };
		// solver.post(IntConstraintFactory.scalar(tab, multi, diff));
		//
		// IntVar abs = VariableFactory.abs(diff);
		//
		// IntVar moyennePonderee = VariableFactory.bounded("moyenne ponderee"
		// + j, 0, this.distMax, solver);
		// solver.post(IntConstraintFactory.times(tabDist[j], 10,
		// moyennePonderee));
		//
		// solver.post(IntConstraintFactory.arithm(abs, "<", moyennePonderee));
		// save[j] = sommeDistDuGroupe;
		// }

		// Somme des distances

		sommeDist = VariableFactory.bounded("somme distance", 0, this.distMax, solver);
		solver.post(IntConstraintFactory.sum(tabDist, sommeDist));
		solver.post(IntConstraintFactory.arithm(sommeDist, "<", this.distMax));

		// Desiderata

		for (Desiderata d : listeDesiderata) {
			solver.post(IntConstraintFactory.arithm(club[d.getClub1()], d.getOp(), club[d.getClub2()]));
		}

	}

	public void configureSearch() {

		// Limitation en temps du probleme
		SMF.limitTime(solver, this.tempsMax);

		AbstractStrategy activity = IntStrategyFactory.activity(club, 15012011);
		solver.set(IntStrategyFactory.lastConflict(solver, activity));
		SMF.geometrical(solver, (nbClub) / 2, 2, new FailCounter(nbClub), Integer.MAX_VALUE);
		if (afficher) {
			Chatterbox.showStatisticsDuringResolution(solver, 5000);
		}
	}

	public void createSolver() {
		solver = new Solver();
	}

	public void prettyOut() {
		if (this.afficher) {
			System.out.println("Somme Distance : " + sommeDist.getValue());
			System.out.println(getSolution());
			System.out.println(getDistance());
		}
	}

	public void solve() {
		// s.findOptimalSolution(ResolutionPolicy.MINIMIZE, sommeDist);
		solver.findSolution();

		// Memorisation
		for (int i = 0; i < this.nbClub; i++) {
			this.solution[i] = club[i].getValue();
		}

		for (int i = 0; i < this.nbClub; i++) {
			this.distance[i] = tabDist[i].getValue();
		}
	}
}
