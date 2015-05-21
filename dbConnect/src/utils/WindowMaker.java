package utils;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Esta clase es un constructor de elementos de Java Swing
 * 
 * @author Runnstein Team
 */
public class WindowMaker {
	/**
	 * Método para crear JButtons.
	 * 
	 * @param JButton button
	 * @param String text
	 * @param String icon
	 * @return JButton newJButton
	 */
	public static JButton createJButton(JButton button, String text, String command, Icon icon, ActionListener action, boolean modifiedDesign) {
		button = new JButton(text);
		if (icon != null) button.setIcon(icon);
		if (modifiedDesign) {
			button.setOpaque(true);
			button.setContentAreaFilled(false);
			button.setBorderPainted(true);
		}
		button.addActionListener(action);
		button.setActionCommand(command);
		return button;
	}
	
	/**
	 * Método para crear JLabels.
	 * 
	 * @param JLabel label
	 * @param String text
	 * @param int size
	 * @return JLabel newJLabel
	 */
	public static JLabel createJLabel(JLabel label, String text, int size) {
		label = new JLabel(text);
		Font font = new Font("Arial", 0, size);
		if (text.equals("Runnstein")) {
			Font newFont = font.deriveFont(Font.BOLD);
			label.setFont(newFont);
		} else label.setFont(font);		
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		return label;
	}
	
	/**
	 * Método genérico que genera Items en submenus y le añade un 'listener'.
	 * 
	 * @param JMenuItem newItem
	 * @param JMenu menu
	 * @param String actionCommand
	 * @param String text
	 */
	public static void createItems(JMenuItem newItem, JMenu menu, String actionCommand, String text, ActionListener action) {
		newItem = menu.add(text);
		newItem.addActionListener(action);
		newItem.setActionCommand(actionCommand);
	}
	
	/**
	 * Método génerico que genera un JPasswordField.
	 * 
	 * @param JPasswordField passwordField
	 * @return JPasswordField newPasswordField
	 */
	public static JPasswordField createJPasswordField(JPasswordField passwordField) {
		passwordField = new JPasswordField();
		return passwordField;
	}
	
	/**
	 * Método genérico que genera un JTextField.
	 * 
	 * @param JTextField textField
	 * @return JTextField newTextField
	 */
	public static JTextField createJTextField(JTextField textField) {
		textField = new JTextField();
		return textField;
	}
	
	/**
	 * Método para crear circulos.
	 * 
	 * @param Graphics g
	 * @param int x
	 * @param int y
	 * @param int r
	 */
	public static void drawCenteredCircle(Graphics g, int x, int y, int r) {
		x = x - (r / 2);
		y = y - (r / 2);
		g.fillOval(x, y, r, r);
	}
}
