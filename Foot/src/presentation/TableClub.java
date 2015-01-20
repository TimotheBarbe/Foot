package presentation;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableClub extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "", "Club" };
	private Object[][] data;

	public TableClub(ArrayList<String> liste) {
		data = new Object[liste.size()][2];
		for (int i = 0; i < liste.size(); i++) {
			data[i][0] = new Boolean(true);
			data[i][1] = liste.get(i);
		}
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public boolean isCellEditable(int row, int col) {
		if (col == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	public void setData(ArrayList<String> liste) {
		data = new Object[liste.size()][2];
		for (int i = 0; i < liste.size(); i++) {
			data[i][0] = new Boolean(true);
			data[i][1] = liste.get(i);
		}
	}

}
