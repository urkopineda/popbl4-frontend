package exception;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Excepcci�n que se muestra al encotrar un error de logeo a la aplicaci�n.
 * 
 * @author Runnstein Team
 */
@SuppressWarnings("serial")
public class LogInException extends Exception {
	JOptionPane error = null;
	
	/**
	 * Contructor que recive el JFrame actual para poder lanzar, junto con la excepci�n, un JOptionPane de error.
	 * 
	 * @param JFrame window
	 */
	public LogInException(JFrame window) {
		JOptionPane.showMessageDialog(window, "Nombre de usuario y/o contrase�a incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
