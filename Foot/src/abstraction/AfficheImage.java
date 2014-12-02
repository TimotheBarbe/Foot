package abstraction;

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

	public AfficheImage(String s, Division listeClub) {
		this.carte = getToolkit().getImage(s);
		this.listeClub = listeClub;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.carte, 0, 0, getWidth(), getHeight(), this);

		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial", Font.BOLD, 12));

		for (Club c : listeClub.getListe()) {
			int x = (int) c.getVille().getX();
			int y = (int) c.getVille().getY();
			String nom = c.toString();
			g.drawString(nom, x - 5 * nom.length() / 2, y - 10);
			g.fillOval(x, y, 10, 10);
		}
	}
}