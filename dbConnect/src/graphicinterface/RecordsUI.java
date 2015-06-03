package graphicinterface;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.WindowMaker;
import model.RecordsData;

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
	
	public void addContent() {
		newData = new RecordsData();
		label1 = WindowMaker.createJLabel(label1, "Total de minutos entrenados: "+newData.totalDuration+" minutos.", 30, "center");
		label2 = WindowMaker.createJLabel(label2, "M�ximo de minutos entrenados en un �nico entrenamiento: "+newData.maxDuration+" minutos.", 30, "center");
		label3 = WindowMaker.createJLabel(label3, "M�ximo de pulsaciones por minuto: "+newData.maxPPM+" ppm.", 30, "center");
		label4 = WindowMaker.createJLabel(label4, "M�ximo de beats por minuto: "+newData.maxBPM+" bpm.", 30, "center");
		label5 = WindowMaker.createJLabel(label5, "N�mero total de entrenamientos: "+newData.totalTrainings, 30, "center");
		mainPanel.add(label5);
		mainPanel.add(label1);
		mainPanel.add(label2);
		mainPanel.add(label3);
		mainPanel.add(label4);
	}
}
