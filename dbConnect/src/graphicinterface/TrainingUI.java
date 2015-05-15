package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import task.ChronoTimer;
import administration.Controller;

/**
 * Clase que se encarga de la UI de la pantalla de Training.
 * 
 * @author Runnstein Team
 */
public class TrainingUI implements ActionListener {
	Controller systemController = null;
	ChronoTimer chronometerThread = null;
	MainUI lastUI = null;
	JFrame window = null;
	JPanel mainPanel = null;
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JPanel southPanel = null;
	JPanel heartRatePanel = null;
	JPanel heartRateImage = null;
	JPanel chronometerPanel = null;
	JButton buttonConnect = null;
	JButton buttonStart = null;
	JButton buttonStop = null;
	JButton buttonExit = null;
	JButton buttonPause = null;
	JLabel sensorMessage = null;
	JLabel cronometerNumbers = null;
	JLabel ppmNumbers = null;
	boolean heartState = false;
	boolean sensorState = false;
	
	/**
	 * Constructor de la UI de Training, oculta el panel anterior y crea uno nuevo.
	 * 
	 * @param systemController
	 * @param window
	 */
	public TrainingUI(Controller systemController, MainUI lastUI) {
		this.window = lastUI.window;
		window.setTitle("Entrenamiento");
		window.setSize(640, 640);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2 - window.getSize().width/2, dim.height/2 - window.getSize().height/2);
		window.setContentPane(createMainPanel());
		window.repaint();
		window.revalidate();
		buttonPause.setEnabled(false);
		buttonStop.setEnabled(false);
	}
		
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}
	
	private Container createNorthPanel() {
		northPanel = new JPanel();
		
		return northPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		centerPanel.add(createChronometerPanel());
		centerPanel.add(createHeartRatePanel());
		return centerPanel;
	}
	
	private Container createChronometerPanel() {
		chronometerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		cronometerNumbers = WindowMaker.createJLabel(cronometerNumbers, "00:00:00", 75);
		chronometerPanel.add(cronometerNumbers);
		// PRUEBAS
		JLabel mensajePrueba = null;
		mensajePrueba = WindowMaker.createJLabel(mensajePrueba, "Aquí irá algo sobre conectar el sensor!", 25);
		chronometerPanel.add(mensajePrueba);
		// PRUEBAS
		return chronometerPanel;
	}
	
	private Container createHeartRatePanel() {
		heartRatePanel = new JPanel(new BorderLayout());
		if (sensorState) {
			if (heartState)	heartRateImage = new ImagePanel(new ImageIcon("img/heart_on_up.png").getImage());
			else if (!heartState) heartRateImage = new ImagePanel(new ImageIcon("img/heart_on_down.png").getImage());
		} else if (!sensorState) {
			heartRateImage = new ImagePanel(new ImageIcon("img/heart_off.png").getImage());
		}
		heartRatePanel.add(heartRateImage, BorderLayout.CENTER);
		ppmNumbers = WindowMaker.createJLabel(ppmNumbers, "00 ppm", 50);
		heartRatePanel.add(ppmNumbers, BorderLayout.NORTH);
		return heartRatePanel;
	}
	
	private Container createSouthPanel() {
		southPanel = new JPanel(new GridLayout(1, 3, 0, 0));
		buttonStart = WindowMaker.createJButton(buttonStart, "Iniciar", "start", null, this, false);
		buttonPause = WindowMaker.createJButton(buttonPause, "Pausar", "pause", null, this, false);
		buttonStop = WindowMaker.createJButton(buttonStop, "Parar", "stop", null, this, false);
		buttonExit = WindowMaker.createJButton(buttonExit, "Salir", "exit", null, this, false);
		southPanel.add(buttonStart);
		southPanel.add(buttonPause);
		southPanel.add(buttonStop);
		southPanel.add(buttonExit);
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
			if (chronometerThread != null) {
				if (!chronometerThread.getTimer().isRunning()) {
					chronometerThread.startTimer();
				}
			} else {
				chronometerThread = new ChronoTimer(cronometerNumbers);
				chronometerThread.startTimer();
			}
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
			lastUI = new MainUI(systemController);
		}
	}
}
