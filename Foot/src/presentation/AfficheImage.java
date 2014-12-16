package presentation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import model.Club;
import model.Obs;

public class AfficheImage extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private Image image;
	private Obs obs;

	public AfficheImage(String s, Obs obs) {
		this.image = getToolkit().getImage(s);
		this.setObs(obs);
		this.addMouseListener(this);
		// Curseur
		Image iconeCurseur = getToolkit().getImage("magnifier.png");
		Cursor curs = getToolkit().createCustomCursor(iconeCurseur,
				new Point(1, 1), "monCurseur");
		this.setCursor(curs);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int zoom = obs.getZoom();
		int coinX = (int) obs.getCoinZoom().getX();
		int coinY = (int) obs.getCoinZoom().getY();
		g.drawImage(this.image, -coinX*zoom, -coinY*zoom, zoom * getWidth(), zoom
				* getHeight(), this);
		g.setFont(new Font("Arial", Font.BOLD, 12));

		for (int i = 0; i < obs.getDiv().getListe().size(); i++) {
			Club c = obs.getDiv().getListe().get(i);

			int x = zoom *(int) (c.getCoordonneesMatricielles()[0] - coinX);
			int y = zoom *(int) (c.getCoordonneesMatricielles()[1] - coinY);

			String nom = c.toString();
			g.setColor(this.getColor(this.obs.getReponseSolveur()[i]));
			if (obs.isAfficherNom()) {
				g.drawString(nom, x - 5 * nom.length() / 2, y - 10);
			}
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
		case 3:
			c = Color.YELLOW;
			break;
		case 4:
			c = Color.ORANGE;
			break;
		case 5:
			c = Color.PINK;
			break;
		case 6:
			c = Color.GRAY;
			break;
		case 7:
			c = Color.DARK_GRAY;
			break;
		case 8:
			c = Color.MAGENTA;
			break;
		case 9:
			c = Color.LIGHT_GRAY;
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

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int zoom = obs.getZoom() + 1;
			obs.setZoom(zoom);
			System.out.println(e.getX()+"  "+e.getY());
			Point nouveauCoin = new Point(e.getX() - 400 / zoom, e.getY()
					- 300 / zoom);
			System.out.println(nouveauCoin);
			obs.setCoinZoom(nouveauCoin);

		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			obs.setZoom(1);
			obs.setCoinZoom(new Point());
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {

	}

}
