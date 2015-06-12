package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import language.Strings;
import main.Configuration;
import playerModel.MiLoadScreen;
import utils.WindowMaker;
import database.MySQLUtils;
import file.FileUtils;

public class LogInUI implements ActionListener, ItemListener {
	JFrame window = null;
	JPanel mainPanel = null;
	JPanel loginPanel = null;
	JPanel northPanel = null;
	JPanel southPanel = null;
	JPanel centerPanel = null;
	JPanel logInPanel = null;
	JLabel titleImage = null;
	JLabel userText = null;
	JLabel passText = null;
	JLabel errorText = null;
	JLabel signUpText = null;
	JPasswordField passField = null;
	JTextField userField = null;
	JButton checkBtn = null;
	JButton cancelBtn = null;
	JButton signUpBtn = null;
	JButton settingsBtn = null;
	JComboBox<String> languageComboBox = null;
	boolean correctLogIn = false;
	MiLoadScreen load = null;
	boolean first = false;
	
	public LogInUI() {
		createJFrame();
	}
	
	private void createJFrame() {
		window = new JFrame(Strings.get("windowLogIn"));
		window.setSize(700, 500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2 - window.getSize().width/2, dim.height/2 - window.getSize().height/2);
		window.setContentPane(createMainPanel());
		window.setResizable(true);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		loginPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		loginPanel.add(createNorthPanel());
		loginPanel.add(createCenterPanel());
		mainPanel.add(loginPanel, BorderLayout.CENTER);
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}
	
	private Container createNorthPanel() {
		northPanel = new JPanel(new BorderLayout());
		titleImage = new JLabel(new ImageIcon(Configuration.dLogoMax));
		languageComboBox = WindowMaker.createJComboBox(null, this);
		languageComboBox.addItem("Euskera");
		languageComboBox.addItem("Castellano");
		languageComboBox.addItem("English");
		if (Configuration.lang == 0) languageComboBox.setSelectedItem("Euskera");
		else if (Configuration.lang == 1) languageComboBox.setSelectedItem("Castellano");
		else if (Configuration.lang == 2) languageComboBox.setSelectedItem("English");
		settingsBtn = WindowMaker.createJButton(Strings.get("settingsTitle"), "settings", this);
		JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tempPanel.add(languageComboBox);
		tempPanel.add(settingsBtn);
		northPanel.add(tempPanel, BorderLayout.NORTH);
		northPanel.add(titleImage, BorderLayout.CENTER);
		errorText = WindowMaker.createJLabel(errorText, Strings.get("logInError"), 20, "center");
		errorText.setForeground(Color.RED);
		errorText.setVisible(false);
		northPanel.add(errorText, BorderLayout.SOUTH);
		return northPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		logInPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		userText = WindowMaker.createJLabel(userText, Strings.get("logInUser"), 20, "right");
		passText = WindowMaker.createJLabel(passText, Strings.get("logInPass"), 20, "right");
		passField = WindowMaker.createJPasswordField();
		userField = WindowMaker.createJTextField();
		checkBtn = WindowMaker.createJButton(Strings.get("logInCheck"), "check", this);
		cancelBtn = WindowMaker.createJButton(Strings.get("logInCancel"), "cancel", this);
		logInPanel.add(userText);
		logInPanel.add(userField);
		logInPanel.add(passText);
		logInPanel.add(passField);
		logInPanel.add(checkBtn);
		logInPanel.add(cancelBtn);
		centerPanel.add(logInPanel, gbc);
		return centerPanel;
	}
	
