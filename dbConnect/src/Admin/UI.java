package Admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Esta clase se encarga de la interfaz gráfica del sistema.
 * 
 * @author Runnstein Team
 */
public class UI {
	JFrame ventana = null;
	JPanel panelPrincipal = null;
	JPanel panelCB = null;
	JComboBox<String> cbTablas = null;
	DataBaseBasics newConnection = null;
	
	String listaPrueba [] = {"A", "B", "C"};
	
	/**
	 * Contructor de la UI del sistema que inicializa la ventana y el panel principal.
	 * 
	 * @param newConnection
	 */
	public UI(DataBaseBasics newConnection) {
		this.newConnection = newConnection;
		ventana = new JFrame("Runnstein");
		ventana.setSize(800, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ventana.setLocation(dim.width/2-ventana.getSize().width/2, dim.height/2-ventana.getSize().height/2);
		ventana.setContentPane(crearPanelVentana());
		ventana.setVisible(true);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
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
		panelCB = new JPanel(new BorderLayout());
		panelCB.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createTitledBorder("Tablas")));
		try {
			try {
				newConnection.openDataBase();
			} catch (ClassNotFoundException e) {
				System.out.println("ERROR: Imposible cargar el driver de conexión a la base de datos MySQL.");
			}
			ArrayList<String> listaTablas = newConnection.getTableNames();
			panelCB.add(comboBox(cbTablas, listaTablas.toArray(new String [newConnection.getTableNumber()])), BorderLayout.CENTER);
		} catch (SQLException e) {
			System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
		} finally {
			try {
				newConnection.closeDataBase();
			} catch (SQLException e) {
				System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
			}
		}
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
}
