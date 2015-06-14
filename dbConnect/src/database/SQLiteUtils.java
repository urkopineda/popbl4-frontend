package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;



/**
 * Clase que se encarga de la interacci�n entre la base de datos local de SQLite y la aplicaci�n.
 * @author unaipme
 *
 */
public class SQLiteUtils {
	Connection conn;
	
	
	/**
	 * Generar conexi�n a la base de datos SQLite
	 * @param db: Nombre de la base de datos
	 */
	public SQLiteUtils(String db) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+db+".db");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar JDBC.");
		} catch (SQLException e) {
			System.out.println("No se ha podido conectar a la base de datos.");
		}
	}
	
	public Statement createStatement() throws SQLException {
		return conn.createStatement();
	}
	
	public DatabaseMetaData getMetaData() throws SQLException {
		return conn.getMetaData();
	}
	
	/**
	 * Realiza una consulta SQL con resultado, generalmente utilizado con SELECTs.
	 * @param sql: Consulta SQL
	 * @return ResultSet con el resultado del query
	 */
	public ResultSet executeQuery(String sql) {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			//System.out.println("No se ha devuelto ning�n resultado. Si no se ha hecho SELECT ignora la advertencia.");
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * M�todo para hacer un INSERT en la base de datos SQLite
	 * @param table: Nombre de la tabla
	 * @param cols: String con las columnas. Debe estar entre par�ntesis, y los nombres de columnas separados por comas.
	 * @param values: String con los valores. Debe tambi�n seguir el formato de las columnas.
	 */
	public void insertQuery(String table, String cols, String values) {
		Statement st = null;
		try {
			st = conn.createStatement();
			String sql = "INSERT INTO "+table+" "+cols+" VALUES "+values;
			System.out.println(sql);
			st.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("No se ha podido crear el nuevo dato en la base de datos. Comprueba el query.");
		}
	}
	
	
	/**
	 * M�todo para hacer un INSERT en la base de datos SQLite
	 * @param sql: String sql del INSERT
	 */
	public void insertQuery(String sql) {
		Statement st = null;
		try {
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("No se ha podido crear el nuevo dato en la base de datos. Comprueba el query.");
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo para buscar la ID de un elemento DataType.
	 * @param table: Tabla en la que hay que buscar el dato
	 * @param pr: Objeto de clase Properties con toda la informaci�n que se puede usar para buscar la ID.
	 * @return Un n�mero entero, ID del DataType pasado
	 */
	public int getID(String table, Properties pr) {
		Statement st = null;
		int ret=0;
		ResultSet rs = null;
		String query = null;
		Enumeration<Object> keys = pr.keys();
		Iterator<Object> values = pr.values().iterator();
		try {
			st = conn.createStatement();
			query = "SELECT "+table+"ID FROM "+table+" WHERE";
			while (keys.hasMoreElements()&&values.hasNext()) {
				String k = keys.nextElement().toString();
				Object v = values.next();
				query+=" "+k+"=";
				if (v instanceof String) query+="'"+v+"'";
				else query+=v;
				if (values.hasNext()&&keys.hasMoreElements()) query+=" AND";
			}
			rs = st.executeQuery(query);
			while (rs.next()) ret = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("No se ha encontrado el dato");
		}
		return ret;
	}

	public int executeUpdate(String sql) throws SQLException {
		Statement st = conn.createStatement();
		return st.executeUpdate(sql);
	}
	
	public void deleteEntry(String table, int id) {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM "+table+" WHERE "+table+"ID="+id);
		} catch (SQLException e) {
			System.out.println("Ha habido un error al eliminar la entrada.");
		}
		
	}
	
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("No se ha podido cerrar la conexi�n.");
		}
	}
}
