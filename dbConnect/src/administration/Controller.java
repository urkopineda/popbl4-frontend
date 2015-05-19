package administration;

import graphicinterface.LogInUI;

/**
 * Controla todo el hilo de la aplicaci�n, ejecuta los objetos principales como las diferentes UI del sistema y, si es necesario, las conexiones de la base de datos y funciones de gesti�n de la misma.
 * 
 * @author Runnstein Team
 */
public class Controller {
	LogInUI ui = null;
	
	/**
	 * Inicio del sistema con la inicializaci�n de la interfaz gr�fica.
	 */
	public Controller() {
		ui = new LogInUI(this);
	}
}
