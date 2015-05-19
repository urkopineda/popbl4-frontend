package administration;

import graphicinterface.LogInUI;

/**
 * Controla todo el hilo de la aplicación, ejecuta los objetos principales como las diferentes UI del sistema y, si es necesario, las conexiones de la base de datos y funciones de gestión de la misma.
 * 
 * @author Runnstein Team
 */
public class Controller {
	LogInUI ui = null;
	
	/**
	 * Inicio del sistema con la inicialización de la interfaz gráfica.
	 */
	public Controller() {
		ui = new LogInUI(this);
	}
}
