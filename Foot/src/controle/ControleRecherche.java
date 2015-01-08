package controle;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import model.Obs;

public class ControleRecherche implements DocumentListener {

	private Obs obs;
	
	public ControleRecherche(Obs obs){
		this.obs = obs;
	}
	
	public void changedUpdate(DocumentEvent e) {
	}

	public void insertUpdate(DocumentEvent e) {
		try {
			majObs(e.getDocument().getText(0, e.getDocument().getLength()));
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	public void removeUpdate(DocumentEvent e) {
		try {
			majObs(e.getDocument().getText(0, e.getDocument().getLength()));
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	private void majObs(String field){
		System.out.println(field+"  "+field.length());		
	}

}
