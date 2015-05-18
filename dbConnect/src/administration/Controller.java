package administration;

import graphicinterface.LogInUI;

/**
 * Clase controladora de todo el hilo de la aplicaci�n.
 * 
 * @author Runnstein Team
 */
public class Controller {
	LogInUI ui = null;
	
	/**
	 * Inicio del sistema con la inicializaci�n de los objetos ('DataBaseBasics', 'StatementBasics' y) 'UI'.
	 */
	public Controller() {
		ui = new LogInUI(this);
	}
}
