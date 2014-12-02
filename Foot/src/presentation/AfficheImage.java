package presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import model.Club;
import model.Division;

public class AfficheImage extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image carte;
	private Division listeClub;
	private int[] reponseSolveur;

	public AfficheImage(String s, Division listeClub, int[] reponseSolveur) {
		this.carte = getToolkit().getImage(s);
		this.listeClub = listeClub;
		this.reponseSolveur = reponseSolveur;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.carte, 0, 0, getWidth(), getHeight(), this);
		g.setFont(new Font("Arial", Font.BOLD, 12));

		for (int i = 0; i < listeClub.getListe().size(); i++) {
			Club c = listeClub.getListe().get(i);
			int x = (int) c.getVille().getX();
			int y = (int) c.getVille().getY();
			String nom = c.toString();
			g.setColor(this.getColor(this.reponseSolveur[i]));
			g.drawString(nom, x - 5 * nom.length() / 2, y - 10);
			g.fillOval(x, y, 10, 10);
		}
	}

	private Color getColor(int numeroGroupe) {
		Color c = Color.BLACK;
		switch (numeroGroupe) {
		case 0:
			c = Color.BLUE;
			break;
		case 1:
			c = Color.RED;
			break;
		case 2:
			c = Color.GREEN;
			break;
		}
		return c;
	}
}