package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class RecordsUI {
	JPanel mainPanel = null;
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JLabel profileIMG = null;
	
	public RecordsUI() {
		
	}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		return mainPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel();
		// MAX TIEMPO
		// MAX DÍAS SEGUIDOS
		// MAX BPMS
		// MAX PPMS
		return centerPanel;
	}
}
