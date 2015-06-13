package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import file.FileUtils;
import language.Strings;
import main.Configuration;
import utils.WindowMaker;

/**
 * Esta clase genera el JPane de SettingsUI.
 * 
 * @author Urko
 *
 */
public class SettingsUI implements ActionListener {
	JFrame window = null;
	JDialog signInDialog = null;
	JPanel mainPanel = null;
	JPanel centerPanel = null;
	JPanel southPanel = null;
	JPanel signInPanel = null;
	JPanel userPanel = null;
	JLabel dbName = null;
	JLabel dbUrl = null;
	JLabel port = null;
	JLabel userDB = null;
	JLabel passDB = null;
	JPasswordField passDBField = null;
	JTextField dbNameField = null;
	JTextField dbUrlField = null;
	JTextField portField = null;
	JTextField userDBField = null;
	JButton cancelBtn = null;
	JButton saveBtn = null;
	
	public SettingsUI(JFrame window) {
		this.window = window;
		createJDialog();
	}
	
	/**
	 * Establece el valor de los campos de configuración existente.
	 */
	private void setFields() {
		dbNameField.setText(Configuration.dbName);
		dbUrlField.setText(Configuration.dbUrl);
		portField.setText(String.valueOf(Configuration.port));
		userDBField.setText(Configuration.user);
		passDBField.setText(Configuration.password);
	}
	
	/**
	 * Deprecated.
	 */
	@SuppressWarnings("unused")
	private void saveTemp() {
		char[] input = passDBField.getPassword();
		String pass = new String(input);
		Configuration.dbName = dbNameField.getText();
		Configuration.dbUrl = dbUrlField.getText();
		Configuration.port = Integer.parseInt(portField.getText());
		Configuration.user = userDBField.getText();
		Configuration.dbName = pass;
	}
	
	private void createJDialog() {
		signInDialog = new JDialog(window, Strings.get("settingsTitle"), true);
		signInDialog.setSize(700, 200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		signInDialog.setLocation(dim.width/2 - signInDialog.getSize().width/2, dim.height/2 - signInDialog.getSize().height/2);
		signInDialog.setContentPane(createMainPanel());
		signInDialog.setResizable(false);
		signInDialog.setVisible(true);
	}
	
	private Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		setFields();
		return mainPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(1, 3, 5, 5));
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		signInPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		userDB = WindowMaker.createJLabel(userDB, Strings.get("dbUser"), 20, "right");
		passDB = WindowMaker.createJLabel(passDB, Strings.get("dbPass"), 20, "right");
		passDBField = WindowMaker.createJPasswordField();
		userDBField = WindowMaker.createJTextField();
		signInPanel.add(userDB);
		signInPanel.add(userDBField);
		signInPanel.add(passDB);
		signInPanel.add(passDBField);
		centerPanel.add(signInPanel, gbc1);
		centerPanel.add(new JPanel());
		GridBagConstraints gbc2 = new GridBagConstraints();
		userPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		dbUrl = WindowMaker.createJLabel(dbUrl, Strings.get("dbUrl"), 20, "right");
		port = WindowMaker.createJLabel(port, Strings.get("dbPort"), 20, "right");
		dbName = WindowMaker.createJLabel(dbName, Strings.get("dbUser"), 20, "right");
		dbUrlField = WindowMaker.createJTextField();
		portField = WindowMaker.createJTextField();
		dbNameField = WindowMaker.createJTextField();
		userPanel.add(dbUrl);
		userPanel.add(dbUrlField);
		userPanel.add(port);
		userPanel.add(portField);
		userPanel.add(dbName);
		userPanel.add(dbNameField);
		centerPanel.add(userPanel, gbc2);
		return centerPanel;
	}
	
	private Container createSouthPanel() {
		southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		cancelBtn = WindowMaker.createJButton(Strings.get("logInCancel"), "cancel", this);
		southPanel.add(cancelBtn);
		saveBtn = WindowMaker.createJButton(Strings.get("saveSettings"), "save", this);
		southPanel.add(saveBtn);
		return southPanel;
	}

	/**
	 * Guarda o cancela la acción del usuario.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "save": FileUtils.writeConfFile();
				signInDialog.dispose();
				JOptionPane.showMessageDialog(window, Strings.get("idiomaAlertM"), Strings.get("idiomaAlert"), JOptionPane.INFORMATION_MESSAGE);
				break;
			case "cancel": signInDialog.dispose();
				break;
			default: signInDialog.dispose();
				break;
		}
	}
}
