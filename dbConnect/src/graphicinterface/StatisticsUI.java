package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import language.Strings;
import main.Configuration;
import model.ChartData;
import statistics.StatisticsFormulas;
import utils.WindowMaker;
import chart.Chart;
import database.MySQLUtils;

public class StatisticsUI {
	JPanel mainPanel = null;
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JPanel graphicPanel1 = null;
	JLabel graphicLabel1 = null;
	JComboBox<String> trainingsCB = null;
	JComboBox<String> modeCB = null;
	ItemListener item = null;
	
	ArrayList<ChartData> allData = null;
	
	public StatisticsUI(ItemListener item) {
		this.item = item;
	}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createTitledBorder(Strings.get("statisticsMain")));
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		createData();
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		return mainPanel;
	}
	
	private Container createNorthPanel() {
		northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		trainingsCB = WindowMaker.createJComboBox(trainingsCB, null, item);
		modeCB = WindowMaker.createJComboBox(modeCB, null, item);
		modeCB.addItem(Strings.get("statsModeTime"));
		modeCB.addItem(Strings.get("statsModeMax"));
		modeCB.addItem(Strings.get("statsModeMean"));
		northPanel.add(trainingsCB);
		northPanel.add(modeCB);
		return northPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(1, 1, 5, 5));
		centerPanel.add(createGraphic());
		return centerPanel;
	}
	
	private Container createGraphic() {
		return graphicPanel1 = new JPanel(new BorderLayout());
	}
	
	private void createAllTimesChart(JPanel containerPanel, JLabel chartLabel, int trainings, int mode) {
		chartLabel = new JLabel();
		if (trainings == 0) {
			//if (mode == 0) Chart.createLineChartv2(containerPanel, chartLabel, timeData, timeColumns, Strings.graphTime, Strings.graphTraining, Strings.statsModeTime);
		} else {
			
		}
		containerPanel.add(chartLabel, BorderLayout.CENTER);
		containerPanel.updateUI();
	}
	
	public void addGraphics(int trainings, int mode) {
		createAllTimesChart(graphicPanel1, graphicLabel1, trainings, mode);
	}
	
	private void createData() {
		MySQLUtils db = new MySQLUtils();
		allData = new ArrayList<>();
		trainingsCB.addItem("TODOS LOS ENTRENAMIENTOS");
		try {
			db.openDataBase();
			ResultSet rsEntrenamiento = db.exeQuery("SELECT * FROM ENTRENAMIENTO WHERE UsuarioID = "+Configuration.userID);
			int i = 1;
			while (rsEntrenamiento.next()) {
				int trainingNumber = i;
				String dateTime = rsEntrenamiento.getString(3);
				String duration = rsEntrenamiento.getString(4);
				ArrayList<Integer> bpm = new ArrayList<>();
				ArrayList<Integer> ppm = new ArrayList<>();
				ResultSet rsIntervalo = db.exeQuery("SELECT * FROM INTERVALO WHERE EntrenamientoID = "+rsEntrenamiento.getInt(1));
				while (rsIntervalo.next()) {
					bpm.add(rsIntervalo.getInt(3));
					ResultSet rsMuestra = db.exeQuery("SELECT * FROM MUESTRA WHERE IntervaloID = "+rsIntervalo.getInt(1));
					while (rsMuestra.next()) {
						ppm.add(rsMuestra.getInt(3));
					}
				}
				allData.add(new ChartData(trainingNumber, dateTime, duration, ppm, bpm));
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
	}
}
