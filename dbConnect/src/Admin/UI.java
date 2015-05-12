package Admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Esta clase se encarga de la interfaz gráfica del sistema.
 * 
 * @author Runnstein Team
 */
public class UI implements ActionListener, ListSelectionListener {
	Controller systemController;
	JFrame ventana = null;
	
	// PRUEBAS!
	String listaPrueba [] = {"A", "B", "C"};
	
	/**
	 * Contructor de la UI del sistema que inicializa la ventana y el panel principal.
	 * 
	 * @param newConnection
	 */
	public UI(Controller systemController) {
		this.systemController = systemController;
		ventana = new JFrame("Runnstein");
		ventana.setSize(800, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ventana.setLocation(dim.width/2-ventana.getSize().width/2, dim.height/2-ventana.getSize().height/2);
		ventana.setContentPane(crearPanelVentana());
		ventana.setVisible(true);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	JPanel panelPrincipal = null;
	JPanel panelNorte = null;
	JPanel panelCentro = null;
	JPanel panelSur = null;
	JPanel panelCB = null;
	JComboBox<String> cbTablas = null;
	
	/**
	 * Panel principal de la UI del sistema.
	 * 
	 * @return Panel principal
	 */
	private Container crearPanelVentana() {
		panelPrincipal = new JPanel(new BorderLayout());
		panelPrincipal.add(crearCBTablas(), BorderLayout.NORTH);
		return panelPrincipal;
	}
	
	/**
	 * Panel que contiene el combo box de tablas - Parte norte del panel principal.
	 * 
	 * @return Panel Norte
	 */
	private Container crearCBTablas() {
		panelCB = new JPanel(new FlowLayout());
		panelCB.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createTitledBorder("Tablas")));
		panelCB.add(comboBox(cbTablas, systemController.tableNames()));
		return panelCB;
	}
	
	/**
	 * Crear un Combo Box, recibe el Combo Box a crear y su lista (String [])
	 * 
	 * @param cb
	 * @param lista
	 * @return Combo Box
	 */
	private Component comboBox(JComboBox<String> cb, String [] lista) {
		cb = new JComboBox<String>(lista);
		return cb;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
