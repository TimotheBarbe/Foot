package model;

import java.awt.Color;

public class Couleur {

	public static Color getColor(int numeroGroupe) {
		numeroGroupe = numeroGroupe % 12;
		Color c = Color.BLACK;
		switch (numeroGroupe) {
		case 0:
			c = Color.BLUE;
			break;
		case 1:
			c = Color.RED;
			break;
		case 2:
			c = new Color(150, 100, 50); // marron
			break;
		case 3:
			c = new Color(170, 170, 20); // Jaune foncé
			break;
		case 4:
			c = new Color(40, 150, 90); // Vert foncé
			break;
		case 5:
			c = new Color(70, 70, 70); // Gris foncé
			break;
		case 6:
			c = new Color(150, 60, 150); // violet
			break;
		case 7:
			c = Color.magenta;
			break;
		case 8:
			c = new Color(170, 30, 30); // Rouge foncé
			break;
		case 9:
			c = new Color(30, 50, 80); // Bleu foncé
			break;
		case 10:
			c = new Color(91, 118, 232); // Bleu clair
		break;
		case 11:
			c = new Color(118, 232, 37); // Vert pomme
		break;
		}
		return c;
	}

	public static int getForme(int numeroGroupe) {
		return numeroGroupe / 12;
	}
}
