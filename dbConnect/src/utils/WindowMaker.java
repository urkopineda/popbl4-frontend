package utils;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
//github.com/Runnstein/RunnsteinFrontEnd.git
import java.awt.event.ActionListener;

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
}
