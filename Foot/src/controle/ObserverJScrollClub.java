package controle;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import model.Obs;

public class ObserverJScrollClub implements Observer {

	private JScrollPane listeClub;
	private Obs obs;
	
	public ObserverJScrollClub(Obs obs, JScrollPane listeClub) {
		this.listeClub = listeClub;
		this.obs = obs;
	}

	public void update(Observable o, Object message) {
		Integer iMessage = (Integer) message;
		if (iMessage == Obs.CHANGEMENT_CLUB) {
			//this.listeClub.getVerticalScrollBar().setValue(obs.getIndiceClick()*20);
		}		
	}

}
