package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import model.TableData;
import tablemodel.ColumnTableModelBasic;
import tablemodel.TableModelBasic;
import tablemodel.TrazadorTableBasic;
import database.DataBaseBasics;
import database.StatementBasics;
import exception.RunnsteinDataBaseException;

public class TrainingDataUI {
	JPanel mainPanel = null;
	ListSelectionListener act = null;
	ColumnTableModelBasic columnModel = null;
	TableModelBasic tableModel = null;
	TrazadorTableBasic trazador = null;
	JScrollPane scrollMainPanel = null;
	JTable mainTable = null;
	
	public TrainingDataUI(ListSelectionListener act) {
		this.act = act;
	}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createTitledBorder("Resumen de Entrenamientos"));
		mainPanel.add(scrollMainPanel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		createTable();
		return mainPanel;
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
		if (allData == null) {
			trazador = new TrazadorTableBasic();
			columnModel = new ColumnTableModelBasic(trazador);
			tableModel = new TableModelBasic(columnModel, allData);
			mainTable = new JTable(tableModel, columnModel);
			mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			mainTable.getSelectionModel().addListSelectionListener(act);
			mainTable.setFillsViewportHeight(true);
			scrollMainPanel.setViewportView(mainTable);
		}
		try {
			newConnect.closeDataBase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
