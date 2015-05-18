package chart;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import utils.WindowMaker;

/**
 * Clase para crear un gráfico 'Pie'.
 * 
 * @author Runnstein Team
 */
public class PieChart implements ActionListener {
	JFrame window = null;
	JPanel mainPanel = null;
	JPanel chartPanel = null;
	
	JButton btn = null;
	JLabel lbl = null;
	
	DefaultPieDataset proportions = null;
	JFreeChart chart = null;
	BufferedImage chartImage = null;

	public PieChart() {
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
		return mainPanel;
	}
	
	public Container createChartPanel() {
		chartPanel = new JPanel(new BorderLayout());
		lbl = WindowMaker.createJLabel(lbl, "", 0);
		chartPanel.add(lbl, BorderLayout.CENTER);
		return chartPanel;
	}
	
	public void createChart() {
		proportions = new DefaultPieDataset();
		proportions.setValue("A", 4550);
		proportions.setValue("B", 4000);
		proportions.setValue("C", 3000);
		proportions.setValue("D", 3500);
		chart = ChartFactory.createPieChart3D("Ejemplo PieChart", proportions, true, true, false);
		chartImage = chart.createBufferedImage(chartPanel.getWidth(), chartPanel.getHeight());        
		lbl.setSize(chartPanel.getSize());
		lbl.setIcon(new ImageIcon(chartImage));
		chartPanel.updateUI();
	}
	
	public static void main(String args[]) {
		@SuppressWarnings("unused")
		PieChart newPrueba = new PieChart();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("show")) {
			createChart();
		}
	}
}
