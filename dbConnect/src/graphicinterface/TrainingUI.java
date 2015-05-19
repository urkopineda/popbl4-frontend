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

import main.Configuration;
import task.ChronoTimer;
import utils.ImagePanel;
import utils.WindowMaker;
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
	JButton buttonStart = null;
	JButton buttonStop = null;
	JButton buttonExit = null;
	JButton buttonPause = null;
	JLabel cronometerNumbers = null;
	JLabel ppmNumbers = null;
	
	/**
	 * Constructor de la UI de Training, oculta el panel anterior y crea uno nuevo.
	 * 
	 * @param Controller systemController
	 * @param MainUI lastUI
	 */
	public TrainingUI(Controller systemController, MainUI lastUI) {
		this.window = lastUI.window;
		this.systemController = systemController;
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
	
	/**
	 * Crea el panel principal con sus sub-paneles.
	 * 
	 * @return JPanel mainPanel
	 */
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}
	
	/**
	 * Crea el panel norte.
	 * 
	 * @return JPanel northPanel
	 */
	private Container createNorthPanel() {
		northPanel = new JPanel();
		
		return northPanel;
	}
	
	/**
	 * Crea el panel central.
	 * 
	 * @return JPanel centerPanel
	 */
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		centerPanel.add(createChronometerPanel());
		centerPanel.add(createHeartRatePanel());
		return centerPanel;
	}
	
	/**
	 * Crea el panel del cronómetro que puede que contenga el panel del reproductor.
	 * 
	 * @return JPanel chronometerPanel
	 */
	private Container createChronometerPanel() {
		chronometerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		cronometerNumbers = WindowMaker.createJLabel(cronometerNumbers, "00:00:00", 75);
		chronometerPanel.add(cronometerNumbers);
		// PRUEBAS
		JLabel mensajePrueba = null;
		mensajePrueba = WindowMaker.createJLabel(mensajePrueba, "¡Aquí irá el reproductor MP3!", 25);
		chronometerPanel.add(mensajePrueba);
		// PRUEBAS
		return chronometerPanel;
	}
	
	/**
	 * Crea el panel donde se visualiza el estado del sensor y las pulsaciones, y el número exacto de las mismas.
	 * 
	 * @return JPanel heartRatePanel
	 */
	private Container createHeartRatePanel() {
		heartRatePanel = new JPanel(new BorderLayout());
		if (Configuration.bluetoothIsConnected) {
			if (Configuration.heartRateState)	heartRateImage = new ImagePanel(new ImageIcon("img/heart_on_up.png").getImage());
			else if (!Configuration.heartRateState) heartRateImage = new ImagePanel(new ImageIcon("img/heart_on_down.png").getImage());
		} else if (!Configuration.bluetoothIsConnected) {
			heartRateImage = new ImagePanel(new ImageIcon("img/heart_off.png").getImage());
		}
		heartRatePanel.add(heartRateImage, BorderLayout.CENTER);
		ppmNumbers = WindowMaker.createJLabel(ppmNumbers, "00 ppm", 50);
		heartRatePanel.add(ppmNumbers, BorderLayout.NORTH);
		return heartRatePanel;
	}
	
	/**
	 * Crea el panel sur con los botones necesarios.
	 * 
	 * @return JPanel southPanel
	 */
	private Container createSouthPanel() {
		southPanel = new JPanel(new GridLayout(1, 4, 0, 0));
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
	
	/**
	 * Refresca/Actualiza la UI de este panel.
	 */
	public void refreshUI() {
		window.setContentPane(createMainPanel());
		window.repaint();
		window.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("start")) {
			if (chronometerThread != null) {
				if (!chronometerThread.isAlive()) {
					chronometerThread.start();
				}
			} else {
				chronometerThread = new ChronoTimer(cronometerNumbers);
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
			lastUI = new MainUI(systemController);
		}
	}
}
