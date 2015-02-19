package main;

import presentation.BoiteDeDialogue;
import presentation.FenetreAccueil;

public class Main {

	public static void main(String[] args) {
		BoiteDeDialogue.info("Bienvenue sur le Programme GéoGroupFoot44 conçu par le District de Loire-Atlantique"
				+ "\nde Football et l’Ecole des Mines de Nantes. Cet outil vous permettra de constituer"
				+ "\nrapidement vos groupes de championnat et de réaliser une carte de ceux-ci."
				+ "\nIl pourra également vous permettre de produire des cartes de toute nature ");
		FenetreAccueil fa = new FenetreAccueil();
		fa.launch();
	}
}
