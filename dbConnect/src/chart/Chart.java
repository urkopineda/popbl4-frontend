package chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Esta clase se encarga de generar gr�ficos a partir de unos datos dados.
 * 
 * @author Urko
 *
 */
public class Chart {
	static JFreeChart chart = null;
	static BufferedImage chartImage = null;
	
	/**
	 * Este m�todo actualiza todos los componentes necesarios al insertar el gr�fico.
	 * 
	 * @param labelContainer
	 * @param chartContainer
	 */
	private static void updateComponents(JPanel labelContainer, JLabel chartContainer) {
		if ((labelContainer.getWidth() == 0) && (labelContainer.getHeight() == 0)) {
			chartImage = chart.createBufferedImage(1351, 596);
		} else chartImage = chart.createBufferedImage(labelContainer.getWidth(), labelContainer.getHeight());        
		chartContainer.setSize(labelContainer.getSize());
		chartContainer.setIcon(new ImageIcon(chartImage));
		labelContainer.updateUI();
	}
	
	/**
	 * Est� clase crea un gr�fico de barras.
	 * 
	 * @param labelContainer
	 * @param chartContainer
	 * @param data
	 * @param barNames
	 * @param chartName
	 * @param xAxis
	 * @param yAxis
	 */
	public static void createBarChart(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, ArrayList<String> barNames, String chartName, String xAxis, String yAxis) {
		DefaultCategoryDataset proportions = new DefaultCategoryDataset();
		for (int i = 0; i != data.size(); i++) proportions.addValue(data.get(i), barNames.get(i), "");
		chart = ChartFactory.createBarChart3D(chartName, xAxis, yAxis, proportions, PlotOrientation.VERTICAL, true, true, true);
		updateComponents(labelContainer, chartContainer);
	}
	
	/**
	 * Este m�todo genera un gr�fico de lineas.
	 * 
	 * @param labelContainer
	 * @param chartContainer
	 * @param data
	 * @param chartName
	 * @param xAxis
	 * @param yAxis
	 */
	public static void createLineChartv1(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, String chartName, String xAxis, String yAxis) {
		XYSeries series = new XYSeries(chartName);
		for (int i = 0; i != data.size(); i++) series.add(Double.valueOf(i + 1), Double.valueOf(data.get(i)));
		XYSeriesCollection displayData = new XYSeriesCollection(series);
        chart = ChartFactory.createXYLineChart(chartName, xAxis, yAxis, displayData, PlotOrientation.VERTICAL, true, true, true);
        updateComponents(labelContainer, chartContainer);
	}
	
	/**
	 * Este m�todo genera un gr�fico de lineas.
	 * 
	 * @param labelContainer
	 * @param chartContainer
	 * @param data
	 * @param barNames
	 * @param chartName
	 * @param xAxis
	 * @param yAxis
	 */
	public static void createLineChartv2(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, ArrayList<String> barNames, String chartName, String xAxis, String yAxis) {
		DefaultCategoryDataset proportions = new DefaultCategoryDataset();
		for (int i = 0; i != data.size(); i++) proportions.addValue(data.get(i), chartName, barNames.get(i));
        chart = ChartFactory.createLineChart(chartName, xAxis, yAxis, proportions, PlotOrientation.VERTICAL, true, true, true);
        updateComponents(labelContainer, chartContainer);
	}
	
	/**
	 * Este m�todo genera un gr�fico de lineas.
	 * 
	 * @param labelContainer
	 * @param chartContainer
	 * @param data
	 * @param barNames
	 * @param chartName
	 * @param xAxis
	 * @param yAxis
	 * @param color
	 */
	public static void createLineChartv3(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, ArrayList<String> barNames, String chartName, String xAxis, String yAxis, Color color) {
		DefaultCategoryDataset proportions = new DefaultCategoryDataset();
		for (int i = 0; i != data.size(); i++) proportions.addValue(data.get(i), chartName, barNames.get(i));
        chart = ChartFactory.createLineChart(chartName, xAxis, yAxis, proportions, PlotOrientation.VERTICAL, true, true, true);
        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        if (color == null) {
        	renderer.setSeriesPaint(0, Color.RED);
        } else {
        	renderer.setSeriesPaint(0, color);
        }
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setRenderer(renderer);
        updateComponents(labelContainer, chartContainer);
	}
	
	/**
	 * Este m�todo genera un gr�fico Pie.
	 * 
	 * @param labelContainer
	 * @param chartContainer
	 * @param data
	 * @param barNames
	 * @param chartName
	 */
	public static void createPieChart(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, ArrayList<String> barNames, String chartName) {
		DefaultPieDataset proportions = new DefaultPieDataset();
		for (int i = 0; i != data.size(); i++) proportions.setValue(barNames.get(i), data.get(i));
		chart = ChartFactory.createPieChart3D(chartName, proportions, true, true, false);
		updateComponents(labelContainer, chartContainer);
	}
}
