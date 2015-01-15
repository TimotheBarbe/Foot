package presentation;

import javax.swing.JOptionPane;

public class BoiteDeDialogue {

	public static void info(String message) {
		JOptionPane.showMessageDialog(null, message, "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void error(String message) {
		JOptionPane.showMessageDialog(null, message, "Erreur",
				JOptionPane.ERROR_MESSAGE);
	}

}
