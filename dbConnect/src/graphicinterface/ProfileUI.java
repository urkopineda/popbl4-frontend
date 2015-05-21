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
import javax.swing.JPanel;

import utils.WindowMaker;
import administration.Controller;

/**
 * Esta clase se encarga de la interfaz gráfica de visualización/edición del perfil.
 * 
 * @author Runnstein Team
 */
public class ProfileUI implements ActionListener {
	JFrame window = null;
	Controller systemController = null;
	MainUI lastUI = null;
	JPanel mainPanel = null;
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JPanel southPanel = null;
	JButton buttonExit = null;
	
	/**
	 * Constructor de la UI de Training, oculta el panel anterior y crea uno nuevo.
	 * 
	 * @param Controller systemController
	 * @param MainUI lastUI
	 */
	public ProfileUI(Controller systemController, MainUI lastUI) {
		this.window = lastUI.window;
		this.systemController = systemController;
		window.setTitle("Mis Perfil");
		window.setSize(640, 640);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2 - window.getSize().width/2, dim.height/2 - window.getSize().height/2);
		window.setContentPane(createMainPanel());
		window.repaint();
		window.revalidate();
	}
	
	/**
	 * Crea el panel principal con todos sus subpaneles.
	 * 
	 * @return JPanel mainPanel
	 */
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}
	
	/**
	 * Crea el panel norte.
	 * 
	 * @return JPanel northPanel
	 */
	private Container createNorthPanel() {
		northPanel = new JPanel();
		
		return northPanel;
	}
	
	/**
	 * Crea el panel central.
	 * 
	 * @return JPanel centerPanel
	 */
	private Container createCenterPanel() {
		centerPanel = new JPanel();
		
		return centerPanel;
	}
	
	/**
	 * Crea el panel sur.
	 * 
	 * @return JPanel southPanel
	 */
	private Container createSouthPanel() {
		southPanel = new JPanel(new GridLayout(1, 1, 0, 0));
		buttonExit = WindowMaker.createJButton(buttonExit, "Salir", "exit", null, this, false);
		southPanel.add(buttonExit);
		return southPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
