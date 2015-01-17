package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import model.Club;
import model.Obs;
import presentation.BoiteDeDialogue;
import presentation.MainWindows;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class ControleBoutonExportCarte implements ActionListener {

	private Obs obs;
	private Image imageCarte;

	public ControleBoutonExportCarte(Obs obs) {
		this.obs = obs;
		try {
			imageCarte = Image.getInstance(MainWindows.pathCarte);
		} catch (Exception e) {
			BoiteDeDialogue.error(e.getMessage());
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int bouton = fileChooser.showSaveDialog(null);
		if (bouton == JFileChooser.APPROVE_OPTION) {
			boolean ok = true;
			for (int i = 0; i < obs.getDiv().getNbGroupe(); i++) {
				Document myPDF = new Document(PageSize.A4);
				String nomDuFichier = fileChooser.getSelectedFile().toString()
						+ File.separator + obs.getDiv().getNom() + "-groupe";
				try {
					FileOutputStream stream = new FileOutputStream(nomDuFichier
							+ " " + (i + 1) + ".pdf");
					PdfWriter myWriter = PdfWriter.getInstance(myPDF, stream);
					myPDF.open();
					PdfContentByte cb = myWriter.getDirectContent();
					dessinerCarte(cb, i);
					dessinerTitre(cb, i);
					dessinerLogo(cb);
					myPDF.close();
				} catch (Exception e) {
					BoiteDeDialogue.error(e.getMessage());
					ok = false;
				}
			}
			if (ok)
				BoiteDeDialogue.info("Cartes sauvegardées");
		}
	}

	private void dessinerLogo(PdfContentByte cb) {
		try {
			imageCarte = Image.getInstance(MainWindows.pathLogo);
			imageCarte.scaleToFit(100, 100);
			imageCarte.setAbsolutePosition(10, 10);
			cb.addImage(imageCarte);
		} catch (Exception e) {
			BoiteDeDialogue.error(e.getMessage());
		}

	}

	private void dessinerTitre(PdfContentByte cb, int i) throws Exception {
		BaseFont bf = BaseFont.createFont();
		cb.beginText();
		cb.setFontAndSize(bf, 20);
		cb.showTextAligned(Element.ALIGN_CENTER, obs.getDiv().getNom()
				+ " - groupe " + (i + 1), 300, 800, 0);

		cb.endText();
	}

	public void dessinerCarte(PdfContentByte cb, int groupe) throws Exception {
		Image imageCourante = imageCarte;
		imageCourante.setAbsolutePosition(20, 360);
		imageCourante.scaleToFit(600, 400);
		cb.addImage(imageCourante);
		// dessin des cercles
		cb.setLineWidth(2);
		ArrayList<Club> listeClub = obs.getDiv().getListe();
		for (int i = 0; i < listeClub.size(); i++) {
			if (obs.getReponseSolveur()[i] == groupe) {
				float x = (float) listeClub.get(i).getCoordonneesMatricielles()[0] / 2;
				float y = 750 - (float) listeClub.get(i)
						.getCoordonneesMatricielles()[1] / 2;
				cb.circle(x, y, (float) 1);
			}
		}

		cb.stroke();
		// dessin des labels
		BaseFont bf = BaseFont.createFont();
		cb.beginText();
		cb.setFontAndSize(bf, 6);
		for (int i = 0; i < listeClub.size(); i++) {
			if (obs.getReponseSolveur()[i] == groupe) {
				float x = (float) listeClub.get(i).getCoordonneesMatricielles()[0] / 2;
				float y = 750 - (float) listeClub.get(i)
						.getCoordonneesMatricielles()[1] / 2;
				cb.showTextAligned(Element.ALIGN_CENTER, listeClub.get(i)
						.getId() + "", x, y + 8, 0);
			}
		}

		cb.endText();
	}
}
