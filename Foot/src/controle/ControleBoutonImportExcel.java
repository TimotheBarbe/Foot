package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import model.Obs;

public class ControleBoutonImportExcel implements ActionListener {

	private Obs obs;

	public ControleBoutonImportExcel(Obs obs) {
		this.obs = obs;
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser(new File("."));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int bouton = fileChooser.showSaveDialog(null);
		
	}

}
