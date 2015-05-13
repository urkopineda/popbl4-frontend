package graphicinterface;

import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class HeartRateUI {	
	ImagePanel mainHRPanel = null;
	boolean state = false;
	
	public HeartRateUI() {
		createHRPanel();
	}
	
	public void switchHRState() {
		if (state) state = false;
		else if (!state) state = true;
	}
	
	public boolean getHRState() {
		return state;
	}
	
	private Container createHRPanel() {
		if (state) {
			mainHRPanel = new ImagePanel(new ImageIcon("img/heart_on.png").getImage());
		} else if (!state) {
			mainHRPanel = new ImagePanel(new ImageIcon("img/heart_off.png").getImage());
		}
		return mainHRPanel;
	}

	public JPanel getPane() {
		return mainHRPanel;
	}
	
	public void regeneratePanel() {
		createHRPanel();
	}
}
