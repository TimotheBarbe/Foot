package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import model.Obs;

public class ControleJBoxToutCocher implements ActionListener {

	private Obs obs;
	private JLabel labelToutCocher;
	
	public ControleJBoxToutCocher(Obs obs, JLabel labelToutCocher) {
		this.obs = obs;
		this.labelToutCocher = labelToutCocher;
	}

	public void actionPerformed(ActionEvent e) {
		JCheckBox box = (JCheckBox) e.getSource();
		boolean[] tab = new boolean[obs.getListForTable().size()];
		if (box.isSelected()) {
			Arrays.fill(tab, true);
			labelToutCocher.setText("Tout décocher");
		} else {
			Arrays.fill(tab, false);
			labelToutCocher.setText("Tout cocher");
		}
		obs.setTableVisible(tab);
	}

}
