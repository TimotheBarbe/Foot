package presentation;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.Club;
import model.Obs;

public class TableClub extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "", "Club" };
	private Object[][] data;
	private Obs obs;

	public TableClub(Obs obs) {
		this.obs = obs;
		ArrayList<String> liste = obs.getListForTable();
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
		if (getIndexClubChanged(row) >= 0) {
			boolean[] columnToBool = obs.getTableVisible();
			columnToBool[getIndexClubChanged(row)] = (boolean) value;
			obs.setTableVisible(columnToBool);
		} else {
			this.changementPouleEntiere(value, row);
		}
	}

	private void changementPouleEntiere(Object value, int row) {
		row++;
		while (getIndexClubChanged(row) >= 0) {
			setValueAt(value, row, 0);
			row++;
		}
	}

	private int getIndexClubChanged(int row) {
		int rep = -1;
		try {
			String selection = (String) data[row][1];
			String id = selection.substring(selection.indexOf("(") + 1,
					selection.indexOf(")"));
			Club c = obs.getDiv().getClubById(Integer.parseInt(id));
			rep = obs.getDiv().getListe().indexOf(c);
		} catch (Exception e) {

		}
		return rep;
	}

	public void setData(ArrayList<String> liste) {
		data = new Object[liste.size()][2];
		for (int i = 0; i < liste.size(); i++) {
			data[i][0] = new Boolean(true);
			data[i][1] = liste.get(i);
			if (getIndexClubChanged(i) > 0) {
				data[i][0] = obs.getTableVisible()[getIndexClubChanged(i)];
			}
		}
	}
}
