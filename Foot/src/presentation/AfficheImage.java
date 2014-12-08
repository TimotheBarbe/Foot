package presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

import model.Club;
import model.Obs;

public class AfficheImage extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image image;
	private Obs obs;

	public AfficheImage(String s, Obs obs) {
		this.image = getToolkit().getImage(s);
		this.setObs(obs);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.image, 0, 0, getWidth(), getHeight(), this);
		g.setFont(new Font("Arial", Font.BOLD, 12));

		for (int i = 0; i < obs.getDiv().getListe().size(); i++) {
			Club c = obs.getDiv().getListe().get(i);
			int x = (int) c.getVille().getX();
			int y = (int) c.getVille().getY();
			String nom = c.toString();
			g.setColor(this.getColor(this.obs.getReponseSolveur()[i]));
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

	public Obs getObs() {
		return obs;
	}

	public void setObs(Obs obs) {
		this.obs = obs;
	}

}