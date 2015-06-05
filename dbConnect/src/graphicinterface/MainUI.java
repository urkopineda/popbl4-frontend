package graphicinterface;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
<<<<<<< HEAD
import java.util.ArrayList;
=======
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
>>>>>>> playerBranch

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import language.Strings;

public class MainUI implements ChangeListener, ActionListener, ListSelectionListener, ItemListener, PropertyChangeListener {
	ActionListener action = this;
	ListSelectionListener list = this;
	ItemListener item = this;
	PropertyChangeListener propertyChange = this;
	JFrame window = null;
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
		} else if (e.getActionCommand().equals("pause")) {
			trainingUI.pauseTimer();
		} else if (e.getActionCommand().equals("stop")) {
			trainingUI.stopTimer();
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
		case "playSong": System.out.println("Play"); break;
		case "stopSong": System.out.println("Stop"); break;
		case "pauseSong": System.out.println("Pause"); break;
		case "nextSong": System.out.println("Next"); break;
		case "previousSong": System.out.println("Previous"); break;
		}		
>>>>>>> playerBranch
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// CAMBIA EL JTABLE.
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
<<<<<<< HEAD
		if (e.getStateChange() == ItemEvent.DESELECTED) {
=======
		if (e.getStateChange() == ItemEvent.DESELECTED) {			
>>>>>>> playerBranch
			statisticsUI.addGraphics(statisticsUI.trainingsCB.getSelectedIndex(), statisticsUI.modeCB.getSelectedIndex());
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
