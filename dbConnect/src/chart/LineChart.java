package chart;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import utils.WindowMaker;

public class LineChart implements ActionListener {
	JFrame window = null;
	JPanel mainPanel = null;
	JPanel chartPanel = null;
	JPanel lblPanel = null;
	
	JButton btn = null;
	JLabel lbl = null;
	
	JTextField lbl1 = null;
	
	XYSeries series = null;
    XYDataset data = null;
	JFreeChart chart = null;
	BufferedImage chartImage = null;

	public LineChart() {
		window = new JFrame("Chart");
		window.setSize(640, 640);
		window.setLocation(50, 50);
		window.setContentPane(createMainPanel());
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		btn = WindowMaker.createJButton(btn, "Chart", "show", null, this, false);
		mainPanel.add(btn, BorderLayout.SOUTH);
		mainPanel.add(createChartPanel(), BorderLayout.CENTER);
		mainPanel.add(createLabelPanel(), BorderLayout.NORTH);
		return mainPanel;
	}
	
	public Container createChartPanel() {
		chartPanel = new JPanel(new BorderLayout());
		lbl = WindowMaker.createJLabel(lbl, "", 0);
		chartPanel.add(lbl, BorderLayout.CENTER);
		return chartPanel;
	}
	
	public Container createLabelPanel() {
		lblPanel = new JPanel(new GridLayout(1, 1, 0, 0));
		lbl1 = new JTextField();
		lblPanel.add(lbl1);
		return lblPanel;
	}
	
	public void createChart() {
		series = new XYSeries("Popularidad de Blog");
		int x = 0;
        series.add(0,x);
        x++;
        series.add(5,x);
        x++;
        series.add(10,x);
        x++;
        series.add(Integer.parseInt(lbl1.getText()),x);
        x++;
        data = new XYSeriesCollection(series);
        chart = ChartFactory.createXYLineChart("Ejemplo LineChart", "Izquierda", "Abajo", data, PlotOrientation.HORIZONTAL, true, true, true);
		chartImage = chart.createBufferedImage(chartPanel.getWidth(), chartPanel.getHeight());        
		lbl.setSize(chartPanel.getSize());
		lbl.setIcon(new ImageIcon(chartImage));
		chartPanel.updateUI();
	}
	
	public static void main(String args[]) {
		@SuppressWarnings("unused")
		LineChart newPrueba = new LineChart();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("show")) {
			createChart();
		}
	}
}
