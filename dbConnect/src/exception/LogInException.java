package exception;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Excepcción que se muestra al encotrar un error de logeo a la aplicación.
 * 
 * @author Runnstein Team
 */
@SuppressWarnings("serial")
public class LogInException extends Exception {
	JOptionPane error = null;
	
	/**
	 * Contructor que recive el JFrame actual para poder lanzar, junto con la excepción, un JOptionPane de error.
	 * 
	 * @param JFrame window
	 */
	public LogInException(JFrame window) {
		JOptionPane.showMessageDialog(window, "Nombre de usuario y/o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
