package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tablemodel.ColumnTableModelBasic;
import tablemodel.TableModelBasic;
import tablemodel.TrazadorTableBasic;
import administration.Controller;

public class TrainingDataUI implements ActionListener {
	MainUI lastUI = null;
	JFrame window = null;
	Controller systemController = null;
	TrazadorTableBasic trazador = null;
	TableModelBasic tableModel = null;
	ColumnTableModelBasic columnModel = null;
	JPanel mainPanel = null;
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JPanel southPanel = null;
	
	public TrainingDataUI(Controller systemController, MainUI lastUI) {
		this.lastUI = lastUI;
		this.window = lastUI.window;
		this.systemController = systemController;
		window.setTitle("Mis Entrenamientos");
		window.setSize(640, 640);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2 - window.getSize().width/2, dim.height/2 - window.getSize().height/2);
		window.setContentPane(createMainPanel());
		window.repaint();
		window.revalidate();
	}
	
	private void createTable() {
		/*
		trazador = new TrazadorTableBasic();
		columnModel = new ColumnTableModelBasic(trazador);
		tableModel = TableModelBasic(columnModel);
		*/
	}
	
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}
	
	private Container createNorthPanel() {
		northPanel = new JPanel();
		
		return northPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel();
		
		return centerPanel;
	}
	
	private Container createSouthPanel() {
		southPanel = new JPanel();
		
		return southPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("exit")) {
			window.dispose();
			lastUI = new MainUI(systemController);
		}
	}
}
