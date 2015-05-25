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
		//addGraphic1();
		return mainPanel;
	}
	
	private Container createNorthPanel() {
		northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		trainingsCB = WindowMaker.createJComboBox(trainingsCB, null, item);
		modeCB = WindowMaker.createJComboBox(modeCB, null, item);
		addModesToCB();
		northPanel.add(trainingsCB);
		northPanel.add(modeCB);
		return northPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(1, 1, 5, 5));
		centerPanel.add(createGraphic1());
		return centerPanel;
	}
	
	private Container createGraphic1() {
		graphicPanel1 = new JPanel(new BorderLayout());
		
		return graphicPanel1;
	}
	
	
	void addGraphic1() {
		if (trainingsCB.getSelectedIndex() == 0) {
			graphicLabel1 = new JLabel();
			Chart.createLineChart(graphicPanel1, graphicLabel1, timeData(), timeColumns(), Strings.graphTime, Strings.graphTraining, Strings.statsModeTime);
		} else {
			
		}
		graphicPanel1.add(graphicLabel1, BorderLayout.CENTER);
		graphicPanel1.updateUI();
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
	}
	
	private ArrayList<Integer> timeData() {
		ArrayList<Integer> data = new ArrayList<>();
		for (int i = 0; i != allData.size(); i++) {
			String time = allData.get(i).getDuration();
			String timeData [] = time.split(":");
			data.add(((Integer.parseInt(timeData[2]) * 60) + Integer.parseInt(timeData[1])));
		}
		return data;
	}
	
	private ArrayList<String> timeColumns() {
		ArrayList<String> columns = new ArrayList<>();
		for (int i = 0; i != allData.size(); i++) {
			columns.add("Entrenamiento Nº"+(i + 1));
		}
		return columns;
	}
	
	private void addModesToCB() {
		modeCB.addItem(Strings.statsModeTime);
		modeCB.addItem(Strings.statsModeMax);
		modeCB.addItem(Strings.statsModeMin);
	}
}
