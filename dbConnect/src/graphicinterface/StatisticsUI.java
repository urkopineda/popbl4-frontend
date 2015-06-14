package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import playerModel.MiLoadScreen;
import language.Strings;
import main.Configuration;
import model.ChartData;
import statistics.StatisticsFormulas;
import utils.WindowMaker;
import chart.Chart;
import database.MySQLUtils;

/**
 * Esta clase genera la JPane donde estarán los gráficos.
 * 
 * @author Urko
 *
 */
public class StatisticsUI {
	JPanel mainPanel = null;
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JPanel graphicPanel1 = null;
	JLabel graphicLabel1 = null;
	JComboBox<String> trainingsCB = null;
	JComboBox<String> modeCB = null;
	ItemListener item = null;
	MiLoadScreen load = null;
	JFrame window = null;
	
	ArrayList<ChartData> allData = null;
	
	public StatisticsUI(ItemListener item, JFrame window) {
		this.item = item;
		this.window = window;
	}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		return mainPanel;
	}
	
	public void addContent() {
		mainPanel.removeAll();
		mainPanel.setBorder(BorderFactory.createTitledBorder(Strings.get("statisticsMain")));
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		createData();
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
	}
	
	private Container createNorthPanel() {
		northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		trainingsCB = WindowMaker.createJComboBox(null, item);
		modeCB = WindowMaker.createJComboBox(null, item);
		modeCB.addItem(Strings.get("statsModeTime"));
		modeCB.addItem(Strings.get("graphBPM"));
		modeCB.addItem(Strings.get("graphPPM"));
		northPanel.add(trainingsCB);
		northPanel.add(modeCB);
		return northPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(1, 1, 5, 5));
		centerPanel.add(createGraphic());
		return centerPanel;
	}
	
	/**
	 * Genera el gráfico que se va a implementar en un JLabel.
	 * 
	 * @return
	 */
	private Container createGraphic() {
		return graphicPanel1 = new JPanel(new BorderLayout());
	}
	
	/**
	 * Método encargado de generar cualquier gráfico.
	 * 
	 * @param containerPanel
	 * @param chartLabel
	 * @param trainings
	 * @param mode
	 */
	private void createAllTimesChart(JPanel containerPanel, JLabel chartLabel, int trainings, int mode) {
		chartLabel = new JLabel();
		containerPanel.removeAll();
		if (trainings == 0) {
			if (mode == 0) {
				ArrayList<Integer> timeData = new ArrayList<>();
				ArrayList<String> timeColumns = new ArrayList<>();
				for (int i = 0; i != allData.size(); i++) {
					String time [] = allData.get(i).duration.split(":");
					timeData.add(((Integer.parseInt(time[0]) * 60) + Integer.parseInt(time[1])));
					timeColumns.add(Strings.get("graphTrainingGraph")+allData.get(i).trainingNumber);
				}
				Chart.createLineChartv3(containerPanel, chartLabel, timeData, timeColumns, Strings.get("graphTime"), Strings.get("graphTraining"), Strings.get("statsModeTime"), Color.GREEN);
			} else if (mode == 1) {
				ArrayList<Integer> bpmData = new ArrayList<>();
				ArrayList<String> bpmColumns = new ArrayList<>();
				for (int i = 0; i != allData.size(); i++) {
					StatisticsFormulas newMean = new StatisticsFormulas(allData.get(i).bpm);
					bpmData.add(((int) newMean.getMean()));
					bpmColumns.add(Strings.get("graphTrainingGraph")+allData.get(i).trainingNumber);
				}
				Chart.createLineChartv3(containerPanel, chartLabel, bpmData, bpmColumns, Strings.get("graphMeanBPM"), Strings.get("graphTraining"), Strings.get("graphBPM"), Color.BLUE);
			} else if (mode == 2) {
				ArrayList<Integer> ppmData = new ArrayList<>();
				ArrayList<String> ppmColumns = new ArrayList<>();
				for (int i = 0; i != allData.size(); i++) {
					StatisticsFormulas newMean = new StatisticsFormulas(allData.get(i).ppm);
					ppmData.add(((int) newMean.getMean()));
					ppmColumns.add(Strings.get("graphTrainingGraph")+allData.get(i).trainingNumber);
				}
				Chart.createLineChartv3(containerPanel, chartLabel, ppmData, ppmColumns, Strings.get("graphMeanPPM"), Strings.get("graphTraining"), Strings.get("graphPPM"), Color.RED);
			}
		} else {
			ChartData specificData = allData.get(trainings - 1);
			if (mode == 0) {
				chartLabel.setText(Strings.get("graphNotAvaliable"));
				chartLabel.setHorizontalAlignment(JLabel.CENTER);
				chartLabel.setVerticalAlignment(JLabel.CENTER);
			} else if (mode == 1) {
				ArrayList<Integer> bpmData = new ArrayList<>();
				ArrayList<String> bpmColumns = new ArrayList<>();
				for (int i = 0; i != specificData.bpm.size(); i++) {
					bpmData.add(specificData.bpm.get(i));
					bpmColumns.add("#"+(i + 1));
				}
				Chart.createLineChartv3(containerPanel, chartLabel, bpmData, bpmColumns, Strings.get("graphBPM"), Strings.get("graphSong"), Strings.get("graphBPM"), Color.BLUE);
			} else if (mode == 2) {
				ArrayList<Integer> ppmData = new ArrayList<>();
				ArrayList<String> ppmColumns = new ArrayList<>();
				for (int i = 0; i != specificData.ppm.size(); i++) {
					ppmData.add(specificData.ppm.get(i));
					ppmColumns.add("#"+(i + 1));
				}
				Chart.createLineChartv3(containerPanel, chartLabel, ppmData, ppmColumns, Strings.get("graphPPM"), Strings.get("graphInterval"), Strings.get("graphPPM"), Color.RED);
			}
		}
		containerPanel.add(chartLabel, BorderLayout.CENTER);
		containerPanel.updateUI();
	}
	
	/**
	 * Método public de acceso al método privado de generar gráficos.
	 * 
	 * @param trainings
	 * @param mode
	 */
	public void addGraphics(int trainings, int mode) {
		createAllTimesChart(graphicPanel1, graphicLabel1, trainings, mode);
	}
	
	/**
	 * Crea todos los datos necesarios para generar los gráficos (Hace un SELECT).
	 */
	private void createData() {
		load = new MiLoadScreen();
		MySQLUtils db = new MySQLUtils();
		allData = new ArrayList<>();
		trainingsCB.addItem(Strings.get("graphAllTrainings"));
		try {
			db.openDataBase();
			ResultSet rsEntrenamiento = db.exeQuery("SELECT * FROM ENTRENAMIENTO WHERE UsuarioID = "+Configuration.userID);
			ResultSet countTrainings = db.exeQuery("SELECT COUNT(*) AS N FROM ENTRENAMIENTO WHERE UsuarioID = "+Configuration.userID);
			countTrainings.next();
			load.setWorkToMake(countTrainings.getInt("N"));
			int i = 1;
			while (rsEntrenamiento.next()) {
				int trainingNumber = i;
				trainingsCB.addItem(Strings.get("graphTrainingGraph")+i);
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
				load.progressHasBeenMade("Entrenamiento Nº"+i, 1);
				i++;
			}
			load.closeScreen();
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
