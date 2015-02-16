package controle;

import java.awt.Color;
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

public class ControleBoutonExportCarte {

	private Obs obs;
	private Image imageCarte;

	public ControleBoutonExportCarte(Obs obs, boolean carteTotal,
			boolean cartesGroupe, boolean carteSelection) {
		this.obs = obs;
		try {
			imageCarte = Image.getInstance(MainWindows.pathCarte);
		} catch (Exception e) {
			BoiteDeDialogue.error(e.getMessage());
		}
		if (carteTotal || cartesGroupe || carteSelection) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int bouton = fileChooser.showSaveDialog(null);
			if (bouton == JFileChooser.APPROVE_OPTION) {
				boolean ok = true;
				if (carteTotal)
					ok &= saveCarteTotale(fileChooser);
				if (cartesGroupe)
					ok &= saveCartesGroupes(fileChooser);
				if (carteSelection)
					ok &= saveCarteSelection(fileChooser);
				if (ok)
					BoiteDeDialogue.info("Cartes sauvegardées");
			}
		}
	}

	private boolean saveCarteSelection(JFileChooser fileChooser) {
		// TODO
		return true;
	}

	private boolean saveCarteTotale(JFileChooser fileChooser) {
		boolean ok = true;
		Document myPDF = new Document(PageSize.A4);
		String nomDuFichier = fileChooser.getSelectedFile().toString()
				+ File.separator + obs.getDiv().getNom();
		try {
			FileOutputStream stream = new FileOutputStream(nomDuFichier + "_"
					+ PrettyDate.getPrettyDate() + ".pdf");
			PdfWriter myWriter = PdfWriter.getInstance(myPDF, stream);
			myPDF.open();
			PdfContentByte cb = myWriter.getDirectContent();
			dessinerCarte(cb);
			for (int i = 0; i < obs.getDiv().getNbGroupe(); i++) {
				dessinerPoints(cb, i, false);
			}
			dessinerTitre(cb, -1);
			dessinerLogo(cb);
			myPDF.close();
		} catch (Exception e) {
			BoiteDeDialogue.error(e.getMessage());
			ok = false;
		}
		return ok;
	}

	private boolean saveCartesGroupes(JFileChooser fileChooser) {
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
				dessinerCarte(cb);
				dessinerPoints(cb, i, true);
				dessinerTable(cb, i);
				dessinerTitre(cb, i);
				dessinerLogo(cb);
				myPDF.close();
			} catch (Exception e) {
				BoiteDeDialogue.error(e.getMessage());
				ok = false;
			}
		}
		return ok;
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

	private void dessinerLogo(PdfContentByte cb) throws Exception {
		Image imageLogo = Image.getInstance(MainWindows.pathLogo);
		imageLogo.scaleToFit(100, 100);
		imageLogo.setAbsolutePosition(490, 10);
		cb.addImage(imageLogo);
	}

	private void dessinerTitre(PdfContentByte cb, int i) throws Exception {
		BaseFont bf = BaseFont.createFont();
		cb.beginText();
		cb.setFontAndSize(bf, 20);
		if (i >= 0) {
			cb.showTextAligned(Element.ALIGN_CENTER, obs.getDiv().getNom()
					+ " - groupe " + EquivalentLettre.getLettre(i), 300, 800, 0);
		}
		if (i == -1) {
			cb.showTextAligned(Element.ALIGN_CENTER, obs.getDiv().getNom(),
					300, 800, 0);
		}
		cb.endText();
	}

	public void dessinerCarte(PdfContentByte cb) throws Exception {
		Image imageCourante = imageCarte;
		imageCourante.setAbsolutePosition(20, 360);
		imageCourante.scaleToFit(600, 400);
		cb.addImage(imageCourante);
	}

	public void dessinerPoints(PdfContentByte cb, int groupe, boolean label)
			throws Exception {
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
		if (label) {
			BaseFont bf = BaseFont.createFont();
			cb.beginText();
			cb.setFontAndSize(bf, 6);
			for (int i = 0; i < listeClub.size(); i++) {
				if (obs.getReponseSolveur()[i] == groupe) {
					float x = (float) listeClub.get(i)
							.getCoordonneesMatricielles()[0];
					float y = 750 - (float) listeClub.get(i)
							.getCoordonneesMatricielles()[1];
					cb.showTextAligned(Element.ALIGN_CENTER, listeClub.get(i)
							.getId() + "", x, y + 8, 0);
				}
			}
			cb.endText();
		}

	}
}
