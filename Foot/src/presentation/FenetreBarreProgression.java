package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FenetreBarreProgression extends
JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ProgressMonitor progressMontor;
	private int compteur = 0;

	public FenetreBarreProgression(int min, int max) {
		super("Exemple des Barres de progression");
		setSize(200,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Cr�er un objet ProgressMonitor
		progressMontor = new ProgressMonitor(null, "Avancement calcul de la r�partition...",
				"Initialiation . . .", min, max);

		// Mise � jour de l'etat de la barre de progression avec un Timer.
		Timer timer = new Timer(500, this);
		timer.start();
		setVisible(true);
	}

	public static void main(String args[]) {
		// D�finir les propri�t�s de la bo�te de dialogue
		UIManager.put("ProgressMonitor.progressText", "Progression du calcul...");
		UIManager.put("OptionPane.cancelButtonText", "Annuler l'op�ration");
		new FenetreBarreProgression(0, 100);
	}

	// Mise � jour chaque 0.5 seconde
	public void actionPerformed(ActionEvent e) {

		SwingUtilities.invokeLater(new Update( ));
	}

	class Update implements Runnable {
		public void run( ) {
			if (progressMontor.isCanceled( )) {
				progressMontor.close( );
				System.exit(1);
			}
			progressMontor.setProgress(compteur);
			progressMontor.setNote(compteur+" % Termin�");
		}
	}
	
	public void setCompteur(int compteur){
		this.compteur = compteur;
	}
}