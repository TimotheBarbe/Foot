package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import model.EquivalentLettre;
import model.Obs;
import model.PrettyDate;
import presentation.BoiteDeDialogue;

public class ControleBoutonExportExcel implements ActionListener {

	private Obs obs;

	public ControleBoutonExportExcel(Obs obs) {
		this.obs = obs;
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int bouton = fileChooser.showSaveDialog(null);
		if (bouton == JFileChooser.APPROVE_OPTION) {
			WritableWorkbook workbook = null;
			boolean ok = true;
			try {
				/* On créé un nouveau worbook et on l'ouvre en écriture */
				workbook = Workbook.createWorkbook(new File(fileChooser
						.getSelectedFile().toString()
						+ "/"
						+ obs.getDiv().getNom()
						+ "_"
						+ PrettyDate.getPrettyDate() + ".xls"));

				/*
				 * On créé une nouvelle feuille (test en position 0) et on
				 * l'ouvre en écriture
				 */
				WritableSheet sheet = workbook.createSheet("solution", 0);

				this.ecrire(sheet);

				/* On ecrit le classeur */
				workbook.write();

			} catch (Exception ex) {
				ok = false;
				BoiteDeDialogue.error(ex.getMessage());
			} finally {
				if (workbook != null) {
					/* On ferme le worbook pour libérer la mémoire */
					try {
						workbook.close();
					} catch (Exception ex) {
						ok = false;
						BoiteDeDialogue.error(ex.getMessage());
					}
				}
			}
			if (ok)
				BoiteDeDialogue.info("Fichier sauvegardé");
		}
	}

	private void ecrire(WritableSheet sheet) throws Exception {
		int clubRestant = obs.getReponseSolveur().length;
		int indice = 0;
		int row = 0;
		int column = 0;
		WritableCellFormat cellFormatTitre = new WritableCellFormat();
		cellFormatTitre.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormatTitre.setAlignment(Alignment.CENTRE);

		WritableCellFormat cellFormatAutre = new WritableCellFormat();
		cellFormatAutre.setBorder(Border.ALL, BorderLineStyle.THIN);

		while (clubRestant > 0) {
			sheet.mergeCells(column, row, column + 1, row);
			Label titre = new Label(column, row, "Poule "
					+ EquivalentLettre.getLettre(column / 3), cellFormatTitre);
			sheet.addCell(titre);

			row++;
			for (int i = 0; i < obs.getReponseSolveur().length; i++) {
				if (obs.getReponseSolveur()[i] == indice) {
					row++;
					Label nom = new Label(column, row, obs.getDiv().getListe()
							.get(i).toString(), cellFormatAutre);
					sheet.addCell(nom);
					Number dist = new Number(column + 1, row,
							obs.getDistParcourue(i), cellFormatAutre);
					sheet.addCell(dist);
					clubRestant--;
				}
			}
			row = 0;
			column += 3;
			indice++;
		}

		for (int i = 0; i < obs.getDiv().getNbGroupe(); i++) {
			CellView cv = sheet.getColumnView(i * 3);
			cv.setSize(5000);
			sheet.setColumnView(i * 3, cv);
		}
	}
}
