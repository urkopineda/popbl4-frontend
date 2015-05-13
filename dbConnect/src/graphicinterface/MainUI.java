package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import administration.Controller;

/**
 * Esta clase se encarga de la interfaz gráfica del sistema.
 * 
 * @author Runnstein Team
 */
public class MainUI implements ActionListener {
	Controller systemController = null;
	JFrame window = null;
	
	/**
	 * Contructor de la UI del sistema que inicializa la ventana y el panel principal.
	 * 
	 * @param newConnection
	 */
	public MainUI(Controller systemController) {
		this.systemController = systemController;
		window = new JFrame("Willkommen bis Runnstein");
		window.setSize(360, 640);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2 - window.getSize().width/2, dim.height/2 - window.getSize().height/2);
		window.setContentPane(createMainPanel());
		window.setResizable(false);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	JPanel mainPanel = null;
	
	/**
	 * Panel principal de la UI del sistema.
	 * 
	 * @return JPanel Main Panel
	 */
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		//mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		return mainPanel;
	}
	
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JPanel southPanel = null;
	
	JLabel labelBienvenida = null;
	
	/**
	 * Panel Norte de la UI del sistema.
	 * 
	 * @return JPanel North Panel
	 */
	private Container createNorthPanel() {
		northPanel = new JPanel(new BorderLayout());
		northPanel.add(createJLabel(labelBienvenida, "Wilkommen Gelehrtes!", 20), BorderLayout.CENTER);
		return northPanel;
	}
	
	String separation = "       ";
	JButton buttonStart = null;
	JButton buttonMyTrainings = null;
	JButton buttonMyStats = null;
	JButton buttonSongLists = null;
	JButton buttonMyProfile = null;
	JButton buttonOptions = null;
	JButton buttonExit = null;
	
	/**
	 * Panel Central de la UI del sistema.
	 * 
	 * @return JPanel Center Panel
	 */
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(7, 1, 0, 0));
		centerPanel.add(createJButton(buttonStart, separation+"INICIAR ENTRENAMIENTO", new ImageIcon("img/entrenamiento.png")));
		centerPanel.add(createJButton(buttonMyTrainings, separation+"MIS ENTRENAMIENTOS", new ImageIcon("img/misentrenamientos.png")));
		centerPanel.add(createJButton(buttonMyStats, separation+"MIS ESTADÍSTICAS", null));
		centerPanel.add(createJButton(buttonSongLists, separation+"MIS PLAYLISTS", null));
		centerPanel.add(createJButton(buttonMyProfile, separation+"MI PERFIL", null));
		centerPanel.add(createJButton(buttonOptions, separation+"OPCIONES", null));
		centerPanel.add(createJButton(buttonExit, separation+"SALIR", null));
		return centerPanel;
	}
	
	/**
	 * Método para crear JLabels.
	 * 
	 * @param newJLabel
	 * @param text
	 * @param size
	 * @return newJLabel
	 */
	private Component createJLabel(JLabel newJLabel, String text, int size) {
		newJLabel = new JLabel(text);
		newJLabel.setFont(new java.awt.Font("Arial", 0, size));
		newJLabel.setHorizontalAlignment(JLabel.CENTER);
		return newJLabel;
	}
	
	/**
	 * Método para crear JButtons.
	 * 
	 * @param newJButton
	 * @param text
	 * @param icon
	 * @return newJButton
	 */
	private Component createJButton(JButton newJButton, String text, Icon icon) {
		newJButton = new JButton(text, icon);
		if (icon != null) newJButton.setIcon(icon);
		newJButton.setOpaque(false);
		newJButton.setContentAreaFilled(false);
		newJButton.setBorderPainted(true);
		newJButton.addActionListener(this);
		newJButton.setActionCommand(text);
		return newJButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Salir")) {
			window.dispose();
		} else if (e.getActionCommand().equals("Iniciar entrenamiento")) {
			@SuppressWarnings("unused")
			TrainingUI trainingUI = new TrainingUI(systemController, window);
		}
	}
}
