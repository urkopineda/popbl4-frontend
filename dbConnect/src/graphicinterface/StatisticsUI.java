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
	ArrayList<ChartData> allData = null;
	ArrayList<Integer> timeData = null;
	ArrayList<String> timeColumns = null;
	ItemListener item = null;
	
	public StatisticsUI(ItemListener item) {
		this.item = item;
	}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createTitledBorder(Strings.statisticsMain));
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		createData();
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		return mainPanel;
	}
	
	private Container createNorthPanel() {
		northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		trainingsCB = WindowMaker.createJComboBox(trainingsCB, null, item);
		modeCB = WindowMaker.createJComboBox(modeCB, null, item);
		modeCB.addItem(Strings.statsModeTime);
		modeCB.addItem(Strings.statsModeMax);
		modeCB.addItem(Strings.statsModeMean);
		northPanel.add(trainingsCB);
		northPanel.add(modeCB);
		return northPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(1, 1, 5, 5));
		centerPanel.add(createGraphic1());
		return centerPanel;
	}
	
	// ####################################################### PRUEBAS #######################################################
	private Container createGraphic1() {
		graphicPanel1 = new JPanel(new BorderLayout());
		
		return graphicPanel1;
	}
	// ####################################################### FIN #######################################################
	
	private void createAllTimesChart(JPanel containerPanel, JLabel chartLabel) {
		chartLabel = new JLabel();
		Chart.createLineChartv2(containerPanel, chartLabel, timeData, timeColumns, Strings.graphTime, Strings.graphTraining, Strings.statsModeTime);
		containerPanel.add(chartLabel, BorderLayout.CENTER);
		containerPanel.updateUI();
	}
	
	public void addGraphics(String trainings, String mode) {
		if (trainings.equals("all")) {
			if (mode.equals("time")) {
				createAllTimesChart(graphicPanel1, graphicLabel1);
			} else if (mode.equals("max")) {
				
			} else if (mode.equals("mean")) {
				
			}
		} else {
			int trainingNumber = Integer.parseInt(trainings);
			if (mode.equals("time")) {
				
			} else if (mode.equals("max")) {
				
			} else if (mode.equals("mean")) {
				
			}
		}
	}
	
	private void createData() {
		MySQLUtils db = new MySQLUtils();
		allData = new ArrayList<>();
		timeData = new ArrayList<>();
		timeColumns = new ArrayList<>();
		trainingsCB.addItem("TODOS LOS ENTRENAMIENTOS");
		try {
			db.openDataBase();
			ResultSet rsEntrenamiento = db.exeQuery("SELECT * FROM ENTRENAMIENTO WHERE UsuarioID = "+Configuration.userID);
			int i = 1;
			while (rsEntrenamiento.next()) {
				String dateTime = rsEntrenamiento.getString(3);
				String duration = rsEntrenamiento.getString(4);
				String time [] = duration.split(":");
				timeColumns.add("Entrenamiento Nº"+i);
				timeData.add(((Integer.parseInt(time[0]) * 60) + Integer.parseInt(time[1])));
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
				trainingsCB.addItem("Entrenamiento #"+i+" - "+dateTime);
				allData.add(new ChartData(i, dateTime, duration, rateMean, rateMax, stability));
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
		for (int i = 0; i != allData.size(); i++) {
			System.out.println();
			System.out.println(allData.get(i).trainingNumber);
			System.out.println(allData.get(i).dateTime);
			System.out.println(allData.get(i).duration);
			System.out.println(allData.get(i).rateMax);
			System.out.println(allData.get(i).rateMean);
			System.out.println();
		}
		for (int i = 0; i != timeColumns.size(); i++) {
			System.out.println();
			System.out.println(timeColumns.get(i));
			System.out.println();
		}
		for (int i = 0; i != timeData.size(); i++) {
			System.out.println();
			System.out.println(timeData.get(i));
			System.out.println();
		}
	}
}
