package graphicinterface;

import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Esta clase es un constructor de elementos de Java Swing
 * 
 * @author Runnstein Team
 */
public class WindowMaker {
	/**
	 * Método para crear JButtons.
	 * 
	 * @param newJButton
	 * @param text
	 * @param icon
	 * @return newJButton
	 */
	public static JButton createJButton(JButton newJButton, String text, String command, Icon icon, ActionListener action, boolean modifiedDesign) {
		newJButton = new JButton(text);
		if (icon != null) newJButton.setIcon(icon);
		if (modifiedDesign) {
			newJButton.setOpaque(true);
			newJButton.setContentAreaFilled(false);
			newJButton.setBorderPainted(true);
		}
		newJButton.addActionListener(action);
		newJButton.setActionCommand(command);
		return newJButton;
	}
	
	/**
	 * Método para crear JLabels.
	 * 
	 * @param newJLabel
	 * @param text
	 * @param size
	 * @return newJLabel
	 */
	public static JLabel createJLabel(JLabel newJLabel, String text, int size) {
		newJLabel = new JLabel(text);
		newJLabel.setFont(new java.awt.Font("Arial", 0, size));
		newJLabel.setHorizontalAlignment(JLabel.CENTER);
		newJLabel.setVerticalAlignment(JLabel.CENTER);
		return newJLabel;
	}
	
	/**
	 * Método genérico que genera Items en submenus y le añade un 'listener'.
	 * 
	 * @param newItem
	 * @param menu
	 * @param actionCommand
	 * @param text
	 */
	public static void createItems(JMenuItem newItem, JMenu menu, String actionCommand, String text, ActionListener action) {
		newItem = menu.add(text);
		newItem.addActionListener(action);
		newItem.setActionCommand(actionCommand);
	}
}
