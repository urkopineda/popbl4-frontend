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

public class SQLiteUtils {
	Connection conn;
	
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
	
	public ResultSet executeQuery(String sql) {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			//System.out.println("No se ha devuelto ningún resultado. Si no se ha hecho SELECT ignora la advertencia.");
			e.printStackTrace();
		}
		return rs;
	}
	
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
			System.out.println("No se ha podido cerrar la conexión.");
		}
	}
	
}
