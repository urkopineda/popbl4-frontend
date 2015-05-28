package graphicinterface;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import language.Strings;

public class MainUI implements ChangeListener, ActionListener, ListSelectionListener, ItemListener{
	ActionListener action = this;
	ListSelectionListener list = this;
	ItemListener item = this;
	JFrame window = null;
	JTabbedPane mainPanel = null;
	TrainingUI trainingUI = null;
	TrainingDataUI trainingDataUI = null;
	StatisticsUI statisticsUI = null;
	RecordsUI recordsUI = null;
	boolean firstLoad = false;
	
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
		mainPanel = new JTabbedPane();
		trainingUI = new TrainingUI(action);
		trainingDataUI = new TrainingDataUI(list);
		statisticsUI = new StatisticsUI(item);
		recordsUI = new RecordsUI();
		mainPanel.add(Strings.get("mainTabTraining"), trainingUI.createMainPanel());
		mainPanel.add(Strings.get("mainTabTrainingData"), trainingDataUI.createMainPanel());
		mainPanel.add(Strings.get("mainTabStatistics"), statisticsUI.createMainPanel());
		mainPanel.add(Strings.get("mainTabRecord"), recordsUI.createMainPanel());
		mainPanel.addChangeListener(this);
		return mainPanel;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (!firstLoad) {
			if (mainPanel.getSelectedIndex() == 2) statisticsUI.addGraphics(0, 0);
			firstLoad = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("start")) {
			trainingUI.startTimer();
		} else if (e.getActionCommand().equals("pause")) {
			trainingUI.pauseTimer();
		} else if (e.getActionCommand().equals("stop")) {
			trainingUI.stopTimer();
		}
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
