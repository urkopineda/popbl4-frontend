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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import language.Strings;
import main.Configuration;
import utils.WindowMaker;
import database.MySQLUtils;
import file.FileUtils;

public class LogInUI implements ActionListener, ItemListener {
	JFrame window = null;
	JPanel mainPanel = null;
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JPanel logInPanel = null;
	JLabel titleImage = null;
	JLabel userText = null;
	JLabel passText = null;
	JLabel errorText = null;
	JPasswordField passField = null;
	JTextField userField = null;
	JButton checkBtn = null;
	JButton cancelBtn = null;
	JComboBox<String> languageComboBox = null;
	boolean correctLogIn = false;
	
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
		mainPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		mainPanel.add(createNorthPanel());
		mainPanel.add(createCenterPanel());
		return mainPanel;
	}
	
	private Container createNorthPanel() {
		northPanel = new JPanel(new BorderLayout());
		titleImage = new JLabel(new ImageIcon(Configuration.dLogoMax));
		languageComboBox = WindowMaker.createJComboBox(languageComboBox, null, this);
		languageComboBox.addItem("Euskera");
		languageComboBox.addItem("Castellano");
		languageComboBox.addItem("English");
		if (Configuration.lang == 0) languageComboBox.setSelectedItem("Euskera");
		else if (Configuration.lang == 1) languageComboBox.setSelectedItem("Castellano");
		else if (Configuration.lang == 2) languageComboBox.setSelectedItem("English");
		JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tempPanel.add(languageComboBox);
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
		passField = WindowMaker.createJPasswordField(passField);
		userField = WindowMaker.createJTextField(userField);
		checkBtn = WindowMaker.createJButton(checkBtn, Strings.get("logInCheck"), "check", this);
		cancelBtn = WindowMaker.createJButton(cancelBtn, Strings.get("logInCancel"), "cancel", this);
		logInPanel.add(userText);
		logInPanel.add(userField);
		logInPanel.add(passText);
		logInPanel.add(passField);
		logInPanel.add(checkBtn);
		logInPanel.add(cancelBtn);
		centerPanel.add(logInPanel, gbc);
		return centerPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "check": checkBtn.setText(Strings.get("logInChecking"));
				checkUser();
				break;
			case "cancel": window.dispose();
				break;
			default: window.dispose();
				break;
		}
	}
	
	private void checkUser() {
		char[] input = passField.getPassword();
		String pass = new String(input);
		MySQLUtils db = new MySQLUtils();
		try {
			db.openDataBase();
			ResultSet rs = db.exeQuery("SELECT Username, Password, UsuarioID, Nombre, PrimerApellido, SegundoApellido FROM USUARIO WHERE Username = '"+userField.getText()+"' AND Password = '"+pass+"'");
			while (rs.next()) {
				Configuration.username = rs.getString(1);
				Configuration.userID = rs.getInt(3);
				Configuration.name = rs.getString(4);
				Configuration.surname1 = rs.getString(5);
				Configuration.surname2 = rs.getString(6);
				correctLogIn = true;
				@SuppressWarnings("unused")
				MainUI mainUI = new MainUI();
				window.dispose();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
		} finally {
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
		}
	}
}
