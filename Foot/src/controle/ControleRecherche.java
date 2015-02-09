package controle;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import presentation.TableClub;

public class ControleRecherche implements DocumentListener {

	private TableRowSorter<TableClub> sorter;
	private JTextField filterText;


	public ControleRecherche(TableRowSorter<TableClub> sorter,
			JTextField filterText) {
		this.sorter = sorter;
		this.filterText = filterText;
	}

	public void changedUpdate(DocumentEvent e) {
		newFilter();
	}

	public void insertUpdate(DocumentEvent e) {
		newFilter();
	}

	public void removeUpdate(DocumentEvent e) {
		newFilter();
	}

	private void newFilter() {
		RowFilter<TableClub, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + filterText.getText(), 1);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

}
