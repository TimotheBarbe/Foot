package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Club;
import model.Desiderata;
import model.Obs;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import presentation.BoiteDeDialogue;
import Excel.UtilsExcelPOI;

public class ControleBoutonDesiderata implements ActionListener {

	private JFrame parent;
	private Obs obs;

	public ControleBoutonDesiderata(JFrame parent, Obs obs) {
		this.parent = parent;
		this.obs = obs;
	}

	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("xls, xlsx",
				new String[] { "xls", "xlsx" });
		fileChooser.setFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int bouton = fileChooser.showOpenDialog(null);
		if (bouton == JFileChooser.APPROVE_OPTION) {
			this.changeObs(fileChooser.getSelectedFile());
		}
	}

	private void changeObs(File file) {
		String path = file.getPath();
		Workbook workbook = null;
		try {
			/* Recuperation du classeur Excel (en lecture) */
			workbook = WorkbookFactory.create(new File(path));
			if (workbook != null) {
				ArrayList<Desiderata> listeDe = new ArrayList<Desiderata>();
				int nbDesiderata = (UtilsExcelPOI
						.getAncienneColumn(0, workbook).size());
				for (int i = 0; i < nbDesiderata; i++) {
					int id1 = (int) Double.parseDouble(UtilsExcelPOI
							.getAncienneColumn(0, workbook).get(i));
					int id2 = (int) Double.parseDouble(UtilsExcelPOI
							.getAncienneColumn(2, workbook).get(i));
					String op = UtilsExcelPOI.getAncienneColumn(1, workbook)
							.get(i);
					ArrayList<Club> listeClub = new ArrayList<Club>();
					listeClub.addAll(obs.getDiv().getListe());
					listeDe.add(new Desiderata(id1, id2, op, listeClub));
				}
				obs.setDesiderata(listeDe);
				parent.dispose();	
			}
		} catch (Exception e) {
			BoiteDeDialogue.error(e.getMessage());
		}
		finally{
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
