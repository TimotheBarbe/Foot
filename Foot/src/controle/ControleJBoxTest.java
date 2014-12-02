package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Division;

public class ControleJBoxTest implements ActionListener{

	private Division d;
	
	public ControleJBoxTest(Division d){
		this.d =d;
	}
	
	public void actionPerformed(ActionEvent e) {
		d.removeClub(1);
	}

}
