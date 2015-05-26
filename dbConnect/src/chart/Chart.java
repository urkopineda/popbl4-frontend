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

public class Chart {
	static JFreeChart chart = null;
	static BufferedImage chartImage = null;
	
	private static void updateComponents(JPanel labelContainer, JLabel chartContainer) {
		chartImage = chart.createBufferedImage(labelContainer.getWidth(), labelContainer.getHeight());        
		chartContainer.setSize(labelContainer.getSize());
		chartContainer.setIcon(new ImageIcon(chartImage));
		labelContainer.updateUI();
	}
	
	public static void createBarChart(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, ArrayList<String> barNames, String chartName, String xAxis, String yAxis) {
		DefaultCategoryDataset proportions = new DefaultCategoryDataset();
		for (int i = 0; i != data.size(); i++) proportions.addValue(data.get(i), barNames.get(i), "");
		chart = ChartFactory.createBarChart3D(chartName, xAxis, yAxis, proportions, PlotOrientation.VERTICAL, true, true, true);
		updateComponents(labelContainer, chartContainer);
	}
	
	public static void createLineChartv1(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, String chartName, String xAxis, String yAxis) {
		XYSeries series = new XYSeries(chartName);
		for (int i = 0; i != data.size(); i++) series.add(Double.valueOf(i + 1), Double.valueOf(data.get(i)));
		XYSeriesCollection displayData = new XYSeriesCollection(series);
        chart = ChartFactory.createXYLineChart(chartName, xAxis, yAxis, displayData, PlotOrientation.VERTICAL, true, true, true);
        updateComponents(labelContainer, chartContainer);
	}
	
	public static void createLineChartv2(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, ArrayList<String> barNames, String chartName, String xAxis, String yAxis) {
		DefaultCategoryDataset proportions = new DefaultCategoryDataset();
		for (int i = 0; i != data.size(); i++) proportions.addValue(data.get(i), chartName, barNames.get(i));
        chart = ChartFactory.createLineChart(chartName, xAxis, yAxis, proportions, PlotOrientation.VERTICAL, true, true, true);
        updateComponents(labelContainer, chartContainer);
	}
	
	public static void createPieChart(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, ArrayList<String> barNames, String chartName) {
		DefaultPieDataset proportions = new DefaultPieDataset();
		for (int i = 0; i != data.size(); i++) proportions.setValue(barNames.get(i), data.get(i));
		chart = ChartFactory.createPieChart3D(chartName, proportions, true, true, false);
		updateComponents(labelContainer, chartContainer);
	}
}
