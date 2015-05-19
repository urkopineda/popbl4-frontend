package exception;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Excepcci�n que utilizamps para sustituir la 'SQLException'. Est� adaptada a nuestra UI general con un JOptionPane.
 * 
 * @author Runnstein Team
 */
@SuppressWarnings("serial")
public class RunnsteinDataBaseException extends SQLException {
	JOptionPane error = null;
	
	/**
	 * Contructor que recive el JFrame actual para poder lanzar, junto con la excepci�n, un JOptionPane de error.
	 * 
	 * @param JFrame window
	 */
	public RunnsteinDataBaseException(String errorCode, String errorMessage, JFrame window) {
		JOptionPane.showMessageDialog(window, errorCode+" - "+errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
