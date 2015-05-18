package administration;

import graphicinterface.LogInUI;
import database.DataBaseBasics;
import database.StatementBasics;

/**
 * Clase controladora de todo el hilo de la aplicaci�n.
 * 
 * @author Runnstein Team
 */
public class Controller {
	LogInUI ui = null;
	DataBaseBasics conDataBase = null;
	StatementBasics stmtController = null;
	
	/**
	 * Inicio del sistema con la inicializaci�n de los objetos 'DataBaseBasics', 'StatementBasics' y 'UI'.
	 */
	public Controller(String url, int port, String username, String password, String dbName) {
		conDataBase = new DataBaseBasics(url, port, username, password, dbName);
		stmtController = new StatementBasics(conDataBase.getDataBaseConnection());
		ui = new LogInUI(this, conDataBase, stmtController);
	}
}
