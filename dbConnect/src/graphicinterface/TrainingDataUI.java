package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import language.Strings;
import main.Configuration;
import model.TableData;
import statistics.StatisticsFormulas;
import tablemodel.ColumnTableModelBasic;
import tablemodel.TableModelBasic;
import tablemodel.TrazadorTableBasic;
import database.MySQLUtils;


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
		mainPanel.setBorder(BorderFactory.createTitledBorder(Strings.trainingDataMain));
		mainPanel.add(scrollMainPanel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		createTable();
		return mainPanel;
	}
	
	private void createTable() {
		MySQLUtils db = new MySQLUtils();
		ArrayList<TableData> allData = new ArrayList<>();
		try {
			db.openDataBase();
			ResultSet rsEntrenamiento = db.exeQuery("SELECT * FROM ENTRENAMIENTO WHERE UsuarioID = "+Configuration.userID);
			int i = 1;
			while (rsEntrenamiento.next()) {
				String dateTime = rsEntrenamiento.getString(3);
				String duration = rsEntrenamiento.getString(4);
				ResultSet rsIntervalo = db.exeQuery("SELECT * FROM INTERVALO WHERE EntrenamientoID = "+rsEntrenamiento.getInt(1));
				ArrayList<Integer> ppm = new ArrayList<>();
				while (rsIntervalo.next()) {
					ResultSet rsMuestra = db.exeQuery("SELECT * FROM MUESTRA WHERE IntervaloID = "+rsIntervalo.getInt(1));
					while (rsMuestra.next()) {
						ppm.add(rsMuestra.getInt(3));
					}
				}
				StatisticsFormulas formulas = new StatisticsFormulas(ppm);
				double rateMean = formulas.getMean();
				double rateMax = formulas.getMax();
				int stability = formulas.getStability();
				allData.add(new TableData(i, dateTime, duration, rateMean, rateMax, stability));
				i++;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
		} finally {
			try {
				db.closeDataBase();
			} catch (SQLException e) {
				System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
				e.printStackTrace();
			}
		}
		if (allData != null) {
			trazador = new TrazadorTableBasic();
			columnModel = new ColumnTableModelBasic(trazador);
			tableModel = new TableModelBasic(columnModel, allData);
			mainTable = new JTable(tableModel, columnModel);
			mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			mainTable.getSelectionModel().addListSelectionListener(act);
			mainTable.setFillsViewportHeight(true);
			scrollMainPanel.setViewportView(mainTable);
		}
	}
}
