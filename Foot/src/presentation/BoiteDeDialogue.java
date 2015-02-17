package presentation;

import javax.swing.JOptionPane;

/**
 * @authors Timothé Barbe, Florent Euvrard, Cheikh Sylla
 * 
 */
public class BoiteDeDialogue {

	/**
	 * Boite de dialogue de type JOptionPane.INFORMATION_MESSAGE
	 * 
	 * @param message
	 */
	public static void info(String message) {
		JOptionPane.showMessageDialog(null, message, "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Boite de dialogue de type JOptionPane.ERROR_MESSAGE
	 * 
	 * @param message
	 */
	public static void error(String message) {
		JOptionPane.showMessageDialog(null, message, "Erreur",
				JOptionPane.ERROR_MESSAGE);
	}

}
