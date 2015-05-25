package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import language.Strings;
import main.Configuration;
import task.ChronoTimer;
import task.HeartAnimator;
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
	
	public TrainingUI(ActionListener act) {
		this.act = act;
	}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		mainPanel.add(createLeftPanel());
		mainPanel.add(createRightPanel());
		buttonPause.setEnabled(false);
		buttonStop.setEnabled(false);
		return mainPanel;
	}
	
	private Container createRightPanel() {
		rightPanel = new JPanel();
		rightPanel.setBorder(BorderFactory.createTitledBorder(Strings.trainingList));
		
		return rightPanel;
	}
	
	private Container createLeftPanel() {
		leftPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		leftPanel.setBorder(BorderFactory.createTitledBorder(Strings.trainingMain));
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
		chronometerPanel.setBorder(BorderFactory.createTitledBorder(Strings.trainingChrono));
		chronometerNumbers = WindowMaker.createJLabel(chronometerNumbers, "00:00:00", 75, "center");
		chronometerPanel.add(chronometerNumbers, BorderLayout.CENTER);
		chronometerSouthPanel = new JPanel(new GridLayout(1, 3, 5, 5));
		buttonStart = WindowMaker.createJButton(buttonStart, Strings.trainingBtnStart, "start", act);
		buttonPause = WindowMaker.createJButton(buttonPause, Strings.trainingBtnPause, "pause", act);
		buttonStop = WindowMaker.createJButton(buttonStop, Strings.trainingBtnStop, "stop", act);
		chronometerSouthPanel.add(buttonStart);
		chronometerSouthPanel.add(buttonPause);
		chronometerSouthPanel.add(buttonStop);
		chronometerPanel.add(chronometerSouthPanel, BorderLayout.SOUTH);
		return chronometerPanel;
	}
	
	private Container createPlayerPanel() {
		playerPanel = new JPanel(new BorderLayout());
		playerPanel.setBorder(BorderFactory.createTitledBorder(Strings.trainingPlayer));
		// AQUÍ VA LO DE UNAI!
		
		return playerPanel;
	}
	
	private Container createHeartRatePanel() {
		heartRatePanel = new JPanel(new BorderLayout());
		heartRatePanel.setBorder(BorderFactory.createTitledBorder(Strings.trainingPulse));
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
}
