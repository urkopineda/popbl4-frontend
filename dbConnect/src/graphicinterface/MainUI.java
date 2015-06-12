package graphicinterface;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import language.Strings;
import main.Configuration;
import model.Entrenamiento;
import playerModel.MiLoadScreen;
import task.Dump;
import utils.WindowMaker;
import bluetooth.COMManager;

public class MainUI implements ChangeListener, ActionListener, ListSelectionListener, ItemListener {
	ActionListener action = this;
	ListSelectionListener list = this;
	ItemListener item = this;
	JFrame window = null;
	JMenuBar menuBar = null;
	JMenu archivoMenu = null, editarMenu = null, exitMenu = null, btMenu = null;
	JMenuItem cargarDirectorioItem = null, cargarArchivoItem = null, exitItem = null, btConnect = null, btDisconnect = null, btInfo = null, settingsItem = null;
	JTabbedPane mainPanel = null;
	TrainingUI trainingUI = null;
	TrainingDataUI trainingDataUI = null;
	StatisticsUI statisticsUI = null;
	RecordsUI recordsUI = null;
	ProfileUI profileUI = null;
	ArrayList<Integer> addContentFlags = null;
	COMManager comManager = null;
	MiLoadScreen load = null;
	
	public MainUI() {
		window = new JFrame("Runnstein");
		window.setSize(640, 640);
		window.setLocation(100, 100);
		window.setContentPane(createMainPanel());
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setJMenuBar(createMenuBar());
	}
	
	private JMenuBar createMenuBar() {
		menuBar = new JMenuBar();
		menuBar.add(createArchivoMenu());
		menuBar.add(createEditarMenu());
		menuBar.add(Box.createGlue());
		menuBar.add(createBluetooth());
		menuBar.add(Box.createGlue());
		menuBar.add(createExit());
		btDisconnect.setEnabled(false);
		return menuBar;
	}
	
	private JMenu createArchivoMenu() {
		archivoMenu = new JMenu(Strings.get("menuFile"));
		cargarDirectorioItem = WindowMaker.createJMenuItem(Strings.get("loadDirectory"), this, "loadPath");
		cargarArchivoItem = WindowMaker.createJMenuItem(Strings.get("loadFile"), this, "loadFile");
		archivoMenu.add(cargarDirectorioItem);
		archivoMenu.add(cargarArchivoItem);
		return archivoMenu;
	}
	
	private JMenu createEditarMenu() {
		editarMenu = new JMenu(Strings.get("edit"));
		settingsItem = WindowMaker.createJMenuItem(Strings.get("settingsTitle"), this, "settings");
		editarMenu.add(settingsItem);
		return editarMenu;
	}
	
	private JMenu createBluetooth() {
		btMenu = new JMenu(Strings.get("btMenu"));
		btConnect = WindowMaker.createJMenuItem(Strings.get("btConnect"), this, "btConn");
		btMenu.add(btConnect);
		btDisconnect = WindowMaker.createJMenuItem(Strings.get("btDisconnect"), this, "btDisconn");
		btMenu.add(btDisconnect);
		btInfo = WindowMaker.createJMenuItem(Strings.get("btInfo"), this, "btInfo");
		btMenu.add(btInfo);
		return btMenu;
	}
	
	private JMenu createExit() {
		exitMenu = new JMenu(Strings.get("exitMenu"));
		exitItem = WindowMaker.createJMenuItem(Strings.get("exitMenu"), this, "exit");
		exitMenu.add(exitItem);
		return exitMenu;
	}
	
