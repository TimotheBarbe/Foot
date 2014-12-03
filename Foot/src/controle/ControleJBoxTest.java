package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Division;
import model.Obs;

public class ControleJBoxTest implements ActionListener{

	private Obs obs;
	
	public ControleJBoxTest(Obs obs){
		this.obs = obs;
	}
	
	public void actionPerformed(ActionEvent e) {
		obs.changeGroupe(1, (int)(Math.random()*3));
	}

}
