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

	public ControleBoutonExportCarte(Obs obs, String pathCarte) {
		this.obs = obs;
		try {
			imageCarte = Image.getInstance(pathCarte);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int bouton = fileChooser.showSaveDialog(null);
		if (bouton == JFileChooser.APPROVE_OPTION) {
			Document myPDF = new Document(PageSize.A4);
			String nomDuFichier = fileChooser.getSelectedFile().toString()
					+ File.separator + "carte.pdf";
			try {
				FileOutputStream stream = new FileOutputStream(nomDuFichier);
				PdfWriter myWriter = PdfWriter.getInstance(myPDF, stream);
				myPDF.open();
				PdfContentByte cb = myWriter.getDirectContent();
				dessinerCarte(cb);
				myPDF.close();
				BoiteDeDialogue.info("Carte sauvegardée");
			} catch (Exception e) {
				BoiteDeDialogue.error(e.getMessage());
			}
		}
	}

	public void dessinerCarte(PdfContentByte cb) throws Exception {
		Image imageCourante = imageCarte;
		imageCourante.setAbsolutePosition(20, 410);
		imageCourante.scaleToFit(600, 400);
		cb.addImage(imageCourante);
		// dessin des cercles
		cb.setLineWidth(1);
		ArrayList<Club> listeClub = obs.getDiv().getListe();
		for (Club c : listeClub) {
			cb.circle((float) c.getCoordonneesMatricielles()[0] / 2,
					800 - (float) c.getCoordonneesMatricielles()[1] / 2, (float) 0.5);
		}

		cb.stroke();
		// dessin des labels
		BaseFont bf = BaseFont.createFont();
		cb.beginText();
		cb.setFontAndSize(bf, 12);
		cb.showTextAligned(Element.ALIGN_CENTER, "club 1 blabla", 0, 0, 0);
		cb.endText();
	}
}
