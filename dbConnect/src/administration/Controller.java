package administration;

import graphicinterface.MainUI;

import java.sql.SQLException;
import java.util.ArrayList;

import database.DataBaseBasics;
import database.StatementBasics;

/**
 * Clase controladora de todo el hilo de la aplicación.
 * 
 * @author Runnstein Team
 */
public class Controller {
	MainUI ui = null;
	DataBaseBasics conDataBase = null;
	StatementBasics stmtController = null;
	
	/**
	 * Inicio del sistema con la inicialización de los objetos 'DataBaseBasics', 'StatementBasics' y 'UI'.
	 */
	public Controller(String url, int port, String username, String password, String dbName) {
		conDataBase = new DataBaseBasics(url, port, username, password, dbName);
		stmtController = new StatementBasics(conDataBase.getDataBaseConnection());
		ui = new MainUI(this);
	}
	
	/**
	 * Devuelve los nombres de las tablas en un array de String.
	 * 
	 * @return String []
	 */
	public String [] tableNames() {
		try {
			try {
				conDataBase.openDataBase();
			} catch (ClassNotFoundException e) {
				System.out.println("ERROR: Imposible cargar el driver de conexión a la base de datos MySQL.");
			}
			ArrayList<String> listaTablas = conDataBase.getTableNames();
			return listaTablas.toArray(new String [conDataBase.getTableNumber()]);
		} catch (SQLException e) {
			System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
		} finally {
			try {
				conDataBase.closeDataBase();
			} catch (SQLException e) {
				System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
			}
		} return null;
	}
}
