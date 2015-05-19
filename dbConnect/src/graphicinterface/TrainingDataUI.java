package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.TableData;
import tablemodel.ColumnTableModelBasic;
import tablemodel.TableModelBasic;
import tablemodel.TrazadorTableBasic;
import utils.WindowMaker;
import administration.Controller;
import database.DataBaseBasics;
import database.StatementBasics;
import exception.RunnsteinDataBaseException;

/**
 * UI de los datos de cada entrenamiento.
 * 
 * @author Runnstein Team
 */
public class TrainingDataUI implements ActionListener, ListSelectionListener {
	MainUI lastUI = null;
	JFrame window = null;
	Controller systemController = null;
	ColumnTableModelBasic columnModel = null;
	TableModelBasic tableModel = null;
	TrazadorTableBasic trazador = null;
	JScrollPane scrollMainPanel = null;
	JPanel mainPanel = null;
	JTable mainTable = null;
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JPanel southPanel = null;
	JButton buttonUpdate = null;
	JButton buttonExit = null;
	
	ArrayList<TableData> tableData = null;
	
	/**
	 * Contructor que utiliza la ventana anterior (JFrame) para construir la nueva UI.
	 * 
	 * @param Controller systemController
	 * @param MainUI lastUI
	 */
	public TrainingDataUI(Controller systemController, MainUI lastUI) {
		this.window = lastUI.window;
		this.systemController = systemController;
		window.setTitle("Mis Entrenamientos");
		window.setSize(1000, 640);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2 - window.getSize().width/2, dim.height/2 - window.getSize().height/2);
		window.setContentPane(createMainPanel());
		createTable();
		window.repaint();
		window.revalidate();
	}
	
	/**
	 * Crea la tabla utilizando métodos estadísticos, modelos de tabla y columna y un trazador. 
	 */
	private void createTable() {
		DataBaseBasics newConnect = new DataBaseBasics();
		try {
			newConnect.openDataBase();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (RunnsteinDataBaseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		StatementBasics stmt = new StatementBasics(newConnect);
		ArrayList<TableData> allData = stmt.generateTableData();
		if (allData == null) System.out.println("NULLLLLLLLLLLLLLLLl");
			trazador = new TrazadorTableBasic();
			columnModel = new ColumnTableModelBasic(trazador);
			tableModel = new TableModelBasic(columnModel, allData);
			mainTable = new JTable(tableModel, columnModel);
			mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			mainTable.getSelectionModel().addListSelectionListener(this);
			mainTable.setFillsViewportHeight(true);
			scrollMainPanel.setViewportView(mainTable);
		
		try {
			newConnect.closeDataBase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Crea el panel principal con sus sub-paneles.
	 * 
	 * @return JPanel mainPanel
	 */
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}
	
	/**
	 * Crea el panel norte.
	 * 
	 * @return JPanel northPanel
	 */
	private Container createNorthPanel() {
		northPanel = new JPanel();
		
		return northPanel;
	}
	
	/**
	 * Crea el panel central con el JTable.
	 * 
	 * @return JPanel centerPanel
	 */
	private Container createCenterPanel() {
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		centerPanel.add(scrollMainPanel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		return centerPanel;
	}
	
	/**
	 * Crea el panel sur con los botones necesarios.
	 * 
	 * @return JPanel southPanel
	 */
	private Container createSouthPanel() {
		southPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		buttonUpdate = WindowMaker.createJButton(buttonUpdate, "Actualizar datos", "update", null, this, false);
		buttonExit = WindowMaker.createJButton(buttonExit, "Salir", "exit", null, this, false);
		southPanel.add(buttonUpdate);
		southPanel.add(buttonExit);
		return southPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("exit")) {
			window.dispose();
			lastUI = new MainUI(systemController);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		
	}
}
