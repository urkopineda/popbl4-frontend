package graphicinterface;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import language.Strings;
import utils.WindowMaker;

public class MainUI implements ChangeListener, ActionListener, ListSelectionListener, ItemListener, PropertyChangeListener {
	ActionListener action = this;
	ListSelectionListener list = this;
	ItemListener item = this;
	PropertyChangeListener propertyChange = this;
	JFrame window = null;
	JMenuBar menuBar = null;
	JMenu archivoMenu = null;
	JMenuItem cargarDirectorioItem = null, cargarArchivoItem = null;
	JTabbedPane mainPanel = null;
	TrainingUI trainingUI = null;
	TrainingDataUI trainingDataUI = null;
	StatisticsUI statisticsUI = null;
	RecordsUI recordsUI = null;
	ProfileUI profileUI = null;
	ArrayList<Integer> addContentFlags = null;
	
	public MainUI() {
		window = new JFrame("Runnstein");
		window.setSize(640, 640);
		window.setLocation(100, 100);
		window.setContentPane(createMainPanel());
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar = new JMenuBar();
		archivoMenu = new JMenu("Archivo");
		cargarDirectorioItem = WindowMaker.createJMenuItem("Cargar directorio", this, "loadPath");
		cargarArchivoItem = WindowMaker.createJMenuItem("Cargar archivo", this, "loadFile");
		archivoMenu.add(cargarDirectorioItem);
		archivoMenu.add(cargarArchivoItem);
		menuBar.add(archivoMenu);
		window.setJMenuBar(menuBar);
	}
	
	private Container createMainPanel() {
		addContentFlags = new ArrayList<>();
		mainPanel = new JTabbedPane();
		trainingUI = new TrainingUI(propertyChange, action, list);
		trainingDataUI = new TrainingDataUI(list);
		statisticsUI = new StatisticsUI(item);
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
<<<<<<< HEAD
		if (e.getActionCommand().equals("start")) {
			trainingUI.startTimer();
			trainingUI.getPlayer().startReproduction();
		} else if (e.getActionCommand().equals("pause")) {
			trainingUI.pauseTimer();
			trainingUI.getPlayer().pauseReproduction();
		} else if (e.getActionCommand().equals("stop")) {
			trainingUI.stopTimer();
			trainingUI.getPlayer().stopReproduction();
		} else if (e.getActionCommand().equals("cancel")) {
			profileUI.cancelOption();
		} else if (e.getActionCommand().equals("save")) {
			profileUI.updateData();
		}
=======
		String cmd = e.getActionCommand();
		switch (cmd) {
		case "start": trainingUI.startTimer(); trainingUI.getPlayer().startReproduction(); break;
		case "pause": trainingUI.pauseTimer(); trainingUI.getPlayer().pauseReproduction(); break;
		case "stop": trainingUI.stopTimer(); trainingUI.getPlayer().stopReproduction(); break;
		case "loadPath": trainingUI.getPlayer().searchDirectory(null); break;
		case "loadFile": trainingUI.getPlayer().searchSong(); break;
		}		
>>>>>>> playerBranch
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

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
