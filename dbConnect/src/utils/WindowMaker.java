package utils;

import java.awt.Font;
//github.com/Runnstein/RunnsteinFrontEnd.git
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
	
	public static JTextField createJTextField(JTextField newJTextField) {
		return newJTextField = new JTextField();
	}
	
	public static JPasswordField createJPasswordField(JPasswordField newJPasswordField) {
		return newJPasswordField = new JPasswordField();
	}
	
	public static JButton createJButton(JButton newJButton, String text, String command, ActionListener action) {
		newJButton = new JButton(text);
		newJButton.addActionListener(action);
		newJButton.setActionCommand(command);
		return newJButton;
	}
	
	public static JComboBox<String> createJComboBox(JComboBox<String> newJComboBox, ArrayList<String> list, ItemListener item) {
		newJComboBox = new JComboBox<String>();
		for (int i = 0; i != list.size(); i++) newJComboBox.addItem(list.get(i));
		newJComboBox.addItemListener(item);
		return newJComboBox;
	}
}
