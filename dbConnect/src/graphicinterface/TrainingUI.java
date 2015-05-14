package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import task.Task;
import administration.Controller;

/**
 * Clase que se encarga de la UI de la pantalla de Training.
 * 
 * @author Runnstein Team
 */
public class TrainingUI implements ActionListener {
	Controller systemController = null;
	JFrame window = null;
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JPanel hrPanel = null;
	JPanel southPanel = null;
	JPanel mainPanel = null;
	JLabel labelStart = null;
	JLabel labelPPM = null;
	JButton buttonStart = null;
	JButton buttonStop = null;
	JButton buttonExit = null;
	
	Task startTime = null;
	HeartRateUI newHR = null;
	CronometerUI newCronometer = null;
	
	int state = -1;
	int ppm = 88;
	
	/**
	 * Constructor de la UI de Training, utiliza el panel anterior.
	 * 
	 * @param systemController
	 * @param window
	 */
	public TrainingUI(Controller systemController, JFrame window) {
		this.window = window;
		this.systemController = systemController;
		window.setContentPane(createMainPanel());
		window.repaint();
		window.revalidate();
	}
	
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}
	
	private Container createNorthPanel() {
		northPanel = new JPanel(new BorderLayout());
		northPanel.add(WindowMaker.createJLabel(labelStart, "Pulsa 'Iniciar' para empezar...", 20), BorderLayout.CENTER);
		return northPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		newCronometer = new CronometerUI(0, 0, 0);
		centerPanel.add(newCronometer.getPane());
		centerPanel.add(createHeartRatePanel());
		return centerPanel;
	}
	
	private Container createHeartRatePanel() {
		hrPanel = new JPanel(new BorderLayout());
		newHR = new HeartRateUI(state);
		hrPanel.add(newHR.getPane(), BorderLayout.CENTER);
		hrPanel.add(WindowMaker.createJLabel(labelPPM, String.valueOf(ppm)+"ppm", 50), BorderLayout.NORTH);
		return hrPanel;
	}
	
	private Container createSouthPanel() {
		southPanel = new JPanel(new GridLayout(1, 3, 0, 0));
		southPanel.add(WindowMaker.createJButton(buttonStart, "Iniciar", "start", null, this, false));
		southPanel.add(WindowMaker.createJButton(buttonStop, "Parar", "stop", null, this, false));
		southPanel.add(WindowMaker.createJButton(buttonExit, "Salir", "exit", null, this, false));
		return southPanel;
	}
	
	public void refreshUI() {
		window.setContentPane(createMainPanel());
		window.repaint();
		window.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("start")) {
			startTime = new Task(this, newCronometer);
			startTime.execute();
		} else if (e.getActionCommand().equals("stop")) {
			startTime.done();
		} else if (e.getActionCommand().equals("exit")) {
			window.dispose();
		} else if (e.getActionCommand().equals("prueba")) {
			state = newHR.switchHRState(state);
			refreshUI();
		}
	}
}
