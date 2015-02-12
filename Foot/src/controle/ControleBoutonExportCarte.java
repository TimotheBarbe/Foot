package controle;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import model.Club;
import model.Couleur;
import model.EquivalentLettre;
import model.Obs;
import model.PrettyDate;
import presentation.BoiteDeDialogue;
import presentation.MainWindows;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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
							+ " " + EquivalentLettre.getLettre(i) + "_"
							+ PrettyDate.getPrettyDate() + ".pdf");
					PdfWriter myWriter = PdfWriter.getInstance(myPDF, stream);
					myPDF.open();
					PdfContentByte cb = myWriter.getDirectContent();
					dessinerCarte(cb, i);
					dessinerTable(cb, i);
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

	private void dessinerTable(PdfContentByte cb, int groupe) throws Exception {
		PdfPTable table = new PdfPTable(3);
		BaseFont bf = BaseFont.createFont();
		cb.setFontAndSize(bf, 8);
		table.setTotalWidth(new float[] { 50, 250, 60 });
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8);
		ArrayList<Club> listeClub = obs.getDiv().getListe();
		// En-tete
		table.addCell(new PdfPCell(new Paragraph("ID", font)));
		table.addCell(new PdfPCell(new Paragraph("Nom", font)));
		table.addCell(new PdfPCell(new Paragraph("Distance (km)", font)));
		// clubs du groupe 'groupe'
		for (int i = 0; i < listeClub.size(); i++) {
			if (obs.getReponseSolveur()[i] == groupe) {
				table.addCell(new PdfPCell(new Paragraph(listeClub.get(i)
						.getId() + "", font)));
				table.addCell(new PdfPCell(new Paragraph(listeClub.get(i)
						.getNom(), font)));
				table.addCell(new PdfPCell(new Paragraph(obs
						.getDistParcourue(i) + "", font)));
			}
		}
		table.writeSelectedRows(0, -1, 100, 350, cb);
	}

	private void dessinerLogo(PdfContentByte cb) {
		try {
			Image imageLogo = Image.getInstance(MainWindows.pathLogo);
			imageLogo.scaleToFit(100, 100);
			imageLogo.setAbsolutePosition(500, 10);
			cb.addImage(imageLogo);
		} catch (Exception e) {
			BoiteDeDialogue.error(e.getMessage());
		}

	}

	private void dessinerTitre(PdfContentByte cb, int i) throws Exception {
		BaseFont bf = BaseFont.createFont();
		cb.beginText();
		cb.setFontAndSize(bf, 20);
		cb.showTextAligned(Element.ALIGN_CENTER, obs.getDiv().getNom()
				+ " - groupe " + EquivalentLettre.getLettre(i), 300, 800, 0);

		cb.endText();
	}

	public void dessinerCarte(PdfContentByte cb, int groupe) throws Exception {
		Image imageCourante = imageCarte;
		imageCourante.setAbsolutePosition(20, 360);
		imageCourante.scaleToFit(600, 400);
		cb.addImage(imageCourante);
		// dessin des cercles
		cb.setLineWidth(2);
		Color c = Couleur.getColor(groupe);
		cb.setColorStroke(new BaseColor(c.getRed(), c.getGreen(), c.getBlue()));
		ArrayList<Club> listeClub = obs.getDiv().getListe();
		for (int i = 0; i < listeClub.size(); i++) {
			if (obs.getReponseSolveur()[i] == groupe) {
				float x = (float) listeClub.get(i).getCoordonneesMatricielles()[0];
				float y = 750 - (float) listeClub.get(i)
						.getCoordonneesMatricielles()[1];
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
				float x = (float) listeClub.get(i).getCoordonneesMatricielles()[0];
				float y = 750 - (float) listeClub.get(i)
						.getCoordonneesMatricielles()[1];
				cb.showTextAligned(Element.ALIGN_CENTER, listeClub.get(i)
						.getId() + "", x, y + 8, 0);
			}
		}

		cb.endText();
	}
}
