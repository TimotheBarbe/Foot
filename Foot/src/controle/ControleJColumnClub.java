package controle;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Club;
import model.Obs;
import presentation.BoiteDeDialogue;
import presentation.FrameClub;
import presentation.TableClub;

public class ControleJColumnClub implements ListSelectionListener, Observer,
		MouseListener {

	private Obs obs;
	private JTable table;
	private TableClub model;

	public ControleJColumnClub(Obs obs, JTable table, TableClub model) {
		this.obs = obs;
		this.table = table;
		this.model = model;
	}

	public void valueChanged(ListSelectionEvent e) {
		DefaultListSelectionModel liste = (DefaultListSelectionModel) e
				.getSource();
		try {
			String selection = obs.getListForTable().get(
					table.convertRowIndexToModel(liste
							.getAnchorSelectionIndex()));
			String id = selection.substring(selection.indexOf("(") + 1,
					selection.indexOf(")"));
			Club c = obs.getDiv().getClubById(Integer.parseInt(id));
			this.obs.setClubSelectionne(c);
			this.obs.setIndiceSurvole(obs.getDiv().getListe().indexOf(c));
		} catch (Exception ex) {

		}
	}

	public void update(Observable o, Object message) {
		Integer iMessage = (Integer) message;
		if (iMessage == Obs.CHANGEMENT_REPONSE_SOLVEUR) {
			model.setData(obs.getListForTable());
			//table.repaint();
		}
		if (iMessage == Obs.CHANGEMENT_CLUB_COURANT
				|| iMessage == Obs.CHANGEMENT_REPONSE_SOLVEUR) {
			if (obs.getClubSelectionne() != null) {
				for (int i = 0; i < table.getRowCount(); i++) {
					if (table.getValueAt(i, 1).equals(
							"  " + obs.getClubSelectionne())) {
						table.setRowSelectionInterval(i, i);
						obs.setIndiceJListClubSelection(i);
					}
				}
			}
			table.scrollRectToVisible(new Rectangle(table.getCellRect(
					table.getSelectedRow(), 0, true)));
		}

	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			try {
				String selection = table.getValueAt(table.getSelectedRow(), 1)
						.toString();
				String id = selection.substring(selection.indexOf("(") + 1,
						selection.indexOf(")"));
				Club c = obs.getDiv().getClubById(Integer.parseInt(id));
				FrameClub frame = new FrameClub(c, obs);
			} catch (Exception ex) {

			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}
}
