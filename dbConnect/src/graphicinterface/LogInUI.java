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

import utils.WindowMaker;
import administration.Controller;
import database.DataBaseBasics;
import database.StatementBasics;

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
	
	
	public LogInUI(Controller systemController, DataBaseBasics conDataBase, StatementBasics stmtController) {
		this.systemController = systemController;
		this.conDataBase = conDataBase;
		this.stmtController = stmtController;
		window = new JFrame();
		window.setSize(250, 150);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2 - window.getSize().width/2, dim.height/2 - window.getSize().height/2);
		window.setContentPane(createMainPanel());
		window.setResizable(false);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}
	
	private Container createNorthPanel() {
		northPanel = new JPanel();
		titleText = WindowMaker.createJLabel(titleText, "Runnstein", 25);
		northPanel.add(titleText);
		return northPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        centerPanel.add(crearPanelUserPass(), gbc);
		return centerPanel;
	}
	
	private Container createSouthPanel() {
		southPanel = new JPanel();
		buttonCheck = WindowMaker.createJButton(buttonCheck, "Comprobar", "check", null, this, false);
		buttonCancel = WindowMaker.createJButton(buttonCancel, "Cancelar", "cancel", null, this, false);
		southPanel.add(buttonCheck);
		southPanel.add(buttonCancel);
		return southPanel;
	}
	
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
	
	private boolean checkUser() {
		try {
			conDataBase.openDataBase();
			ResultSet rsUser = stmtController.exeQuery("SELECT NombreUsuario, Password FROM USUARIO WHERE NombreUsuario = '"+userField.getText()+"'");
			if (conDataBase.getNumberRows(rsUser) > 0) {
				char[] input = passField.getPassword();
				String pass = new String(input);
				ResultSet rsPass = stmtController.exeQuery("SELECT NombreUsuario, Password FROM USUARIO WHERE Password = '"+pass+"'");
				if (conDataBase.getNumberRows(rsPass) > 0) {
					return true;
				} else return false;
			} else return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("check")) {
			if (checkUser()) {
				window.dispose();
				@SuppressWarnings("unused")
				MainUI mainUI = new MainUI(systemController);
			}
		} else if (e.getActionCommand().equals("cancel")) {
			window.dispose();
		}
		
	}
}
