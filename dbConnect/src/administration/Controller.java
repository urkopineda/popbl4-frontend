package administration;

import graphicinterface.LogInUI;

/**
 * Clase controladora de todo el hilo de la aplicación.
 * 
 * @author Runnstein Team
 */
public class Controller {
	LogInUI ui = null;
	
	/**
	 * Inicio del sistema con la inicialización de los objetos ('DataBaseBasics', 'StatementBasics' y) 'UI'.
	 */
	public Controller() {
		ui = new LogInUI(this);
	}
}
