package presentation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import model.Club;
import model.Obs;

public class AfficheImage extends JPanel implements MouseListener,
		MouseWheelListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private Image image;
	private Obs obs;

	public AfficheImage(String s, Obs obs) {
		this.image = getToolkit().getImage(s);
		this.obs = obs;
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addMouseMotionListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int zoom = obs.getZoom();
		int coinX = (int) obs.getCoinZoom().getX();
		int coinY = (int) obs.getCoinZoom().getY();
		g.drawImage(this.image, -coinX * zoom, -coinY * zoom,
				zoom * getWidth(), zoom * getHeight(), this);
		g.setFont(new Font("Arial", Font.BOLD, 12));

		// Dessin des points (avant les noms pour eviter la superposition)
		for (int i = 0; i < obs.getDiv().getListe().size(); i++) {
			Club c = obs.getDiv().getListe().get(i);

			int x = zoom * (int) (c.getCoordonneesMatricielles()[0] - coinX);
			int y = zoom * (int) (c.getCoordonneesMatricielles()[1] - coinY);

			g.setColor(this.getColor(this.obs.getReponseSolveur()[i]));
			g.fillOval(x, y, 10, 10);
		}

		// Dessin des noms
		for (int i = 0; i < obs.getDiv().getListe().size(); i++) {
			Club c = obs.getDiv().getListe().get(i);

			int x = zoom * (int) (c.getCoordonneesMatricielles()[0] - coinX);
			int y = zoom * (int) (c.getCoordonneesMatricielles()[1] - coinY);

			String nom = c.toString();
			g.setColor(this.getColor(this.obs.getReponseSolveur()[i]));
			if (obs.isAfficherNom() || obs.getIndiceSurvole() == i) {
				g.drawString(nom, x - 5 * nom.length() / 2, y - 10);
			}
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
			c = new Color(150, 50, 60); // Rouge foncé
			break;
		case 9:
			c = new Color(30, 50, 80); // Bleu foncé
			break;
		}
		return c;
	}

	public void mouseClicked(MouseEvent e) {
		int clubclick = this.getClubPlusProche(e.getX(), e.getY());
		if (clubclick >= 0) {
			obs.setClubSelectionne(obs.getDiv().getListe().get(clubclick));
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

	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if (notches < 0) {
			int zoom = obs.getZoom() + 1;
			obs.setZoom(zoom);
			Point nouveauCoin = new Point(e.getX() - 400 / zoom, e.getY() - 300
					/ zoom);
			obs.setCoinZoom(nouveauCoin);

		} else {
			obs.setZoom(1);
			obs.setCoinZoom(new Point());
		}
	}

	private int getClubPlusProche(int x, int y) {
		Point pointClick = this.getCoordonneesSansZoom(x, y);
		double distMini = 20;
		int rep = -1;
		for (int i = 0; i < obs.getDiv().getListe().size(); i++) {
			Club c = obs.getDiv().getListe().get(i);
			Point p = new Point((int) c.getCoordonneesMatricielles()[0],
					(int) c.getCoordonneesMatricielles()[1]);
			double dist = p.distance(pointClick);
			if (dist < distMini) {
				rep = i;
				distMini = dist;
			}
		}
		return rep;
	}

	private Point getCoordonneesSansZoom(int x, int y) {
		int xf = (int) (obs.getCoinZoom().getX() + x / obs.getZoom());
		int yf = (int) (obs.getCoinZoom().getY() + y / obs.getZoom());
		return new Point(xf, yf);
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		int clubSurvole = this.getClubPlusProche(e.getX(), e.getY());
		obs.setIndiceSurvole(clubSurvole);
	}
}
