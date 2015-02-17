package presentation;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import model.Couleur;
import model.EquivalentLettre;

/**
 * Renderer de la table des clubs
 * 
 * @authors Timothé Barbe, Florent Euvrard, Cheikh Sylla
 *
 */
public class RendererTable extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);

		// permet de ne pas decolorer les cases colorees lors de leur selection
		boolean enterLoop = false;

		String nom = table.getModel()
				.getValueAt(table.convertRowIndexToModel(row), 1).toString();

		if (column == 2) {
			setHorizontalAlignment(SwingConstants.RIGHT);
			if (nom.startsWith(" ")) {
				setBackground(table.getBackground());
			} else {
				enterLoop = true;
				int groupe = EquivalentLettre.getIndice(nom.substring(6, 8).trim());
				setBackground(Couleur.getColor(groupe));
			}
		} else {
			if (column == 1 && !nom.startsWith(" ")) {
				setFont(new Font("Arial", Font.BOLD, 12));
			}
			setHorizontalAlignment(SwingConstants.LEFT);
			setBackground(table.getBackground());
		}

		if ((isSelected || hasFocus) && !enterLoop) {
			setBackground(javax.swing.UIManager.getDefaults().getColor(
					"List.selectionBackground"));
		}

		return this;
	}
}
