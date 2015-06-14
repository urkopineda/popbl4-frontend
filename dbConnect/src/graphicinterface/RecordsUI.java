package graphicinterface;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.WindowMaker;
import language.Strings;
import model.RecordsData;

/**
 * Esta clase genera el JPane de la pestaña Records.
 * 
 * @author Urko
 *
 */
public class RecordsUI {
	JPanel mainPanel = null;
	JPanel northPanel = null;
	JLabel label1 = null;
	JLabel label2 = null;
	JLabel label3 = null;
	JLabel label4 = null;
	JLabel label5 = null;
	
	RecordsData newData = null;
	
	public RecordsUI() {}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new GridLayout(5, 1, 5, 5));
		return mainPanel;
	}
	
	/**
	 * Mostramos todos los datos de Records.
	 */
	public void addContent() {
		mainPanel.removeAll();
		newData = new RecordsData();
		label1 = WindowMaker.createJLabel(label1, Strings.get("totalMinutesRecord")+" "+newData.totalDuration+Strings.get("minutesRecord"), 30, "center");
		label2 = WindowMaker.createJLabel(label2, Strings.get("maxMinutesRecord")+" "+newData.maxDuration+Strings.get("minutesRecord"), 30, "center");
		label3 = WindowMaker.createJLabel(label3, Strings.get("maxHeartRateRecord")+" "+newData.maxPPM+Strings.get("ppmRecord"), 30, "center");
		label4 = WindowMaker.createJLabel(label4, Strings.get("maxBPMRecord")+" "+newData.maxBPM+Strings.get("bpmRecord"), 30, "center");
		label5 = WindowMaker.createJLabel(label5, Strings.get("totalTrainingRecord")+" "+newData.totalTrainings, 30, "center");
		mainPanel.add(label5);
		mainPanel.add(label1);
		mainPanel.add(label2);
		mainPanel.add(label3);
		mainPanel.add(label4);
	}
}
