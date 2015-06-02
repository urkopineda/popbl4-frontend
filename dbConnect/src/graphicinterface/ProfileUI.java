package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import language.Strings;
import main.Configuration;
import utils.WindowMaker;
import database.MySQLUtils;

public class ProfileUI {
	JPanel mainPanel = null;
	JPanel centerPanel = null;
	JPanel southPanel = null;
	JPanel profileNamePanel = null;
	JPanel profileUserPanel = null;
	JPanel optionsPanel = null;
	JLabel userText = null;
	JLabel passText = null;
	JTextField userField = null;
	JPasswordField passField = null;
	JLabel nameText = null;
	JLabel surname1Text = null;
	JLabel surname2Text = null;
	JTextField nameField = null;
	JTextField surname1Field = null;
	JTextField surname2Field = null;
	JButton saveBtn = null;
	JButton cancelBtn = null;
	ActionListener act = null;
	JFrame window = null;
		
	public ProfileUI(ActionListener act, JFrame window) {
		this.act = act;
		this.window = window;
	}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		return mainPanel;
	}
	
	public void addContent() {
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		setDefaults();
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		centerPanel.add(createProfileNamePanel());
		centerPanel.add(createProfileUserPanel());
		centerPanel.add(createOptionsPanel());
		centerPanel.add(createOptionsPanel());
		return centerPanel;
	}
	
	private Container createProfileNamePanel() {
		profileNamePanel = new JPanel(new GridLayout(1, 1, 5, 5));
		profileNamePanel.setBorder(BorderFactory.createTitledBorder("Incio de sesión"));
		profileNamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		JPanel logInPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		userText = WindowMaker.createJLabel(userText, Strings.get("logInUser"), 20, "right");
		passText = WindowMaker.createJLabel(passText, Strings.get("logInPass"), 20, "right");
		passField = WindowMaker.createJPasswordField(passField);
		userField = WindowMaker.createJTextField(userField);
		logInPanel.add(userText);
		logInPanel.add(userField);
		logInPanel.add(passText);
		logInPanel.add(passField);
		profileNamePanel.add(logInPanel, gbc);
		return profileNamePanel;
	}
	
	private Container createProfileUserPanel() {
		profileUserPanel = new JPanel(new GridLayout(1, 1, 5, 5));
		profileUserPanel.setBorder(BorderFactory.createTitledBorder("Datos Personales"));
		profileUserPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		JPanel logInPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		nameText = WindowMaker.createJLabel(nameText, "Nombre", 20, "right");
		surname1Text = WindowMaker.createJLabel(surname1Text, "1º Apellido", 20, "right");
		surname2Text = WindowMaker.createJLabel(surname2Text, "2º Apellido", 20, "right");
		nameField = WindowMaker.createJTextField(nameField);
		surname1Field = WindowMaker.createJTextField(surname1Field);
		surname2Field = WindowMaker.createJTextField(surname2Field);
		logInPanel.add(nameText);
		logInPanel.add(nameField);
		logInPanel.add(surname1Text);
		logInPanel.add(surname1Field);
		logInPanel.add(surname2Text);
		logInPanel.add(surname2Field);
		profileUserPanel.add(logInPanel, gbc);
		return profileUserPanel;
	}
	
	private Container createOptionsPanel() {
		optionsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		optionsPanel.setBorder(BorderFactory.createTitledBorder("Opciones"));
		return optionsPanel;
	}
	
	private Container createSouthPanel() {
		southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		saveBtn = WindowMaker.createJButton(saveBtn, "Guardar", "save", act);
		cancelBtn = WindowMaker.createJButton(cancelBtn, "Cancelar", "cancel", act);
		southPanel.add(saveBtn);
		southPanel.add(cancelBtn);
		return southPanel;
	}
	
	public void cancelOption() {
		userField.setText("");
		passField.setText("");
		nameField.setText("");
		surname1Field.setText("");
		surname2Field.setText("");
	}
	
	private void setDefaults() {
		userField.setText(Configuration.username);
		nameField.setText(Configuration.name);
		surname1Field.setText(Configuration.surname1);
		surname2Field.setText(Configuration.surname2);
	}
	
	public void updateData() {
		replaceOldData();
	}
	
	private void replaceOldData() {
		char [] input = passField.getPassword();
		String pass = new String(input);
		MySQLUtils db = new MySQLUtils();
		try {
			String connection = null;
			db.openDataBase();
			if ((userField.getText().equals("")) || (nameField.getText().equals("")) || (surname1Field.getText().equals("")) || (surname2Field.getText().equals(""))) {
				JOptionPane.showMessageDialog(window, "Todos los campos deben estar llenos. Ell campo de la contraseña es opcional", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				if (pass.equals("")) {
					connection = "UPDATE USUARIO SET Username='"+userField.getText()+"', Nombre='"+nameField.getText()+"', PrimerApellido='"+surname1Field.getText()+"', SegundoApellido='"+surname2Field.getText()+"'  WHERE UsuarioID = "+Configuration.userID;
				} else {
					connection = "UPDATE USUARIO SET Username='"+userField.getText()+"', Password='"+pass+"', Nombre='"+nameField.getText()+"', PrimerApellido='"+surname1Field.getText()+"', SegundoApellido='"+surname2Field.getText()+"'  WHERE UsuarioID = "+Configuration.userID;
				}
				db.exeStmt(connection);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
		} finally {
			try {
				db.closeDataBase();
			} catch (SQLException e) {
				System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
			}
		}
	}
}
