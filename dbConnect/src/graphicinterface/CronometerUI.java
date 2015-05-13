package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CronometerUI {
	JPanel mainCronometerPanel = null;
	JLabel cronometerNumbers = null;
	
	public CronometerUI() {
		createCronometerPanel();
	}

	private Container createCronometerPanel() {
		mainCronometerPanel = new JPanel(new BorderLayout());
		mainCronometerPanel.add(createJLabel(cronometerNumbers, "00:00:00", 100), BorderLayout.CENTER);
		return mainCronometerPanel;
	}
	
	public JPanel getPane() {
		return mainCronometerPanel;
	}
	
	/**
	 * Método para crear JLabels.
	 * 
	 * @param newJLabel
	 * @param text
	 * @param size
	 * @return newJLabel
	 */
	private Component createJLabel(JLabel newJLabel, String text, int size) {
		newJLabel = new JLabel(text);
		newJLabel.setFont(new java.awt.Font("Arial", 0, size));
		newJLabel.setHorizontalAlignment(JLabel.CENTER);
		return newJLabel;
	}
}
