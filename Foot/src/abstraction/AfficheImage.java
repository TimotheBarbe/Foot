package abstraction;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class AfficheImage extends JPanel {
	Image eau;

	AfficheImage(String s) {
		eau = getToolkit().getImage(s);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(eau, 0, 0, getWidth(), getHeight(), this);
	}
}