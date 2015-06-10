package playerModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import language.Strings;


public class MiLoadScreen extends JFrame {
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
		JLabel label = new JLabel(Strings.get("loadScreenFirstLoadWarning"));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font(label.getFont().getName(), Font.BOLD, 13));
		textPanel.setPreferredSize(new Dimension(450, 275));
		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		progressBar = new JProgressBar();
		((DefaultCaret) textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textPanel.add(textArea);
		scroll.setViewportView(textPanel);
		panel.add(scroll);
		panel.add(progressBar, BorderLayout.NORTH);
		panel.add(label, BorderLayout.SOUTH);
		return panel;
	}
	
	public void setWorkToMake(int value) {
		workToMake = value;
	}
	
	public void progressHasBeenMade(String text) {
		textArea.setText(text+"\n"+textArea.getText());
		progressBar.setValue(++workMade*100/workToMake);
		update(getGraphics());
	}
	
	public void publishWithoutProgress(String text) {
		textArea.setText(text+"\n"+textArea.getText());
		update(getGraphics());
	}
	
}
