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

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import model.Club;
import model.Division;
import model.Obs;
import presentation.BoiteDeDialogue;
import presentation.MainWindows;

public class ControleBoutonImportExcel implements ActionListener {

	private JFrame mw;
	
	public ControleBoutonImportExcel(JFrame mw) {
		this.mw = mw;
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("xls", new String[] {"xls"});
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
			workbook = Workbook.getWorkbook(new File(path));
		} catch (Exception e) {
			BoiteDeDialogue.error(e.getMessage());
		} finally {
			if (workbook != null) {
				Sheet sheet = workbook.getSheet(0);
				int nbGroupe = (sheet.getColumns() + 1) / 3;

				Division d = new Division(nbGroupe, file.getName().substring(0,
						file.getName().lastIndexOf("_")));

				// DONNEES DIVISION
				for (int i = 0; i < nbGroupe; i++) {
					Cell[] col = sheet.getColumn(i * 3);
					for (int j = 2; j < col.length; j++) {
						String selection = col[j].getContents();
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
					Cell[] col = sheet.getColumn(i * 3);
					for (int j = 2; j < col.length; j++) {
						reponseSolveur[indice] = i;
						indice++;
					}
				}

				// DONNES DISTANCE
				// TODO
				int[][] tabDist = new int[d.getNbClub()][d.getNbClub()];
				for (int[] row : tabDist)
					Arrays.fill(row, (int) (Math.random() * 12) + 1);
				for (int i = 0; i < d.getNbClub(); i++) {
					tabDist[i][i] = 0;
				}
				
				mw.setVisible(false);
				mw.dispose();
				
				Obs obs = new Obs(d, reponseSolveur, tabDist);
				MainWindows test = new MainWindows(obs, obs.getDiv().getNom());
				
				workbook.close();
			}
		}
	}

	private ArrayList<Club> getListeTotale() {
		Workbook workbook = null;
		ArrayList<Club> listeTotale = new ArrayList<Club>();
		try {
			/* Recuperation du classeur Excel (en lecture) */
			workbook = Workbook.getWorkbook(new File(
					"Donnees/CoordonneesGPSEquipes.xls"));

			Sheet sheet = workbook.getSheet(0);

			// On recupere les numeros d'affiliation des clubs
			Cell[] affiliationClubs = sheet.getColumn(0);

			// On recupere le nom des clubs
			Cell[] nomClubs = sheet.getColumn(1);

			// On recupere la latitude des clubs
			Cell[] latitudeClubs = sheet.getColumn(2);

			// On recupere la longitude des clubs
			Cell[] longitudeClubs = sheet.getColumn(3);

			int nbClub = affiliationClubs.length;

			for (int i = 1; i < nbClub; i++) {
				double[] coordonneesGPS = {
						Double.parseDouble(latitudeClubs[i].getContents()),
						Double.parseDouble(longitudeClubs[i].getContents()) };

				Club c = new Club(nomClubs[i].getContents(),
						Integer.parseInt(affiliationClubs[i].getContents()),
						coordonneesGPS);

				listeTotale.add(c);
			}
		} catch (Exception e) {
			BoiteDeDialogue.error(e.getMessage());
		} finally {
			if (workbook != null) {
				/* On ferme le worbook pour liberer la memoire */
				workbook.close();
			}
		}
		return listeTotale;
	}

}
