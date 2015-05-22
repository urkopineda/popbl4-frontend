package pruebas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Configuration;
import task.ChronoTimer;
import utils.WindowMaker;

public class MobileUI implements ActionListener {
	JFrame window = null;
	JPanel mainPanel = null;
	ChronoTimer chronometerThread = null;
	// North panel components/containers.
	JPanel northPanel = null;
	JLabel menuIcon = null;
	JLabel runnsteinText = null;
	// Center panel components/containers.
	JPanel centerPanel = null;
	JPanel centerNorthPanel = null;
	JPanel centerCenterPanel = null;
	JPanel chronoPanel = null;
	JPanel btnChronoPanel = null;
	JLabel heartSensorIcon = null;
	JLabel serverConnectedIcon = null;
	JLabel chronometerNumbers = null;
	JLabel player = null;
	JLabel heartRate = null;
	// South panel components/containers.
	JPanel southPanel = null;
	JLabel ppmNumbers = null;
	JLabel heartImage = null;
	JPanel tempBtnPanel = null;
	JButton startBtn = null;
	JButton pauseBtn = null;
	JButton stopBtn = null;
	
	public MobileUI() {
		window = new JFrame("Runnstein");
		window.setSize(360, 640);
		window.setLocation(50, 50);
		window.setContentPane(createMainPanel());
		window.setResizable(false);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		// Add containers.
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}
	
	private Container createNorthPanel() {
		northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// Set color.
		northPanel.setBackground(new Color(25343));
		// Add icon.
		menuIcon = new JLabel(new ImageIcon("img/icons/menu.png"));
		northPanel.add(menuIcon);
		// Add text.
		runnsteinText = WindowMaker.createJLabel(runnsteinText, "Runnstein", 20, "center");
		runnsteinText.setForeground(Color.WHITE);
		northPanel.add(runnsteinText);
		return northPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new BorderLayout());
		// Set color.
		centerPanel.setBackground(new Color(16777215));
		// Create internal north panel.
		centerNorthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		// Set color.
		centerNorthPanel.setBackground(new Color(16777215));
		// Add icons.
		if (Configuration.sensorState) heartSensorIcon = new JLabel(new ImageIcon("img/icons/sensorConnected.png"));
		else if (!Configuration.sensorState) heartSensorIcon = new JLabel(new ImageIcon("img/icons/sensorDisconnected.png"));
		centerNorthPanel.add(heartSensorIcon);
		if (Configuration.syncState) serverConnectedIcon = new JLabel(new ImageIcon("img/icons/serverConnected.png"));
		else if (!Configuration.syncState) serverConnectedIcon = new JLabel(new ImageIcon("img/icons/serverDisconnected.png"));
		centerNorthPanel.add(serverConnectedIcon);
		// Add subcontainer to container.
		centerPanel.add(centerNorthPanel, BorderLayout.NORTH);
		// Create internal center panel.
		centerCenterPanel = new JPanel(new GridLayout(1, 1, 0, 0));
		// Set color.
		centerCenterPanel.setBackground(new Color(16777215));
		// Add chronometer numbers.
		chronoPanel = new JPanel(new BorderLayout());
		// Set color.
		chronoPanel.setBackground(new Color(16777215));
		chronometerNumbers = WindowMaker.createJLabel(chronometerNumbers, "00:00:00", 75, "center");
		chronoPanel.add(chronometerNumbers, BorderLayout.CENTER);
		// Add buttons.
		btnChronoPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		// Set color.
		btnChronoPanel.setBackground(new Color(16777215));
		player = new JLabel(new ImageIcon("img/icons/playerIconOff.png"));
		heartRate = new JLabel(new ImageIcon("img/icons/heartIconOn.png"));
		btnChronoPanel.add(player);
		btnChronoPanel.add(heartRate);
		chronoPanel.add(btnChronoPanel, BorderLayout.SOUTH);
		centerCenterPanel.add(chronoPanel);
		// Add subcontainer to container.
		centerPanel.add(centerCenterPanel, BorderLayout.CENTER);
		return centerPanel;
	}
	
	private Container createSouthPanel() {
		southPanel = new JPanel(new BorderLayout());
		// Set color.
		southPanel.setBackground(new Color(16777215));
		// Add PPM numbers.
		ppmNumbers = WindowMaker.createJLabel(ppmNumbers, "00 ppm", 30, "center");
		southPanel.add(ppmNumbers, BorderLayout.NORTH);
		// Add heart image.
		if (Configuration.sensorState) {
			heartImage = new JLabel(new ImageIcon("img/heart_on_down.png"));
		} else if (!Configuration.sensorState) {
			heartImage = new JLabel(new ImageIcon("img/heart_off.png"));
		}
		southPanel.add(heartImage, BorderLayout.CENTER);
		// Add Start/Stop and Pause buttons.
		tempBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		// Set color.
		tempBtnPanel.setBackground(new Color(16777215));
		startBtn = WindowMaker.createJButton(startBtn, "Iniciar", "start", this);
		tempBtnPanel.add(startBtn);
		southPanel.add(tempBtnPanel, BorderLayout.SOUTH);
		return southPanel;
	}
	
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		MobileUI ui = new MobileUI();
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
			mainPanel.updateUI();
		} else if (e.getActionCommand().equals("pause")) {
			chronometerThread.pauseTimer();
		} else if (e.getActionCommand().equals("stop")) {
			chronometerThread.stopTimer();
			chronometerThread = null;
		}
	}
}
