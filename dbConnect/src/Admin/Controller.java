package Admin;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase controladora de todo el hilo de la aplicación.
 * 
 * @author Runnstein Team
 */
public class Controller {
	UI newInterface = null;
	DataBaseBasics newConnection = null;
	StatementBasics newStmtController = null;
	
	/**
	 * Inicio del sistema con la inicialización de los objetos 'DataBaseBasics', 'StatementBasics' y 'UI'.
	 */
	public Controller(String url, int port, String username, String password, String dbName) {
		newConnection = new DataBaseBasics(url, port, username, password, dbName);
		newStmtController = new StatementBasics(newConnection.con);
		newInterface = new UI(this);
	}
	
	/**
	 * Devuelve los nombres de las tablas en un array de String.
	 * 
	 * @return String []
	 */
	public String [] tableNames() {
		try {
			try {
				newConnection.openDataBase();
			} catch (ClassNotFoundException e) {
				System.out.println("ERROR: Imposible cargar el driver de conexión a la base de datos MySQL.");
			}
			ArrayList<String> listaTablas = newConnection.getTableNames();
			return listaTablas.toArray(new String [newConnection.getTableNumber()]);
		} catch (SQLException e) {
			System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
		} finally {
			try {
				newConnection.closeDataBase();
			} catch (SQLException e) {
				System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
			}
		} return null;
	}
}