	private Container createMainPanel() {
		addContentFlags = new ArrayList<>();
		mainPanel = new JTabbedPane();
		trainingUI = new TrainingUI(action, window);
		trainingDataUI = new TrainingDataUI(list, window);
		statisticsUI = new StatisticsUI(item, window);
		recordsUI = new RecordsUI();
		profileUI = new ProfileUI(action, window);
		mainPanel.add(Strings.get("mainTabTraining"), trainingUI.createMainPanel());
		addContentFlags.add(0);
		mainPanel.add(Strings.get("mainTabTrainingData"), trainingDataUI.createMainPanel());
		addContentFlags.add(0);
		mainPanel.add(Strings.get("mainTabStatistics"), statisticsUI.createMainPanel());
		addContentFlags.add(0);
		mainPanel.add(Strings.get("mainTabRecord"), recordsUI.createMainPanel());
		addContentFlags.add(0);
		mainPanel.add(Strings.get("mainTabProfile"), profileUI.createMainPanel());
		addContentFlags.add(0);
		mainPanel.addChangeListener(this);
		trainingUI.addContent();
		trainingUI.switchBt();
		return mainPanel;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (mainPanel.getSelectedIndex() == 1) {
			if (addContentFlags.get(mainPanel.getSelectedIndex()) == 0) {
				addContentFlags.set(mainPanel.getSelectedIndex(), 1);
				trainingDataUI.addContent();
			}
		}
		if (mainPanel.getSelectedIndex() == 2) {
			if (addContentFlags.get(mainPanel.getSelectedIndex()) == 0) {
				addContentFlags.set(mainPanel.getSelectedIndex(), 1);
				statisticsUI.addContent();
				statisticsUI.addGraphics(statisticsUI.trainingsCB.getSelectedIndex(), statisticsUI.modeCB.getSelectedIndex());
			}
		}
		if (mainPanel.getSelectedIndex() == 3) {
			if (addContentFlags.get(mainPanel.getSelectedIndex()) == 0) {
				addContentFlags.set(mainPanel.getSelectedIndex(), 1);
				recordsUI.addContent();
			}
		}
		if (mainPanel.getSelectedIndex() == 4) {
			if (addContentFlags.get(mainPanel.getSelectedIndex()) == 0) {
				addContentFlags.set(mainPanel.getSelectedIndex(), 1);
				profileUI.addContent();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("start")) {
			if (trainingUI.getListSize() != 0) {
				trainingUI.startTimer();
				trainingUI.getPlayer().startReproduction();
				Configuration.actualTraining++;
				Dump.entrenamiento = new Entrenamiento();
				Configuration.isRunning = true;
			} else JOptionPane.showMessageDialog(window, "No se encuentra ninguna canción.", "Error", JOptionPane.ERROR_MESSAGE);
		} else if (e.getActionCommand().equals("pause")) {
			trainingUI.pauseTimer();
			trainingUI.getPlayer().pauseReproduction();
		} else if (e.getActionCommand().equals("stop")) {
			trainingUI.stopTimer();
			trainingUI.getPlayer().stopReproduction();
			Configuration.isRunning = false;
			// trainingUI.getPlayer().createDump();
		} else if (e.getActionCommand().equals("cancel")) {
			profileUI.cancelOption();
			Configuration.isRunning = false;
		} else if (e.getActionCommand().equals("save")) {
			profileUI.updateData();
		} else if (e.getActionCommand().equals("loadPath")) {
			trainingUI.getPlayer().searchDirectory(null);
		} else if (e.getActionCommand().equals("loadFile")) {
			trainingUI.getPlayer().searchSong();
		} else if (e.getActionCommand().equals("exit")) {
			window.dispose();
		} else if (e.getActionCommand().equals("btInfo")) {
			btInfo();
		} else if (e.getActionCommand().equals("btConn")) {
			load = new MiLoadScreen(window);
			load.setWorkToMake(3);
			comManager = new COMManager();
			load.progressHasBeenMade("Connecting...", 1);
			Configuration.com = comManager;
			load.progressHasBeenMade("Saving connection - 1...", 1);
			trainingUI.getPlayer().getCalculator().comCreated();
			load.progressHasBeenMade("Saving connection - 2...", 1);
			trainingUI.btTrue();
			load.progressHasBeenMade("Updating UI...", 1);
			if (Configuration.sensorState) {
				btDisconnect.setEnabled(true);
				btConnect.setEnabled(false);
				load.closeScreen();
				btInfo();
			} else load.closeScreen();
		} else if (e.getActionCommand().equals("btDisconn")) {
			comManager.interrupt();
			if (!Configuration.sensorState){
				Configuration.isRunning = false;
				btDisconnect.setEnabled(false);
				btConnect.setEnabled(true);
				trainingUI.stopTimer();
				trainingUI.getPlayer().stopReproduction();
				trainingUI.btFalse();
				btInfo();
			}
		} else if (e.getActionCommand().equals("settings")) {
			@SuppressWarnings("unused")
			SettingsUI settings = new SettingsUI(window);
		}
	}
	
	private void btInfo() {
		if (Configuration.sensorState) JOptionPane.showMessageDialog(window, Strings.get("btConnected"), "Info", JOptionPane.INFORMATION_MESSAGE);
		else JOptionPane.showMessageDialog(window, Strings.get("btDisconnected"), "Info", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// CAMBIA EL JTABLE.
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.DESELECTED) {			
			statisticsUI.addGraphics(statisticsUI.trainingsCB.getSelectedIndex(), statisticsUI.modeCB.getSelectedIndex());
		}
	}
}
