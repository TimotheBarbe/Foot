package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JCheckBox;

import model.Obs;

public class ControleJBoxToutCocher implements ActionListener {

	private Obs obs;

	public ControleJBoxToutCocher(Obs obs) {
		this.obs = obs;
	}

	public void actionPerformed(ActionEvent e) {
		JCheckBox box = (JCheckBox)e.getSource();
		boolean[] tab = new boolean[obs.getListForTable().size()];
		if(box.isSelected()){
			Arrays.fill(tab, true);
		}
		else {
			Arrays.fill(tab, false);
		}
		obs.setTableVisible(tab);
	}

}
