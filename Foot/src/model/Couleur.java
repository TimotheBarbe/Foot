package model;

import java.awt.Color;

public class Couleur {

	public static Color getColor(int numeroGroupe) {
		numeroGroupe = numeroGroupe % 10;
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
			c = new Color(170, 170, 20); // Jaune fonc�
			break;
		case 4:
			c = new Color(40, 150, 90); // Vert fonc�
			break;
		case 5:
			c = new Color(70, 70, 70); // Gris fonc�
			break;
		case 6:
			c = new Color(150, 60, 150); // violet
			break;
		case 7:
			c = Color.magenta;
			break;
		case 8:
			c = new Color(150, 50, 60); // Rouge fonc�
			break;
		case 9:
			c = new Color(30, 50, 80); // Bleu fonc�
			break;
		}
		return c;
	}

	public static int getForme(int numeroGroupe) {
		return numeroGroupe / 10;
	}
}
