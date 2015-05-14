package graphicinterface;

import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class HeartRateUI {	
	ImagePanel mainHRPanel = null;
	
	public HeartRateUI(int state) {
		createHRPanel(state);
	}
	
	public int switchHRState(int state) {
		if (state == -1) state = 0;
		else if (state == 0) state = 1;
		else if (state == 1) state = 0;
		createHRPanel(state);
		return state;
	}
	
	private Container createHRPanel(int state) {
		if (state == -1) {
			mainHRPanel = new ImagePanel(new ImageIcon("img/heart_off.png").getImage());
		} else if (state == 0) {
			mainHRPanel = new ImagePanel(new ImageIcon("img/heart_on_up.png").getImage());
		} else if (state == 1) {
			mainHRPanel = new ImagePanel(new ImageIcon("img/heart_on_down.png").getImage());
		}
		return mainHRPanel;
	}

	public JPanel getPane() {
		return mainHRPanel;
	}
}
