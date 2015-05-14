package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import administration.Controller;

/**
 * Clase que se encarga de la UI de la pantalla de Training.
 * 
 * @author Runnstein Team
 */
public class TrainingUI implements ActionListener {
	Controller systemController = null;
	JFrame window = null;
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JPanel hrPanel = null;
	JPanel chPanel = null;
	JPanel southPanel = null;
	JPanel mainPanel = null;
	JLabel labelStart = null;
	JLabel labelPPM = null;
	JButton buttonStart = null;
	JButton buttonStop = null;
	JButton buttonExit = null;
	JLabel cronometerNumbers = null;
	
	HeartRateUI newHR = null;
	int state = -1;
	int ppm = 88;
	
	Timer timer = null;
	int minutes = 0;
	int seconds = 0;
	int miliseconds = 0;
	String sMinutes = null;
	String sSeconds = null;
	String sMiliseconds = null;
	
	/**
	 * Constructor de la UI de Training, utiliza el panel anterior.
	 * 
	 * @param systemController
	 * @param window
	 */
	public TrainingUI(Controller systemController, JFrame window) {
		this.window = window;
		this.systemController = systemController;
		window.setSize(640, 360);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2 - window.getSize().width/2, dim.height/2 - window.getSize().height/2);
		window.setContentPane(createMainPanel());
		window.repaint();
		window.revalidate();
	}
	
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		chPanel = new JPanel(new GridLayout(1, 1, 5, 5));
		cronometerNumbers = WindowMaker.createJLabel(cronometerNumbers, "00:00:00", 75);
		chPanel.add(cronometerNumbers);
		centerPanel.add(chPanel);
		centerPanel.add(createHeartRatePanel());
		return centerPanel;
	}
	
	private Container createHeartRatePanel() {
		hrPanel = new JPanel(new BorderLayout());
		newHR = new HeartRateUI(state);
		hrPanel.add(newHR.getPane(), BorderLayout.CENTER);
		hrPanel.add(WindowMaker.createJLabel(labelPPM, String.valueOf(ppm)+"ppm", 50), BorderLayout.NORTH);
		return hrPanel;
	}
	
	private Container createSouthPanel() {
		southPanel = new JPanel(new GridLayout(1, 3, 0, 0));
		southPanel.add(WindowMaker.createJButton(buttonStart, "Iniciar", "start", null, this, false));
		southPanel.add(WindowMaker.createJButton(buttonStop, "Pausar", "pause", null, this, false));
		southPanel.add(WindowMaker.createJButton(buttonStop, "Parar", "stop", null, this, false));
		southPanel.add(WindowMaker.createJButton(buttonExit, "Salir", "exit", null, this, false));
		return southPanel;
	}
	
	public void refreshUI() {
		window.setContentPane(createMainPanel());
		window.repaint();
		window.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("start")) {
			if (timer == null) {
				timer = new Timer(10, this);
				timer.setActionCommand("timerStart");
			}
			timer.start();
		} else if (e.getActionCommand().equals("pause")) {
			if (timer != null) timer.stop();
		} else if (e.getActionCommand().equals("stop")) {
			if (timer != null) {
				timer.stop();
				minutes = 0;
				seconds = 0;
				miliseconds = 0;
				timer = null;
				cronometerNumbers.setText("00:00:00");
			}			
		} else if (e.getActionCommand().equals("exit")) {
			window.dispose();
		} else if (e.getActionCommand().equals("timerStart")){
			miliseconds++;
			if (miliseconds == 60) {
				miliseconds = 0;
				seconds++;
			}
			if (seconds == 60) {
				seconds = 0;
				minutes++;
			}
			if (miliseconds <= 9) sMiliseconds = "0"+miliseconds;
			else sMiliseconds = ((Integer) miliseconds).toString();
			if (seconds <= 9) sSeconds = "0"+seconds;
			else sSeconds = ((Integer) seconds).toString();
			if (minutes <= 9) sMinutes = "0"+minutes;
			else sMinutes = ((Integer) minutes).toString();
			cronometerNumbers.setText(sMinutes+":"+sSeconds+":"+sMiliseconds);
		} else if (e.getActionCommand().equals("prueba")) {
			state = newHR.switchHRState(state);
			refreshUI();
		}		
	}
}
