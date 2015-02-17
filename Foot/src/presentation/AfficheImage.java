package presentation;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.sun.prism.paint.Color;

import model.Club;
import model.Couleur;
import model.EquivalentLettre;
import model.Obs;
import controle.ControleJPopMenu;

/**
 * AfficheImage permet d'afficher la carte et les clubs
 * 
 * @authors Timothé Barbe, Florent Euvrard, Cheikh Sylla
 *
 */
public class AfficheImage extends JPanel implements MouseListener,
		MouseWheelListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private Image imageCarte;
	private Obs obs;
	// coordonnee de depart du drag'n'drop
	int startX = -1, startY = -1;

	/**
	 * Cree et initialise un nouveau JPanel AfficheImage
	 * 
	 * @param obs
	 */
	public AfficheImage(Obs obs) {
		this.imageCarte = getToolkit().getImage(MainWindows.pathCarte);
		this.obs = obs;
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addMouseMotionListener(this);
		this.setLayout(new BorderLayout());
		this.creerSud();
	}

	/**
	 * Creation du bas de la fenetre : boutons zoom
	 */
	private void creerSud() {
		JPanel sud = new JPanel(new GridLayout(1, 3, 3, 3));
		JButton zPlus = new JButton("Zoom +");
		zPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoomPoint(new Point(getWidth() / 2, getHeight() / 2));
			}
		});

		JButton zMoins = new JButton("Zoom -");
		zMoins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obs.setZoom(obs.getZoom() - 1);
				if (obs.getZoom() < 2) {
					reinitialiserZoom();
				}
			}
		});

		JButton reset = new JButton("Réinitialiser Zoom");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reinitialiserZoom();
			}
		});
		sud.add(reset);
		sud.add(zMoins);
		sud.add(zPlus);
		this.add(sud, BorderLayout.SOUTH);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int zoom = obs.getZoom();
		int coinX = (int) obs.getCoinZoom().getX();
		int coinY = (int) obs.getCoinZoom().getY();

		// Si la largeur est plus petite que la longeur (au facteur 1.389 pres)
		if (getHeight() * 1.389 > getWidth()) {
			g.drawImage(this.imageCarte, (int) (-coinX * zoom
					* (double) getWidth() / 552), (int) (-coinY * zoom
					* (double) getWidth() / 552), zoom * getWidth(),
					(int) (zoom * getWidth() / 1.389), this);
			g.setFont(new Font("Arial", Font.BOLD, 12));

			// Dessin des points (avant les noms pour eviter la superposition)
			for (int i = 0; i < obs.getDiv().getListe().size(); i++) {
				if (obs.getTableVisible()[i]) {
					Club c = obs.getDiv().getListe().get(i);
					int groupe = this.obs.getReponseSolveur()[i];

					int x = (int) (zoom * (double) getWidth() / 552 * (c
							.getCoordonneesMatricielles()[0] - coinX));
					int y = (int) (zoom * (double) getWidth() / 552 * (c
							.getCoordonneesMatricielles()[1] - coinY));
					g.setColor(Couleur.getColor(groupe));
					if (Couleur.getForme(groupe) == 0) {
						g.fillOval(x, y, 10, 10);
					}
					if (Couleur.getForme(groupe) == 1) {
						g.fillRect(x, y, 10, 10);
					}
					if (Couleur.getForme(groupe) == 2) {
						g.fillPolygon(new int[] { x, x + 7, x - 7 }, new int[] {
								y, y + 7, y + 7 }, 3);
					}
				}
			}

			// Dessin des noms
			for (int i = 0; i < obs.getDiv().getListe().size(); i++) {
				if (obs.getTableVisible()[i]) {
					Club c = obs.getDiv().getListe().get(i);

					int x = (int) (zoom * (double) getWidth() / 552 * (c
							.getCoordonneesMatricielles()[0] - coinX));
					int y = (int) (zoom * (double) getWidth() / 552 * (c
							.getCoordonneesMatricielles()[1] - coinY));

					String nom = c.toString();
					g.setColor(Couleur.getColor(this.obs.getReponseSolveur()[i]));
					if (obs.getIndiceSurvole() == i) {
						g.drawString(nom, x - 5 * nom.length() / 2, y - 10);
					}
				}
			}
			// Si la largeur est plus grande que la longeur (au facteur 1.389
			// pres)
		} else {
			g.drawImage(this.imageCarte, (int) (-coinX * zoom
					* (double) getHeight() / 552 * 1.389), (int) (-coinY * zoom
					* (double) getHeight() / 552 * 1.389),
					(int) (zoom * 1.389 * getHeight()), zoom * getHeight(),
					this);
			g.setFont(new Font("Arial", Font.BOLD, 12));

			// Dessin des points (avant les noms pour eviter la superposition)
			for (int i = 0; i < obs.getDiv().getListe().size(); i++) {
				if (obs.getTableVisible()[i]) {
					Club c = obs.getDiv().getListe().get(i);
					int groupe = this.obs.getReponseSolveur()[i];

					int x = (int) (zoom * (double) getHeight() / 552 * 1.389 * (c
							.getCoordonneesMatricielles()[0] - coinX));
					int y = (int) (zoom * (double) getHeight() / 552 * 1.389 * (c
							.getCoordonneesMatricielles()[1] - coinY));

					g.setColor(Couleur.getColor(groupe));
					if (Couleur.getForme(groupe) == 0) {
						g.fillOval(x, y, 10, 10);
					}
					if (Couleur.getForme(groupe) == 1) {
						g.fillRect(x, y, 10, 10);
					}
					if (Couleur.getForme(groupe) == 2) {
						g.fillPolygon(new int[] { x, x + 7, x - 7 }, new int[] {
								y, y + 7, y + 7 }, 3);
					}
				}
			}

			// Dessin des noms
			for (int i = 0; i < obs.getDiv().getListe().size(); i++) {
				if (obs.getTableVisible()[i]) {
					Club c = obs.getDiv().getListe().get(i);

					int x = (int) (zoom * (double) getHeight() / 552 * 1.389 * (c
							.getCoordonneesMatricielles()[0] - coinX));
					int y = (int) (zoom * (double) getHeight() / 552 * 1.389 * (c
							.getCoordonneesMatricielles()[1] - coinY));

					String nom = c.toString();
					g.setColor(Couleur.getColor(this.obs.getReponseSolveur()[i]));
					if (obs.getIndiceSurvole() == i) {
						g.drawString(nom, x - 5 * nom.length() / 2, y - 10);
					}
				}
			}
		}

		// voyant de validation de solution
		if (obs.estRepartiHomogene())
			g.drawImage(getToolkit().getImage("Donnees/ok.png"), 20, 20, 40,
					40, this);
		else
			g.drawImage(getToolkit().getImage("Donnees/refus.png"), 20, 20, 40,
					40, this);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int clubclick = this.getClubPlusProche(e.getX(), e.getY());
			if (clubclick >= 0) {
				obs.setClubSelectionne(obs.getDiv().getListe().get(clubclick));
			}
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			int clubclick = this.getClubPlusProche(e.getX(), e.getY());
			if (clubclick >= 0) {
				JPopupMenu contextMenu = new JPopupMenu();
				for (int i = 0; i < obs.getDiv().getNbGroupe(); i++) {
					JMenuItem item = new JMenuItem(
							EquivalentLettre.getLettre(i));
					if (obs.getReponseSolveur()[clubclick] == i) {
						item.setIcon(new ImageIcon("Donnees/icone-tick.png"));
					}
					contextMenu.add(item);
					item.addActionListener(new ControleJPopMenu(obs, clubclick));
				}
				contextMenu.setEnabled(true);
				contextMenu.setVisible(true);
				contextMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (obs.getZoom() > 1) {
			this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			Point p = e.getPoint();
			startX = p.x;
			startY = p.y;
		}
	}

	public void mouseReleased(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if (notches < 0) {
			zoomPoint(e.getPoint());
		} else {
			reinitialiserZoom();
		}
	}

	/**
	 * Remet le zoom a 1 et le coinZoom a (0,0)
	 */
	private void reinitialiserZoom() {
		obs.setZoom(1);
		obs.setCoinZoom(new Point());
	}

	/**
	 * @param e
	 *            centre du zoom
	 */
	private void zoomPoint(Point e) {
		int zoom = obs.getZoom() + 1;
		obs.setZoom(zoom);
		Point nouveauCoin = new Point(e.x * 552 / getWidth() - 400 / (zoom),
				e.y - 300 / zoom);
		obs.setCoinZoom(nouveauCoin);
	}

	/**
	 * @param x
	 * @param y
	 * @return le Club le plus proche de (x,y)
	 */
	private int getClubPlusProche(int x, int y) {
		Point pointClick = this.getCoordonneesSansZoom(x, y);
		double distMini = 10;
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

	/**
	 * @param x
	 * @param y
	 * @return coordonnee sur la carte a l'echelle 1
	 */
	private Point getCoordonneesSansZoom(int x, int y) {
		double facteur = 1;
		if (getHeight() * 1.389 > getWidth()) {
			facteur = (double) getWidth() / 552;
		} else {
			facteur = (double) getHeight() / 552 * 1.389;
		}
		int xf = (int) (obs.getCoinZoom().getX() + x
				/ (obs.getZoom() * facteur));
		int yf = (int) (obs.getCoinZoom().getY() + y
				/ (obs.getZoom() * facteur));
		return new Point(xf, yf);
	}

	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		if (obs.getZoom() > 1) {
			int diffX = (startX - p.x) / obs.getZoom();
			int diffY = (startY - p.y) / obs.getZoom();
			int newX = obs.getCoinZoom().x + diffX;
			int newY = obs.getCoinZoom().y + diffY;
			obs.setCoinZoom(new Point(newX, newY));
			startX = p.x;
			startY = p.y;
		}
	}

	public void mouseMoved(MouseEvent e) {
		int clubSurvole = this.getClubPlusProche(e.getX(), e.getY());
		obs.setIndiceSurvole(clubSurvole);
	}
}