	private Container createSouthPanel() {
		southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		signUpText = WindowMaker.createJLabel(signUpText, Strings.get("signUp"), 20, "right");
		southPanel.add(signUpText);
		signUpBtn = WindowMaker.createJButton(Strings.get("signUpBtn"), "signup", this);
		southPanel.add(signUpBtn);
		return southPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "check": checkBtn.setText(Strings.get("logInChecking"));
				checkUser();
				break;
			case "cancel": window.dispose();
				break;
			case "signup": @SuppressWarnings("unused")
			SignUpUI signUp = new SignUpUI(window);
				break;
			case "settings": @SuppressWarnings("unused")
				SettingsUI settings = new SettingsUI(window);
				break;
			default: window.dispose();
				break;
		}
	}
	
	private void checkUser() {
		load = new MiLoadScreen(window);
		char[] input = passField.getPassword();
		String pass = new String(input);
		MySQLUtils db = new MySQLUtils();
		try {
			db.openDataBase();
			load.setWorkToMake(15);
			ResultSet rs = db.exeQuery("SELECT Username, Password, UsuarioID, Nombre, PrimerApellido, SegundoApellido FROM USUARIO WHERE Username = '"+userField.getText()+"' AND Password = '"+pass+"'");
			while (rs.next()) {
				Configuration.username = rs.getString(1);
				load.progressHasBeenMade("Username", 1);
				Configuration.userID = rs.getInt(3);
				load.progressHasBeenMade("User ID", 1);
				Configuration.name = rs.getString(4);
				load.progressHasBeenMade("Name", 1);
				Configuration.surname1 = rs.getString(5);
				load.progressHasBeenMade("Surname 1", 1);
				Configuration.surname2 = rs.getString(6);
				load.progressHasBeenMade("Surname 2", 1);
				correctLogIn = true;
				@SuppressWarnings("unused")
				MainUI mainUI = new MainUI();
				window.dispose();
			}
			ResultSet rsT = db.exeQuery("SELECT Provincia, Pueblo, Calle, Numero, Piso, Letra  FROM DIRECCION WHERE UsuarioID = "+Configuration.userID);
			while(rsT.next()) {
				Configuration.provincia = rsT.getString(1);
				load.progressHasBeenMade("Province", 1);
				Configuration.pueblo = rsT.getString(2);
				load.progressHasBeenMade("City", 1);
				Configuration.calle = rsT.getString(3);
				load.progressHasBeenMade("Street", 1);
				Configuration.numero = rsT.getString(4);
				load.progressHasBeenMade("Number", 1);
				Configuration.piso = rsT.getString(5);
				load.progressHasBeenMade("Floor", 1);
				Configuration.letra = rsT.getString(6);
				load.progressHasBeenMade("Leter", 1);
			}
			ResultSet rsD = db.exeQuery("SELECT Numero FROM TELEFONO WHERE UsuarioID = "+Configuration.userID);
			while(rsD.next()) {
				Configuration.tlf = rsD.getString(1);
				load.progressHasBeenMade("Phone", 1);
			}
			ResultSet rsE = db.exeQuery("SELECT EntrenamientoID FROM Entrenamiento ORDER BY EntrenamientoID DESC LIMIT 1");
			while(rsE.next()) {
				Configuration.actualTraining = rsE.getInt(1);
				load.progressHasBeenMade("Entrenamiento", 1);
			}
			ResultSet rsI = db.exeQuery("SELECT IntervaloID FROM INTERVALO ORDER BY IntervaloID DESC LIMIT 1");
			while(rsI.next()) {
				Configuration.actualInterval = rsI.getInt(1);
				load.progressHasBeenMade("Intervalo", 1);
			}
			ResultSet rsM = db.exeQuery("SELECT MuestraID FROM MUESTRA ORDER BY MuestraID DESC LIMIT 1");
			while(rsM.next()) {
				Configuration.actualMuestra = rsM.getInt(1);
				load.progressHasBeenMade("Muestra", 1);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
		} finally {
			load.closeScreen();
			if (!correctLogIn) {
				checkBtn.setText(Strings.get("logInCheck"));
				errorText.setVisible(true);
			} else if (correctLogIn) {
				errorText.setVisible(false);
			}
			try {
				db.closeDataBase();
			} catch (SQLException e) {
				System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.DESELECTED) {
			Configuration.lang = languageComboBox.getSelectedIndex();
			FileUtils.writeConfFile();
			if (!first) first = true; 
			else JOptionPane.showMessageDialog(window, Strings.get("idiomaAlertM"), Strings.get("idiomaAlert"), JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
