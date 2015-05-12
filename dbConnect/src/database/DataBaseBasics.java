package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Esta clase que crea una nueva conexión a la base de datos.
 * 
 * @author Runnstein Team
 */
public class DataBaseBasics {
	Connection con = null;
	String url = null;
	int port = 0;
	String username = null;
	String password = null;
	String dbName = null;
	
	int tableNumber = -1;
	
	/**
	 * Constructor de SQL - Guarda todos los parámetros necesarios para la conexión en un nuevo objeto.
	 * 
	 * @param url
	 * @param port
	 * @param username
	 * @param password
	 * @param dbName
	 */
	public DataBaseBasics(String url, int port, String username, String password, String dbName) {
		this.url = url;
		this.port = port;
		this.username = username;
		this.password = password;
		this.dbName = dbName;
	}
	
	/**
	 * Devuelve el estado de la conexión a la base de datos.
	 * 
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean getDataBaseStatus() throws SQLException {
		if (con == null) {
			return false;
		} else if (con.isClosed()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Abre la base de datos con los datos obtenidos de las variables del contructor.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void openDataBase() throws SQLException, ClassNotFoundException {
		String generalURL = null;
		System.out.print("Connecting to MySQL database at '"+url+":"+port+"'...");
		Class.forName("com.mysql.jdbc.Driver");
        if (dbName == null) generalURL = "jdbc:mysql://"+url+":"+port;
        else generalURL = "jdbc:mysql://"+url+":"+port+"/"+dbName;
        con = DriverManager.getConnection(generalURL, username, password);
        System.out.println(" Connected!");
	}
	
	/**
	 * Cierra la base de datos. 
	 * 
	 * @throws SQLException
	 */
	public void closeDataBase() throws SQLException {
		if (con != null) {
			System.out.print("Disconnecting from MySQL...");
			con.close();
	        System.out.println(" Disconnected!");
		}
	}
	
	/**
	 * Selecciona una base de datos en la conexión.
	 * 
	 * @param dbName
	 * @throws SQLException
	 */
	public void useDataBase(String dbName) throws SQLException {
		if (con != null) {
			Statement stmt = con.createStatement();
			stmt.execute("USE "+dbName);
		}
	}
	
	/**
	 * Devuelve el número de columnas de una tabla.
	 * 
	 * @param tbName
	 * @return Número de columnas (int)
	 * @throws SQLException
	 */
	public int getNumberColumns(String tbName) throws SQLException {
		if (con != null) {
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM "+tbName);
			ResultSet rs = prepStmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			return rsmd.getColumnCount();
		} else return 0;
	}
	
	/**
	 * Devuelve un ArrayList con las columnas de una tabla ordenadas.
	 * 
	 * @param tbName
	 * @return ArrayList con columnas (String)
	 * @throws SQLException
	 */
	public ArrayList<String> getColumnsNames(String tbName) throws SQLException {
		if (con != null) {
			ArrayList<String> columnsName = new ArrayList<>();
			PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM "+tbName);
			ResultSet rs = prepStmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i < getNumberColumns(tbName); i++) {
				columnsName.add(rsmd.getColumnName(i));
			}
			return columnsName;
		} else return null;
	}
	
	/**
	 * Devuelve el número de datos obtenidos de un ResultSet.
	 * 
	 * @param rs
	 * @return Número de filas (int)
	 * @throws SQLException
	 */
	public int getNumberRows(ResultSet rs) throws SQLException {
		if (con != null) {
			rs.last();
			return rs.getRow();
		} else return -1;
	}
	
	/**
	 * Devuelve un array con los nombres de las tablas existentes en la base de datos.
	 * 
	 * @return ArrayList<String> tables
	 * @throws SQLException 
	 */
	public ArrayList<String> getTableNames() throws SQLException {
		if (con != null) {
			ArrayList<String> tables = new ArrayList<>();
			java.sql.DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, "%", null);
			while (rs.next()) {
				tables.add(rs.getString(3));
			}
			tableNumber = tables.size();
			return tables;
		} else return null;
	}
	
	/**
	 * Devuelve el número de tablas existentes.
	 * 
	 * @return int tableNumber
	 * @throws SQLException 
	 */
	public int getTableNumber() throws SQLException {
		if (tableNumber == -1) {
			@SuppressWarnings("unused")
			ArrayList<String> tablasTemp = getTableNames();
		}
		return tableNumber;
	}
	
	public Connection getDataBaseConnection() {
		return con;
	}
}
