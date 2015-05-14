package graphicinterface;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CronometerUI {
	JPanel mainCronometerPanel = null;
	JLabel cronometerNumbers = null;
	
	public CronometerUI() {
		createCronometerPanel();
	}

	private Container createCronometerPanel() {
		mainCronometerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		mainCronometerPanel.add(WindowMaker.createJLabel(cronometerNumbers, "00:00:00", 75));
		mainCronometerPanel.add(WindowMaker.createJLabel(cronometerNumbers, "00:00:00", 75));
		return mainCronometerPanel;
	}
	
	public JPanel getPane() {
		return mainCronometerPanel;
	}
}
