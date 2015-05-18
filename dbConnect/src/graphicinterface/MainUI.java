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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import main.Configuration;
import utils.WindowMaker;
import administration.Controller;

/**
 * Esta clase se encarga de la interfaz gráfica inicial del sistema.
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
	
	/**
	 * Crea una barra de menú con todos los menus. Pendiente el menu de archivo.
	 * 
	 * @return JMenuBar mainMenu
	 */
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
	
	/**
	 * Crea el panel principal con todos sus subpaneles.
	 * 
	 * @return JPanel mainPanel
	 */
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(WindowMaker.createJLabel(new JLabel(), "Bienvenido, "+Configuration.name+" "+Configuration.surname1+" "+Configuration.surname2, 15), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		return mainPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(5, 1, 0, 0));
		buttonStart = WindowMaker.createJButton(buttonStart, "INICIAR ENTRENAMIENTO", "startTraining", null, this, false);
		buttonMyTrainings = WindowMaker.createJButton(buttonMyTrainings, "MIS ENTRENAMIENTOS", "dataTraining", null, this, false);
		buttonMyStats = WindowMaker.createJButton(buttonMyStats, "MIS ESTADÍSTICAS", "stats", null, this, false);
		buttonSongLists = WindowMaker.createJButton(buttonSongLists, "MIS PLAYLISTS", "playlist", null, this, false);
		buttonMyProfile = WindowMaker.createJButton(buttonMyProfile, "MI PERFIL", "profile", null, this, false);
		centerPanel.add(buttonStart);
		centerPanel.add(buttonMyTrainings);
		centerPanel.add(buttonMyStats);
		centerPanel.add(buttonSongLists);
		centerPanel.add(buttonMyProfile);
		return centerPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("exit")) {
			window.dispose();
		} else if (e.getActionCommand().equals("startTraining")) {
			@SuppressWarnings("unused")
			TrainingUI trainingUI = new TrainingUI(systemController, this);
		} else if (e.getActionCommand().equals("dataTraining")) {
			@SuppressWarnings("unused")
			TrainingDataUI trainingDataUI = new TrainingDataUI(systemController, this);
		} else if (e.getActionCommand().equals("config")) {
			
		}
	}
}
