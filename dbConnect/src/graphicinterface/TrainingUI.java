package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionListener;

import language.Strings;
import main.Configuration;
import task.ChronoTimer;
import task.HeartAnimator;
import utils.Player;
import utils.WindowMaker;

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
	JLabel ppmNumbers = null;
	JLabel ppmImage = null;
	ActionListener act = null;
	Player player = null;
	
	public TrainingUI(PropertyChangeListener pg, ActionListener act, ListSelectionListener ls) {
		this.act = act;
		player = new Player(pg, act, ls);
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
		//ESTO ES DE UNAI
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
		// AQUÍ VA LO DE UNAI!
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
	
	public void pauseTimer() {
		chronometerThread.pauseTimer();
		buttonPause.setEnabled(false);
		buttonStart.setEnabled(true);
	}
	
	public void stopTimer() {
		chronometerThread.stopTimer();
		chronometerThread = null;
		buttonPause.setEnabled(false);
		buttonStop.setEnabled(false);
		buttonStart.setEnabled(true);
	}
	
	public Player getPlayer() {
		return player;
	}
}
