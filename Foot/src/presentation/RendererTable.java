package presentation;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import model.Couleur;
import model.EquivalentLettre;
import model.Obs;

public class RendererTable extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	private Obs obs;

	public RendererTable(Obs obs) {
		this.obs = obs;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);

		if (!isSelected && column == 2) {
			setHorizontalAlignment(SwingConstants.RIGHT);
			String nom = table.getModel()
					.getValueAt(table.convertRowIndexToModel(row), 1)
					.toString();
			if (nom.startsWith(" ")) {
				setBackground(table.getBackground());
			} else {
				int groupe = EquivalentLettre.getIndice(nom.substring(6, 7));
				setBackground(Couleur.getColor(groupe));
			}
		} else {
			setHorizontalAlignment(SwingConstants.LEFT);
			setBackground(table.getBackground());
		}

		return this;
	}
}
