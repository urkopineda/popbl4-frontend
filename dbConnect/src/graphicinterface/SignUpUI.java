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
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import language.Strings;
import utils.WindowMaker;
import database.MySQLUtils;

public class SignUpUI implements ActionListener {
	JFrame window = null;
	JDialog signInDialog = null;
	JPanel mainPanel = null;
	JPanel centerPanel = null;
	JPanel southPanel = null;
	JPanel signInPanel = null;
	JPanel userPanel = null;
	JLabel userText = null;
	JLabel passText = null;
	JLabel nameText = null;
	JLabel ape1Text = null;
	JLabel ape2Text = null;
	JPasswordField passField = null;
	JTextField userField = null;
	JTextField nameField = null;
	JTextField ape1Field = null;
	JTextField ape2Field = null;
	JButton cancelBtn = null;
	JButton signUpBtn = null;
	
	public SignUpUI(JFrame window) {
		this.window = window;
		createJDialog();
	}
	
	private void createJDialog() {
		signInDialog = new JDialog(window, Strings.get("windowSignIn"), true);
		signInDialog.setSize(500, 200);
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
		return mainPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(1, 3, 5, 5));
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		signInPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		userText = WindowMaker.createJLabel(userText, Strings.get("logInUser"), 20, "right");
		passText = WindowMaker.createJLabel(passText, Strings.get("logInPass"), 20, "right");
		passField = WindowMaker.createJPasswordField();
		userField = WindowMaker.createJTextField();
		signInPanel.add(userText);
		signInPanel.add(userField);
		signInPanel.add(passText);
		signInPanel.add(passField);
		centerPanel.add(signInPanel, gbc1);
		centerPanel.add(new JPanel());
		GridBagConstraints gbc2 = new GridBagConstraints();
		userPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		nameText = WindowMaker.createJLabel(nameText, Strings.get("userName"), 20, "right");
		ape1Text = WindowMaker.createJLabel(ape1Text, Strings.get("userApe1"), 20, "right");
		ape2Text = WindowMaker.createJLabel(ape2Text, Strings.get("userApe2"), 20, "right");
		nameField = WindowMaker.createJTextField();
		ape1Field = WindowMaker.createJTextField();
		ape2Field = WindowMaker.createJTextField();
		userPanel.add(nameText);
		userPanel.add(nameField);
		userPanel.add(ape1Text);
		userPanel.add(ape1Field);
		userPanel.add(ape2Text);
		userPanel.add(ape2Field);
		centerPanel.add(userPanel, gbc2);
		return centerPanel;
	}
	
	private Container createSouthPanel() {
		southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		cancelBtn = WindowMaker.createJButton(Strings.get("logInCancel"), "cancel", this);
		southPanel.add(cancelBtn);
		signUpBtn = WindowMaker.createJButton(Strings.get("signUpBtn"), "signup", this);
		southPanel.add(signUpBtn);
		return southPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "signup": createUser();
				break;
			case "cancel": signInDialog.dispose();
				break;
			default: signInDialog.dispose();
				break;
		}
	}
	
	private void createUser() {
		char[] input = passField.getPassword();
		String pass = new String(input);
		if ((pass.equals("")) || (nameField.getText().equals("")) || (ape1Field.getText().equals("")) || (ape2Field.getText().equals("")) || (userField.getText().equals(""))) {
			JOptionPane.showMessageDialog(window, Strings.get("errSignUp"), "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			MySQLUtils db = new MySQLUtils();
			try {
				db.openDataBase();
				db.exeStmt("INSERT INTO USUARIO(Username, Password, Nombre, PrimerApellido, SegundoApellido) VALUES('"+userField.getText()+"', '"+pass+"', '"+nameField.getText()+"', '"+ape1Field.getText()+"', '"+ape2Field.getText()+"')");
				JOptionPane.showMessageDialog(window, Strings.get("okSignUpM"), Strings.get("okSignUp"), JOptionPane.INFORMATION_MESSAGE);
				signInDialog.dispose();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(window, "ERROR: "+e.getSQLState()+" - "+e.getMessage()+".", "Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				try {
					db.closeDataBase();
				} catch (SQLException e) {
					System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
				}
			}
		}
	}
}
