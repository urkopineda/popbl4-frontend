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
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Chart {
	JFreeChart chart = null;
	BufferedImage chartImage = null;
	
	private void updateComponents(JPanel labelContainer, JLabel chartContainer) {
		chartImage = chart.createBufferedImage(labelContainer.getWidth(), labelContainer.getHeight());        
		chartContainer.setSize(labelContainer.getSize());
		chartContainer.setIcon(new ImageIcon(chartImage));
		labelContainer.updateUI();
	}
	
	public void createBarChart(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, ArrayList<String> barNames, String chartName, String xAxis, String yAxis) {
		DefaultCategoryDataset proportions = new DefaultCategoryDataset();
		for (int i = 0; i <= data.size(); i++) proportions.setValue(data.get(i), barNames.get(i), "");
		chart = ChartFactory.createBarChart3D(chartName, xAxis, yAxis, proportions, PlotOrientation.VERTICAL, true, true, true);
		updateComponents(labelContainer, chartContainer);
	}
	
	public void createLineChart(JPanel labelContainer, JLabel chartContainer, ArrayList<Integer> data, ArrayList<String> barNames, String chartName, String xAxis, String yAxis) {
		XYSeries series = new XYSeries(chartName);
		for (int i = 0; i <= data.size(); i++) series.add(Double.valueOf(data.get(i)), Double.valueOf(i));
		XYSeriesCollection displayData = new XYSeriesCollection(series);
        chart = ChartFactory.createXYLineChart(chartName, xAxis, yAxis, displayData, PlotOrientation.HORIZONTAL, true, true, true);
        updateComponents(labelContainer, chartContainer);
	}
}
