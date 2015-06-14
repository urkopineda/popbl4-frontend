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


/**
 * Pantalla de carga personalizada para que los periodos de cargas sean más amenos.
 * @author unaipme
 *
 */
public class MiLoadScreen extends JFrame {
	private static final long serialVersionUID = 1884269953277974091L;
	
	JProgressBar progressBar;
	JTextArea textArea;
	int workToMake, workMade;
	
	/**
	 * @param workToMake: Cantidad de trabajo que hay que hacer (Cifra que representaría el 100% del proceso)
	 */
	public MiLoadScreen(int workToMake) {
		super(Strings.get("loadScreenLoading"));
		setContentPane(fillDialog());
		setSize(450, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2 - getSize().width/2, dim.height/2 - getSize().height/2);
		setWorkToMake(workToMake);
		workMade = 0;
		setVisible(true);
	}
	
	public MiLoadScreen() {
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
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
	
	/**
	 * Hacer que la barra progrese.
	 * @param text: Texto de actualización que se mostrará en el área de texto.
	 * @param value: Cantidad de progreso hecho, respecto a la cantidad de trabajo por hacer establecido.
	 */
	public void progressHasBeenMade(String text, Integer value) {
		textArea.setText(text+"\n"+textArea.getText());
		if (value == null) workMade++;
		else workMade += value;
		progressBar.setValue(workMade*100/workToMake);
		progressBar.setString(String.valueOf(progressBar.getValue())+"%");
		update(getGraphics());
	}
	
	/**
	 * Publicar una actualización en el área de texto sin hacer que la barra progrese.
	 * @param text: Texto a mostrar.
	 */
	public void publishWithoutProgress(String text) {
		textArea.setText(text+"\n"+textArea.getText());
		update(getGraphics());
	}
	
	public void closeScreen() {
		this.dispose();
	}
}
