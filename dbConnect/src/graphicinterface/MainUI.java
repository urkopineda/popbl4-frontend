package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import administration.Controller;

/**
 * Esta clase se encarga de la interfaz gr�fica del sistema.
 * 
 * @author Runnstein Team
 */
public class MainUI implements ActionListener {
	Controller systemController = null;
	JFrame window = null;
	JMenuBar mainMenu = null;
	JMenu mainFile = null;
	JMenu mainEdit = null;
	JMenu mainExit = null;
	JMenuItem config = null;
	JMenuItem exit = null;
	JPanel mainPanel = null;
	JPanel centerPanel = null;	
	JButton buttonStart = null;
	JButton buttonMyTrainings = null;
	JButton buttonMyStats = null;
	JButton buttonSongLists = null;
	JButton buttonMyProfile = null;
	
	/**
	 * Contructor de la UI del sistema que inicializa la ventana y el panel principal.
	 * 
	 * @param newConnection
	 */
	public MainUI(Controller systemController) {
		this.systemController = systemController;
		window = new JFrame("Runnstein");
		window.setSize(360, 640);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2 - window.getSize().width/2, dim.height/2 - window.getSize().height/2);
		window.setContentPane(createMainPanel());
		window.setJMenuBar(createMenuBar());
		window.setResizable(false);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private JMenuBar createMenuBar(){
		mainMenu = new JMenuBar();
		mainMenu.add(createFileBar());
		mainMenu.add(createEditBar());
		mainMenu.add(Box.createHorizontalGlue());
		mainMenu.add(createExitBar());
		return mainMenu;
	}
	
	private JMenu createFileBar(){
		mainFile = new JMenu("Archivo");
		
		return mainFile;
	}
	
	private JMenu createEditBar(){
		mainEdit = new JMenu("Editar");
		WindowMaker.createItems(config, mainEdit, "config", "Preferencias", this);
		return mainEdit;
	}
	
	private JMenu createExitBar(){
		mainExit = new JMenu("Salir");
		WindowMaker.createItems(exit, mainExit, "exit", "Salir", this);
		return mainExit;
	}
	
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		return mainPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(5, 1, 0, 0));
		centerPanel.add(WindowMaker.createJButton(buttonStart, "INICIAR ENTRENAMIENTO", "startTraining", null, this, true));
		centerPanel.add(WindowMaker.createJButton(buttonMyTrainings, "MIS ENTRENAMIENTOS", "trainings", null, this, true));
		centerPanel.add(WindowMaker.createJButton(buttonMyStats, "MIS ESTAD�STICAS", "stats", null, this, true));
		centerPanel.add(WindowMaker.createJButton(buttonSongLists, "MIS PLAYLISTS", "playlist", null, this, true));
		centerPanel.add(WindowMaker.createJButton(buttonMyProfile, "MI PERFIL", "profile", null, this, true));
		return centerPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("exit")) {
			window.dispose();
		} else if (e.getActionCommand().equals("startTraining")) {
			@SuppressWarnings("unused")
			TrainingUI trainingUI = new TrainingUI(systemController, window);
		}
	}
}
