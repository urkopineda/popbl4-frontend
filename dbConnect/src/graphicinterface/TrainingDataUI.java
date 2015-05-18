package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.Configuration;
import model.Data;
import tablemodel.ColumnTableModelBasic;
import tablemodel.TableModelBasic;
import tablemodel.TrazadorTableBasic;
import administration.Controller;
import database.DataBaseBasics;
import database.StatementBasics;

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
	
	public TrainingDataUI(Controller systemController, MainUI lastUI) {
		this.lastUI = lastUI;
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
	
	private void createTable() {
		/*
		 * Aquí debemos hacer lo siguiente:
		 * 		1-. Generar el SELECT que queramos.
		 * 		2-. Ejecutarlo y obtener su ResultSet.
		 * 		3-. Crear un nuevo objeto 'Data' con el ResultSet.
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
	
	private Data pruebaGetData() {
		DataBaseBasics dbConnect = new DataBaseBasics(Configuration.dbUrl, Configuration.port, Configuration.user, Configuration.password, Configuration.tableName);
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
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		centerPanel.add(scrollMainPanel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
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

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
