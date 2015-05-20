package pruebas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import task.ChronoTimer;
import utils.WindowMaker;

public class TrainingUI implements ActionListener {
	JFrame window = null;
	ChronoTimer chronometerThread = null;
	JPanel mainPanel = null;
	JPanel rightPanel = null;
	JPanel leftPanel = null;
	JPanel chronometerPanel = null;
	JPanel heartRatePanel = null;
	JPanel chronometerSouthPanel = null;
	JButton buttonStart = null;
	JButton buttonStop = null;
	JButton buttonPause = null;
	JLabel chronometerNumbers = null;
	
	public TrainingUI() {
		window = new JFrame("Runsstein");
		window.setSize(640, 640);
		window.setLocation(100, 100);
		window.setContentPane(createMainPanel());
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private Container createMainPanel() {
		mainPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		mainPanel.add(createLeftPanel());
		mainPanel.add(createRightPanel());
		return mainPanel;
	}
	
	private Container createRightPanel() {
		rightPanel = new JPanel();
		rightPanel.setBorder(BorderFactory.createTitledBorder("Mapa del Recorrido"));
		
		return rightPanel;
	}
	
	private Container createLeftPanel() {
		leftPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		leftPanel.setBorder(BorderFactory.createTitledBorder("Control del Entrenamiento"));
		leftPanel.add(createChronometerPanel());
		leftPanel.add(createHeartRatePanel());
		return leftPanel;
	}
	
	private Container createChronometerPanel() {
		chronometerPanel = new JPanel(new BorderLayout());
		chronometerPanel.setBorder(BorderFactory.createTitledBorder("Cronómetro"));
		chronometerNumbers = WindowMaker.createJLabel(chronometerNumbers, "00:00:00", 75);
		chronometerPanel.add(chronometerNumbers, BorderLayout.CENTER);
		chronometerSouthPanel = new JPanel(new GridLayout(1, 3, 5, 5));
		buttonStart = WindowMaker.createJButton(buttonStart, "Iniciar", "start", null, this, false);
		buttonPause = WindowMaker.createJButton(buttonPause, "Pausar", "pause", null, this, false);
		buttonStop = WindowMaker.createJButton(buttonStop, "Parar", "stop", null, this, false);
		chronometerSouthPanel.add(buttonStart);
		chronometerSouthPanel.add(buttonPause);
		chronometerSouthPanel.add(buttonStop);
		chronometerPanel.add(chronometerSouthPanel, BorderLayout.SOUTH);
		return chronometerPanel;
	}
	
	private Container createHeartRatePanel() {
		heartRatePanel = new JPanel(new BorderLayout());
		heartRatePanel.setBorder(BorderFactory.createTitledBorder("Pulsómetro"));
		
		return heartRatePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("start")) {
			if (chronometerThread != null) {
				if (!chronometerThread.isAlive()) {
					chronometerThread.start();
				}
			} else {
				chronometerThread = new ChronoTimer(chronometerNumbers);
				chronometerThread.start();
			}
			chronometerThread.startTimer();
			buttonStop.setEnabled(true);
			buttonPause.setEnabled(true);
			buttonStart.setEnabled(false);
		} else if (e.getActionCommand().equals("pause")) {
			chronometerThread.pauseTimer();
			buttonPause.setEnabled(false);
			buttonStart.setEnabled(true);
		} else if (e.getActionCommand().equals("stop")) {
			chronometerThread.stopTimer();
			chronometerThread = null;
			buttonPause.setEnabled(false);
			buttonStop.setEnabled(false);
			buttonStart.setEnabled(true);
		} else if (e.getActionCommand().equals("exit")) {
			window.dispose();
		}
	}
}
