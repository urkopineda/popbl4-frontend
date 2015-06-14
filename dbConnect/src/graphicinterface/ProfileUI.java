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
import playerModel.MiLoadScreen;
import utils.WindowMaker;
import database.MySQLUtils;

/**
 * Esta clase carga la tabla de Perfil del usuario.
 * 
 * @author Urko
 *
 */
public class ProfileUI {
	JPanel mainPanel = null;
	JPanel centerPanel = null;
	JPanel southPanel = null;
	JPanel profileNamePanel = null;
	JPanel profileUserPanel = null;
	JPanel optionsPanel = null;
	JPanel directPanel = null;
	JPanel phonePanel = null;
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
	JLabel phoneLabel = null;
	JTextField phoneField = null;
	JTextField provinField = null;
	JTextField puebloField = null;
	JTextField calleField = null;
	JTextField numField = null;
	JTextField pisoField = null;
	JTextField letraField = null;
	JLabel provinLabel = null;
	JLabel puebloLabel = null;
	JLabel calleLabel = null;
	JLabel numLabel = null;
	JLabel pisoLabel = null;
	JLabel letraLabel = null;
	MiLoadScreen load = null;
		
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
		centerPanel.add(createDirectionsPanel());
		centerPanel.add(createPhonePanel());
		return centerPanel;
	}
	
	private Container createProfileNamePanel() {
		profileNamePanel = new JPanel(new GridLayout(1, 1, 5, 5));
		profileNamePanel.setBorder(BorderFactory.createTitledBorder(Strings.get("sesion")));
		profileNamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		JPanel logInPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		userText = WindowMaker.createJLabel(userText, Strings.get("logInUser"), 20, "right");
		passText = WindowMaker.createJLabel(passText, Strings.get("logInPass"), 20, "right");
		passField = WindowMaker.createJPasswordField();
		userField = WindowMaker.createJTextField();
		logInPanel.add(userText);
		logInPanel.add(userField);
		logInPanel.add(passText);
		logInPanel.add(passField);
		profileNamePanel.add(logInPanel, gbc);
		return profileNamePanel;
	}
	
	private Container createProfileUserPanel() {
		profileUserPanel = new JPanel(new GridLayout(1, 1, 5, 5));
		profileUserPanel.setBorder(BorderFactory.createTitledBorder(Strings.get("persData")));
		profileUserPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		JPanel logInPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		nameText = WindowMaker.createJLabel(nameText, Strings.get("userName"), 20, "right");
		surname1Text = WindowMaker.createJLabel(surname1Text, Strings.get("userApe1"), 20, "right");
		surname2Text = WindowMaker.createJLabel(surname2Text, Strings.get("userApe2"), 20, "right");
		nameField = WindowMaker.createJTextField();
		surname1Field = WindowMaker.createJTextField();
		surname2Field = WindowMaker.createJTextField();
		logInPanel.add(nameText);
		logInPanel.add(nameField);
		logInPanel.add(surname1Text);
		logInPanel.add(surname1Field);
		logInPanel.add(surname2Text);
		logInPanel.add(surname2Field);
		profileUserPanel.add(logInPanel, gbc);
		return profileUserPanel;
	}
	
	private Container createDirectionsPanel() {
		directPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		directPanel.setBorder(BorderFactory.createTitledBorder(Strings.get("direccionB")));
		directPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		JPanel logInPanel = new JPanel(new GridLayout(6, 2, 5, 5));
		provinLabel = WindowMaker.createJLabel(provinLabel,	Strings.get("provin"), 20, "right");
		puebloLabel = WindowMaker.createJLabel(puebloLabel, Strings.get("pueblo"), 20, "right");
		calleLabel = WindowMaker.createJLabel(calleLabel, Strings.get("calle"), 20, "right");
		numLabel = WindowMaker.createJLabel(numLabel, Strings.get("num"), 20, "right");
		pisoLabel = WindowMaker.createJLabel(pisoLabel, Strings.get("piso"), 20, "right");
		letraLabel = WindowMaker.createJLabel(letraLabel, Strings.get("letra"), 20, "right");
		provinField = WindowMaker.createJTextField();
		puebloField = WindowMaker.createJTextField();
		calleField = WindowMaker.createJTextField();
		numField = WindowMaker.createJTextField();
		pisoField = WindowMaker.createJTextField();
		letraField = WindowMaker.createJTextField();
		logInPanel.add(provinLabel);
		logInPanel.add(provinField);
		logInPanel.add(puebloLabel);
		logInPanel.add(puebloField);
		logInPanel.add(calleLabel);
		logInPanel.add(calleField);
		logInPanel.add(numLabel);
		logInPanel.add(numField);
		logInPanel.add(pisoLabel);
		logInPanel.add(pisoField);
		logInPanel.add(letraLabel);
		logInPanel.add(letraField);
		directPanel.add(logInPanel, gbc);
		return directPanel;
	}
	
	private Container createPhonePanel() {
		phonePanel = new JPanel(new GridLayout(2, 2, 5, 5));
		phonePanel.setBorder(BorderFactory.createTitledBorder(Strings.get("phoneB")));
		phonePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		JPanel logInPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		phoneLabel = WindowMaker.createJLabel(phoneLabel, Strings.get("phone"), 20, "right");
		phoneField = WindowMaker.createJTextField();
		logInPanel.add(phoneLabel);
		logInPanel.add(phoneField);
		phonePanel.add(logInPanel, gbc);
		return phonePanel;
	}
	
	private Container createSouthPanel() {
		southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		saveBtn = WindowMaker.createJButton("Guardar", "save", act);
		cancelBtn = WindowMaker.createJButton("Cancelar", "cancel", act);
		southPanel.add(saveBtn);
		southPanel.add(cancelBtn);
		return southPanel;
	}
	
	/**
	 * Establecemos todos los valores a 0.
	 */
	public void cancelOption() {
		userField.setText("");
		passField.setText("");
		nameField.setText("");
		surname1Field.setText("");
		surname2Field.setText("");
		phoneField.setText("");
		provinField.setText("");
		puebloField.setText("");
		calleField.setText("");
		numField.setText("");
		pisoField.setText("");
		letraField.setText("");
	}
	
	/**
	 * Cargamos los campos con las variables del usuario.
	 */
	private void setDefaults() {
		userField.setText(Configuration.username);
		nameField.setText(Configuration.name);
		surname1Field.setText(Configuration.surname1);
		surname2Field.setText(Configuration.surname2);
		phoneField.setText(Configuration.tlf);
		provinField.setText(Configuration.provincia);
		puebloField.setText(Configuration.pueblo);
		calleField.setText(Configuration.calle);
		numField.setText(Configuration.numero);
		pisoField.setText(Configuration.piso);
		letraField.setText(Configuration.letra);
	}
	
	/**
	 * Actualizamos los datos de los campos.
	 */
	public void updateData() {
		replaceOldData();
	}
	
	/**
	 * Con este método cargamos todos los datos a la base de datos al darle a Guardar.
	 */
	private void replaceOldData() {
		char [] input = passField.getPassword();
		String pass = new String(input);
		MySQLUtils db = new MySQLUtils();
		try {
			String usuario = null;
			db.openDataBase();
			if ((userField.getText().equals("")) || (nameField.getText().equals("")) || (surname1Field.getText().equals(""))
					|| (surname2Field.getText().equals("")) || (phoneField.getText().equals("")) || (provinField.getText().equals(""))
					|| (puebloField.getText().equals("")) || (calleField.getText().equals("")) || (numField.getText().equals(""))
					|| (pisoField.getText().equals("")) || (letraField.getText().equals(""))) {
				JOptionPane.showMessageDialog(window, "Todos los campos deben estar llenos. El campo de la contraseña es opcional", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				load = new MiLoadScreen();
				load.setWorkToMake(3);
				if (pass.equals("")) {
					usuario = "UPDATE USUARIO SET Username='"+userField.getText()+"', Nombre='"+nameField.getText()+"', PrimerApellido='"+surname1Field.getText()+"', SegundoApellido='"+surname2Field.getText()+"'  WHERE UsuarioID = "+Configuration.userID;
				} else {
					usuario = "UPDATE USUARIO SET Username='"+userField.getText()+"', Password='"+pass+"', Nombre='"+nameField.getText()+"', PrimerApellido='"+surname1Field.getText()+"', SegundoApellido='"+surname2Field.getText()+"'  WHERE UsuarioID = "+Configuration.userID;
				}
				db.exeStmt(usuario);
				load.progressHasBeenMade("User data", 1);
				db.exeStmt("UPDATE DIRECCION SET Provincia='"+provinField.getText()+"', Pueblo='"+puebloField.getText()+"'"
						+ ", Calle='"+calleField.getText()+"', Numero='"+numField.getText()+"', Piso="+pisoField.getText()+""
								+ ", Letra='"+letraField.getText()+"' WHERE UsuarioID = "+Configuration.userID);
				load.progressHasBeenMade("Directions data", 1);
				db.exeStmt("UPDATE TELEFONO SET Numero='"+phoneField.getText()+"' WHERE UsuarioID = "+Configuration.userID);
				load.progressHasBeenMade("Phone data", 1);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
		} finally {
			if (load != null) load.closeScreen();
			try {
				db.closeDataBase();
			} catch (SQLException e) {
				System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
			}
		}
	}
}
