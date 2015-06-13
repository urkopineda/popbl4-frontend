package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import language.Strings;
import main.Configuration;
import task.ChronoTimer;
import task.HeartAnimator;
import utils.Player;
import utils.WindowMaker;

/**
 * JPanel incial que contiene el cronómetro, la JList de canciones, el reproductor de música, los PPMs, etc.
 * 
 * @author Urko
 *
 */
public class TrainingUI {
	ChronoTimer chronometerThread = null;
	HeartAnimator heartAnimator = null;
	JPanel mainPanel = null;
	JPanel rightPanel = null;
	JPanel leftPanel = null;
	JPanel chronoPlayerPanel = null;
	JPanel chronometerPanel = null;
	JPanel playerPanel = null;
	JPanel heartRatePanel = null;
	JPanel chronometerSouthPanel = null;
	JButton buttonStart = null;
	JButton buttonStop = null;
	JButton buttonPause = null;
	JLabel chronometerNumbers = null;
	public static JLabel ppmNumbers = null;
	JLabel ppmImage = null;
	ActionListener act = null;
	Player player = null;
	JFrame window = null;
	
	public TrainingUI(ActionListener act, JFrame window) {
		this.act = act;
		this.window = window;
		player = new Player(window);
	}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		return mainPanel;
	}
	
	public void addContent() {
		mainPanel.add(createLeftPanel());
		mainPanel.add(createRightPanel());
		buttonPause.setEnabled(false);
		buttonStop.setEnabled(false);
		mainPanel.updateUI();
	}
	
	private Container createRightPanel() {
		rightPanel = new JPanel(new BorderLayout());
		rightPanel.setBorder(BorderFactory.createTitledBorder(Strings.get("trainingList")));
		rightPanel.add(player.getPlayerList());
		return rightPanel;
	}
	
	private Container createLeftPanel() {
		leftPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		leftPanel.setBorder(BorderFactory.createTitledBorder(Strings.get("trainingMain")));
		leftPanel.add(createChronoPlayerPanel());
		leftPanel.add(createHeartRatePanel());
		return leftPanel;
	}
	
	private Container createChronoPlayerPanel() {
		chronoPlayerPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		chronoPlayerPanel.add(createChronometerPanel());
		chronoPlayerPanel.add(createPlayerPanel());
		return chronoPlayerPanel;
	}
	
	private Container createChronometerPanel() {
		chronometerPanel = new JPanel(new BorderLayout());
		chronometerPanel.setBorder(BorderFactory.createTitledBorder(Strings.get("trainingChrono")));
		chronometerNumbers = WindowMaker.createJLabel(chronometerNumbers, "00:00:00", 75, "center");
		chronometerPanel.add(chronometerNumbers, BorderLayout.CENTER);
		chronometerSouthPanel = new JPanel(new GridLayout(1, 3, 5, 5));
		buttonStart = WindowMaker.createJButton(Strings.get("trainingBtnStart"), "start", act);
		buttonPause = WindowMaker.createJButton(Strings.get("trainingBtnPause"), "pause", act);
		buttonStop = WindowMaker.createJButton(Strings.get("trainingBtnStop"), "stop", act);
		chronometerSouthPanel.add(buttonStart);
		chronometerSouthPanel.add(buttonPause);
		chronometerSouthPanel.add(buttonStop);
		chronometerPanel.add(chronometerSouthPanel, BorderLayout.SOUTH);
		return chronometerPanel;
	}
	
	private Container createPlayerPanel() {
		playerPanel = new JPanel(new BorderLayout());
		playerPanel.setBorder(BorderFactory.createTitledBorder(Strings.get("trainingPlayer")));
		playerPanel.add(player.getPlayerButtons());
		return playerPanel;
	}
	
	private Container createHeartRatePanel() {
		heartRatePanel = new JPanel(new BorderLayout());
		heartRatePanel.setBorder(BorderFactory.createTitledBorder(Strings.get("trainingPulse")));
		ppmNumbers = WindowMaker.createJLabel(ppmNumbers, Configuration.ppm+" ppm", 50, "center");
		heartRatePanel.add(ppmNumbers, BorderLayout.NORTH);
		ppmImage = new JLabel(new ImageIcon(Configuration.dHeartOff));
		heartRatePanel.add(ppmImage, BorderLayout.CENTER);
		return heartRatePanel;
	}
	
	/**
	 * Método que inicia el Timer del entrenamiento.
	 */
	public void startTimer() {
		if (chronometerThread != null) {
			if (!chronometerThread.isAlive()) {
				chronometerThread.start();
				heartAnimator.start();
			}
		} else {
			chronometerThread = new ChronoTimer(chronometerNumbers);
			heartAnimator = new HeartAnimator(ppmImage);
			chronometerThread.start();
			heartAnimator.start();
		}
		chronometerThread.startTimer();
		buttonStop.setEnabled(true);
		buttonPause.setEnabled(true);
		buttonStart.setEnabled(false);
	}
	
	/**
	 * Método que pausa el Timer.
	 */
	public void pauseTimer() {
		chronometerThread.pauseTimer();
		buttonPause.setEnabled(false);
		buttonStart.setEnabled(true);
	}
	
	/**
	 * Método que para el Timer y 'mata' el Thread.
	 */
	public void stopTimer() {
		if (chronometerThread != null) {
			chronometerThread.stopTimer();
			chronometerThread = null;
		}
		buttonPause.setEnabled(false);
		buttonStop.setEnabled(false);
		buttonStart.setEnabled(true);
	}
	
	/**
	 * Método publico para obtener el reproductor.
	 * 
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Devuelve el tamaño de la lista.
	 * 
	 * @return
	 */
	public int getListSize() {
		return player.getListSize();
	}
	
	/**
	 * Establece el boton de inicio de entrenamiento en false.
	 */
	public void btFalse() {
		buttonStart.setEnabled(false);
	}
	
	/**
	 * Establece el boton de inicio de entrenamiento en true.
	 */
	public void btTrue() {
		buttonStart.setEnabled(true);
	}
}
