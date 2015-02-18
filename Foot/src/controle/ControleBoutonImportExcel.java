package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Club;
import model.Division;
import model.Obs;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import presentation.BoiteDeDialogue;
import presentation.MainWindows;
import Excel.UtilsExcelPOI;

public class ControleBoutonImportExcel implements ActionListener {

	private JFrame mw;

	public ControleBoutonImportExcel(JFrame mw) {
		this.mw = mw;
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("xls, xlsx",
				new String[] { "xls", "xlsx" });
		fileChooser.setFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int bouton = fileChooser.showOpenDialog(null);
		if (bouton == JFileChooser.APPROVE_OPTION) {
			ArrayList<Club> listeTotale = this.getListeTotale();
			this.changeObs(fileChooser.getSelectedFile(), listeTotale);
		}
	}

	private void changeObs(File file, ArrayList<Club> listeTotale) {
		String path = file.getPath();
		Workbook workbook = null;
		try {
			/* Recuperation du classeur Excel (en lecture) */
			workbook = WorkbookFactory.create(new File(path));
		} catch (Exception e) {
			BoiteDeDialogue.error(e.getMessage());
		} finally {
			if (workbook != null) {
				Sheet sheet = workbook.getSheetAt(0);
				int nbGroupe = (UtilsExcelPOI.getNbColumns(sheet) + 1) / 3;
				
				Division d = new Division(nbGroupe, file.getName().substring(0,
						file.getName().lastIndexOf("_")));

				// DONNEES DIVISION
				for (int i = 0; i < nbGroupe; i++) {
					ArrayList<String> col = UtilsExcelPOI.getAncienneColumn(i * 3,
							workbook);
					for (int j = 2; j < col.size(); j++) {
						String selection = col.get(j);
						try {
							String id = selection.substring(
									selection.indexOf("(") + 1,
									selection.indexOf(")"));
							for (Club c : listeTotale) {
								if (c.getId() == Integer.parseInt(id)) {
									d.addClub(c);
								}
							}
						} catch (Exception ex) {
							BoiteDeDialogue.error("Mauvais format du club :"
									+ selection);
						}
					}
				}

				// DONNES SOLVEUR
				int[] reponseSolveur = new int[d.getNbClub()];
				int indice = 0;
				for (int i = 0; i < nbGroupe; i++) {
					ArrayList<String> col = UtilsExcelPOI.getAncienneColumn(i * 3,
							workbook);
					for (int j = 2; j < col.size(); j++) {
						reponseSolveur[indice] = i;
						indice++;
					}
				}

				// DONNES DISTANCE
				// TODO
				double[][] tabDist = new double[d.getNbClub()][d.getNbClub()];
				for (double[] row : tabDist)
					Arrays.fill(row, (int) (Math.random() * 12) + 1);
				for (int i = 0; i < d.getNbClub(); i++) {
					tabDist[i][i] = 0;
				}
				
				mw.setVisible(false);
				mw.dispose();

				Obs obs = new Obs(d, reponseSolveur, tabDist);
				MainWindows newFrame = new MainWindows(obs, obs.getDiv().getNom());
			}
		}
	}

	private ArrayList<Club> getListeTotale() {
		Workbook workbook = null;
		ArrayList<Club> listeTotale = new ArrayList<Club>();
		try {
			/* Recuperation du classeur Excel (en lecture) */
			workbook = WorkbookFactory.create(new File(
					"Donnees/CoordonneesGPSEquipes.xls"));

			// On recupere les numeros d'affiliation des clubs
			ArrayList<String> affiliationClubs = UtilsExcelPOI.getAncienneColumn(0,
					workbook);
			
			// On recupere le nom des clubs
			ArrayList<String> nomClubs = UtilsExcelPOI.getAncienneColumn(1, workbook);

			// On recupere la latitude des clubs
			ArrayList<String> latitudeClubs = UtilsExcelPOI.getAncienneColumn(2,
					workbook);

			// On recupere la longitude des clubs
			ArrayList<String> longitudeClubs = UtilsExcelPOI.getAncienneColumn(3,
					workbook);

			int nbClub = affiliationClubs.size();
			for (int i = 1; i < nbClub; i++) {
				double[] coordonneesGPS = {
						Double.parseDouble(latitudeClubs.get(i)),
						Double.parseDouble(longitudeClubs.get(i)) };

				Club c = new Club(nomClubs.get(i),
						(int)Double.parseDouble(affiliationClubs.get(i)),
						coordonneesGPS);

				listeTotale.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
			BoiteDeDialogue.error(e.getMessage());
		}
		return listeTotale;
	}

}
