package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import language.Strings;
import utils.WindowMaker;

public class StatisticsUI {
	JPanel mainPanel = null;
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JComboBox<String> trainingsCB = null;
	ItemListener item = null;
	
	public StatisticsUI(ItemListener item) {
		this.item = item;
	}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createTitledBorder(Strings.statisticsMain));
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		return mainPanel;
	}
	
	private Container createNorthPanel() {
		northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		trainingsCB = WindowMaker.createJComboBox(trainingsCB, createTrainingsList(), item);
		northPanel.add(trainingsCB);
		return northPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		
		return centerPanel;
	}
	
	private ArrayList<String> createTrainingsList() {
		ArrayList<String> list = new ArrayList<>();
		
		return list;
	}
}
