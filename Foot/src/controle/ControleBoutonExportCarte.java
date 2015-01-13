package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class ControleBoutonExportCarte implements ActionListener {

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
				PdfWriter MyWriter = PdfWriter.getInstance(myPDF, stream);
				myPDF.open();
				PdfContentByte cb = MyWriter.getDirectContent();
				Image imageCourante = Image.getInstance("carte_region.jpg");
				imageCourante.setAbsolutePosition(30, 30);
				cb.addImage(imageCourante);
				myPDF.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
