package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import administration.Controller;

public class TrainingUI implements ActionListener {
	Controller systemController = null;
	JFrame window = null;
	Object classObject = null;
	
	public TrainingUI(Controller systemController, JFrame window) {
		classObject = this;
		this.systemController = systemController;
		window.setContentPane(createMainPanel());
		window.repaint();
		window.revalidate();
	}
	
	JPanel mainPanel = null;
	
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}
	
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JPanel southPanel = null;
	
	JLabel labelStart = null;
	
	private Container createNorthPanel() {
		northPanel = new JPanel(new BorderLayout());
		northPanel.add(createJLabel(labelStart, "Pulsa 'Start' para empezar...", 20), BorderLayout.CENTER);
		return northPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		CronometerUI newCronometer = new CronometerUI();
		centerPanel.add(newCronometer.getPane());
		HeartRateUI newHR = new HeartRateUI();
		centerPanel.add(newHR.getPane());
		return centerPanel;
	}
	
	JButton buttonStart = null;
	JButton buttonStop = null;
	JButton buttonExit = null;
	
	private Container createSouthPanel() {
		southPanel = new JPanel(new GridLayout(1, 3, 0, 0));
		southPanel.add(createJButton(buttonStart, "Iniciar", null));
		southPanel.add(createJButton(buttonStop, "Parar", null));
		southPanel.add(createJButton(buttonExit, "Salir", null));
		return southPanel;
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
	
	/**
	 * Método para crear JButtons.
	 * 
	 * @param newJButton
	 * @param text
	 * @param icon
	 * @return newJButton
	 */
	private Component createJButton(JButton newJButton, String text, Icon icon) {
		newJButton = new JButton(text);
		if (icon != null) newJButton.setIcon(icon);
		newJButton.addActionListener(this);
		newJButton.setActionCommand(text);
		return newJButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
