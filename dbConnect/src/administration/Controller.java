package administration;

import graphicinterface.MainUI;

/**
 * Clase controladora de todo el hilo de la aplicación.
 * 
 * @author Runnstein Team
 */
public class Controller {
	MainUI ui = null;
	// DataBaseBasics conDataBase = null;
	//StatementBasics stmtController = null;
	
	/**
	 * Inicio del sistema con la inicialización de los objetos 'DataBaseBasics', 'StatementBasics' y 'UI'.
	 */
	public Controller(String url, int port, String username, String password, String dbName) {
		// conDataBase = new DataBaseBasics(url, port, username, password, dbName);
		// stmtController = new StatementBasics(conDataBase.getDataBaseConnection());
		ui = new MainUI(this);
	}
}
