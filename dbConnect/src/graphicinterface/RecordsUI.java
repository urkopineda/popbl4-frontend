package graphicinterface;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class RecordsUI {
	JPanel mainPanel = null;
	JPanel northPanel = null;
	
	public RecordsUI() {
		
	}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new GridLayout(1, 4, 5, 5));
		
		return mainPanel;
	}
}
