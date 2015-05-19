package chart;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Se encarga de generar gráficos dentro de JLabel-s.
 * 
 * @author Runnstein Team
 */
public class Chart {
	JFreeChart chart = null;
	BufferedImage chartImage = null;
	
	/**
	 * Actualiza todos los componentes al finalizar la creación del Chart.
	 * 
	 * @param JPanel labelContainer
	 * @param JLabel chartContainer
	 */
	private void updateComponents(JPanel labelContainer, JLabel chartContainer) {
		chartImage = chart.createBufferedImage(labelContainer.getWidth(), labelContainer.getHeight());        
		chartContainer.setSize(labelContainer.getSize());
		chartContainer.setIcon(new ImageIcon(chartImage));
		labelContainer.updateUI();
	}
	
	/**
	 * Crea un gráfico de barras a partir de los siguientes parámetros:
	 * 
	 * @param JPanel labelContainer
	 * @param JLabel chartContainer
	 * @param ArrayList<Integer> data
	 * @param ArrayList<String> barNames
	 * @param String chartName
	 * @param String xAxis
	 * @param String yAxis
	 */
	public void createBarChart(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, ArrayList<String> barNames, String chartName, String xAxis, String yAxis) {
		DefaultCategoryDataset proportions = new DefaultCategoryDataset();
		for (int i = 0; i <= data.size(); i++) proportions.setValue(data.get(i), barNames.get(i), "");
		chart = ChartFactory.createBarChart3D(chartName, xAxis, yAxis, proportions, PlotOrientation.VERTICAL, true, true, true);
		updateComponents(labelContainer, chartContainer);
	}
	
	/**
	 * Crea un gráfico de lineas a partir de los siguientes parámetros:
	 * 
	 * @param JPanel labelContainer
	 * @param JLabel chartContainer
	 * @param ArrayList<Integer> data
	 * @param ArrayList<String> barNames
	 * @param String chartName
	 * @param String xAxis
	 * @param String yAxis
	 */
	public void createLineChart(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, ArrayList<String> barNames, String chartName, String xAxis, String yAxis) {
		XYSeries series = new XYSeries(chartName);
		for (int i = 0; i <= data.size(); i++) series.add(Double.valueOf(data.get(i)), Double.valueOf(i));
		XYSeriesCollection displayData = new XYSeriesCollection(series);
        chart = ChartFactory.createXYLineChart(chartName, xAxis, yAxis, displayData, PlotOrientation.HORIZONTAL, true, true, true);
        updateComponents(labelContainer, chartContainer);
	}
	
	/**
	 * Crea un gráfico de torta (pie chart) de los siguientes parámetros:
	 * 
	 * @param JPanel labelContainer
	 * @param JLabel chartContainer
	 * @param ArrayList<Integer> data
	 * @param ArrayList<String> barNames
	 * @param String chartName
	 */
	public void createPieChart(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, ArrayList<String> barNames, String chartName) {
		DefaultPieDataset proportions = new DefaultPieDataset();
		for (int i = 0; i <= data.size(); i++) proportions.setValue(barNames.get(i), data.get(i));
		chart = ChartFactory.createPieChart3D(chartName, proportions, true, true, false);
		updateComponents(labelContainer, chartContainer);
	}
}
