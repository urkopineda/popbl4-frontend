package playerModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import language.Strings;


public class MiLoadScreen extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1884269953277974091L;
	JProgressBar progressBar;
	JTextArea textArea;
	int workToMake, workMade;
	
	public MiLoadScreen(JFrame parent, int workToMake) {
		super(Strings.get("loadScreenLoading"));
		setContentPane(fillDialog());
		setSize(450, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2 - getSize().width/2, dim.height/2 - getSize().height/2);
		setWorkToMake(workToMake);
		workMade = 0;
		setVisible(true);
	}
	
	public MiLoadScreen(JFrame parent) {
		super(Strings.get("loadScreenLoading"));
		setContentPane(fillDialog());
		setSize(450, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2 - getSize().width/2, dim.height/2 - getSize().height/2);
		workMade = 0;
		setVisible(true);
	}
	
	private JPanel fillDialog() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel textPanel = new JPanel(new BorderLayout());
		textPanel.setPreferredSize(new Dimension(450, 275));
		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setString(String.valueOf(progressBar.getValue())+"%");
		((DefaultCaret) textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textPanel.add(textArea);
		scroll.setViewportView(textPanel);
		panel.add(scroll);
		panel.add(progressBar, BorderLayout.NORTH);
		return panel;
	}
	
	public void setWorkToMake(int value) {
		workToMake = value;
	}
	
	public void progressHasBeenMade(String text, Integer value) {
		textArea.setText(text+"\n"+textArea.getText());
		if (value == null) workMade++;
		else workMade += value;
		progressBar.setValue(workMade*100/workToMake);
		progressBar.setString(String.valueOf(progressBar.getValue())+"%");
		update(getGraphics());
	}
	
	public void publishWithoutProgress(String text) {
		textArea.setText(text+"\n"+textArea.getText());
		update(getGraphics());
	}
	
}
