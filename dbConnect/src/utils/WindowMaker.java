package utils;

import java.awt.Font;
//github.com/Runnstein/RunnsteinFrontEnd.git
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class WindowMaker {
	public static JLabel createJLabel(JLabel newJLabel, String text, int size, String aligment) {
		newJLabel = new JLabel(text);
		Font font = new Font("Arial", 0, size);
		newJLabel.setFont(font);
		if (aligment.equals("left")) {
			newJLabel.setHorizontalAlignment(JLabel.LEFT);
		} else if (aligment.equals("right")) {
			newJLabel.setHorizontalAlignment(JLabel.RIGHT);
		} else if (aligment.equals("center")) {
			newJLabel.setHorizontalAlignment(JLabel.CENTER);
		}
		newJLabel.setVerticalAlignment(JLabel.CENTER);
		return newJLabel;
	}
	
	public static JTextField createJTextField() {
		return new JTextField();
	}
	
	public static JPasswordField createJPasswordField() {
		return new JPasswordField();
	}
	
	public static JButton createJButton(String text, String command, ActionListener action) {
		JButton newJButton = new JButton(text);
		newJButton.addActionListener(action);
		newJButton.setActionCommand(command);
		return newJButton;
	}
	
	public static JButton createJButton(ImageIcon icon, String command, ActionListener action) {
		JButton newJButton = new JButton(icon);
		newJButton.addActionListener(action);
		newJButton.setActionCommand(command);
		return newJButton;
	}
	
	public static JComboBox<String> createJComboBox(ArrayList<String> list, ItemListener item) {
		JComboBox<String> newJComboBox = new JComboBox<String>();
		if (list != null) for (int i = 0; i != list.size(); i++) newJComboBox.addItem(list.get(i));
		newJComboBox.addItemListener(item);
		return newJComboBox;
	}
	
	public static JMenuItem createJMenuItem(String text, ActionListener al, String actionCmd) {
		JMenuItem jMenuItem = new JMenuItem(text);
		jMenuItem.addActionListener(al);
		jMenuItem.setActionCommand(actionCmd);
		return jMenuItem;
	}
}
