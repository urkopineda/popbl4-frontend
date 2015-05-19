package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.Configuration;
import utils.WindowMaker;
import administration.Controller;
import database.DataBaseBasics;
import database.StatementBasics;
import exception.LogInException;

/**
 * UI de log in - Comprueba si el usuario y contraseña metidos concuerdan.
 * 
 * @author Runnstein Team
 */
public class LogInUI implements ActionListener{
	Controller systemController = null;
	DataBaseBasics conDataBase = null;
	StatementBasics stmtController = null;
	JFrame window = null;
	JPanel mainPanel = null;
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JPanel southPanel = null;
	JPanel userPassPanel = null;
	JLabel titleText = null;
	JLabel userText = null;
	JLabel passText = null;
	JButton buttonCheck = null;
	JButton buttonCancel = null;
	JTextField userField = null;
	JPasswordField passField = null;
	StatementBasics stmtUtils = null;
	DataBaseBasics dbConnect = null;
	protected String user = null;
	protected String password = null;
	
	/**
	 * Contructor de 'LogInUI' que crea la ventana de log in.
	 * 
	 * @param Controller systemController
	 */
	public LogInUI(Controller systemController) {
		this.systemController = systemController;
		window = new JFrame();
		window.setSize(250, 150);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2 - window.getSize().width/2, dim.height/2 - window.getSize().height/2);
		window.setContentPane(createMainPanel());
		window.setResizable(false);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Crea el panel principal.
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
		titleText = WindowMaker.createJLabel(titleText, "Runnstein", 25);
		northPanel.add(titleText);
		return northPanel;
	}
	
	/**
	 * Crea el panel central.
	 * 
	 * @return JPanel centerPanel
	 */
	private Container createCenterPanel() {
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        centerPanel.add(crearPanelUserPass(), gbc);
		return centerPanel;
	}
	
	/**
	 * Crea el panel sur.
	 * 
	 * @return JPanel southPanel
	 */
	private Container createSouthPanel() {
		southPanel = new JPanel();
		buttonCheck = WindowMaker.createJButton(buttonCheck, "Comprobar", "check", null, this, false);
		buttonCancel = WindowMaker.createJButton(buttonCancel, "Cancelar", "cancel", null, this, false);
		southPanel.add(buttonCheck);
		southPanel.add(buttonCancel);
		return southPanel;
	}
	
	/**
	 * Crea el panel de los JTextField/JPasswordField y JLabel de usuario y contraseña. Se integra en el panel central.
	 * 
	 * @return JPanel userPassPanel
	 */
	public Container crearPanelUserPass(){
		userPassPanel = new JPanel(new GridLayout(2, 2, 10, 0));
		userField = WindowMaker.createJTextField(userField);
		passField = WindowMaker.createJPasswordField(passField);
		userText = WindowMaker.createJLabel(userText, "Usuario:", 15);
		passText = WindowMaker.createJLabel(passText, "Contraseña:", 15);
		userPassPanel.add(userText);
		userPassPanel.add(userField);
		userPassPanel.add(passText);
		userPassPanel.add(passField);
		return userPassPanel;
	}
	
	/**
	 * Comprueba que el usuario y la contraseña existen en la base de datos.
	 */
	private void checkUser() throws LogInException {
		try {
			conDataBase = new DataBaseBasics();
			conDataBase.openDataBase();
			stmtController = new StatementBasics(conDataBase.getDataBaseConnection());
			char[] input = passField.getPassword();
			String pass = new String(input);
			ResultSet rs = stmtController.exeQuery("SELECT NombreUsuario, Password FROM USUARIO WHERE NombreUsuario = '"+userField.getText()+"' AND Password = '"+pass+"'");
			if (conDataBase.getNumberRows(rs) > 0) {
				ResultSet rsNameSurname = stmtController.exeQuery("SELECT Nombre, PrimerApellido, SegundoApellido FROM USUARIO WHERE NombreUsuario = '"+userField.getText()+"'");
				while (rsNameSurname.next()) {
					Configuration.name = rsNameSurname.getString(1);
					Configuration.surname1 = rsNameSurname.getString(2);
					Configuration.surname2 = rsNameSurname.getString(3);
				}
				conDataBase.closeDataBase();
			} else {
				conDataBase.closeDataBase();
				throw new LogInException(window);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
		} finally {
			try {
				conDataBase.closeDataBase();
			} catch (SQLException e) {
				System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("check")) {
			try {
				checkUser();
				window.dispose();
				@SuppressWarnings("unused")
				MainUI mainUI = new MainUI(systemController);
			} catch (LogInException logE) {}
		} else if (e.getActionCommand().equals("cancel")) {
			window.dispose();
		}
		
	}
}
