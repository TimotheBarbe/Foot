package abstraction;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindows extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainWindows() {
		setSize(1280, 1024);
		setTitle("Test");
		setContentPane(new AfficheImage("image/carte_region.png"));
		getContentPane().setLayout(new BorderLayout());
		this.setVisible(true);
	}
}
