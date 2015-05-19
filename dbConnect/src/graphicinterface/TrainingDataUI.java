package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Data;
import tablemodel.ColumnTableModelBasic;
import tablemodel.TableModelBasic;
import tablemodel.TrazadorTableBasic;
import utils.WindowMaker;
import administration.Controller;
import database.DataBaseBasics;
import database.StatementBasics;

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
		window.setSize(640, 640);
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
		/*
		 * Aquí debemos hacer lo siguiente:
		 * 		1-. Generar el SELECT que queramos. (VISTAS!)
		 * 		2-. Ejecutarlo y obtener su ResultSet.
		 * 		3-. Crear un nuevo objeto 'Data' con el ResultSet.
		 * 		4-. Utilizar las formulas estadisticas.
		 */
		Data data = pruebaGetData();
		trazador = new TrazadorTableBasic();
		columnModel = new ColumnTableModelBasic(trazador, data);
		tableModel = new TableModelBasic(columnModel, data);
		mainTable = new JTable(tableModel, columnModel);
		mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mainTable.getSelectionModel().addListSelectionListener(this);
		mainTable.setFillsViewportHeight(true);
		scrollMainPanel.setViewportView(mainTable);
	}
	
	/**
	 * PRUEBA! Para borrar en un futuro.
	 * 
	 * @return
	 */
	private Data pruebaGetData() {
		DataBaseBasics dbConnect = new DataBaseBasics();
		try {
			dbConnect.openDataBase();
			StatementBasics stmt = new StatementBasics(dbConnect.getDataBaseConnection());
			ResultSet rsData = stmt.exeQuery("SELECT * FROM MUESTRA");
			ResultSet rsColumns = stmt.exeQuery("SELECT * FROM MUESTRA");
			Data data = new Data(rsData, dbConnect.getNumberRows(rsColumns), dbConnect.getNumberColumns("MUESTRA"));
			return data;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
