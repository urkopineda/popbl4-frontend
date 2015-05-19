package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.Configuration;
import exception.RunnsteinDataBaseException;

/**
 * Crea una nueva conexión a la base de datos, además de conseguir información de la misma.
 * 
 * @author Runnstein Team
 */
public class DataBaseBasics {
	Connection con = null;	
	int tableNumber = -1;
	
	/**
	 * Devuelve el estado de la conexión a la base de datos.
	 * 
	 * @return boolean state
	 * @throws RunnsteinDataBaseException
	 * @throws SQLException
	 */
	public boolean getDataBaseStatus() throws RunnsteinDataBaseException, SQLException {
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
	 * @throws RunnsteinDataBaseException
	 * @throws ClassNotFoundException
	 */
	public void openDataBase() throws ClassNotFoundException, RunnsteinDataBaseException, SQLException {
		String generalURL = null;
		System.out.print("Connecting to MySQL database at '"+Configuration.dbUrl+":"+Configuration.port+"'...");
		Class.forName("com.mysql.jdbc.Driver");
        if (Configuration.dbName == null) generalURL = "jdbc:mysql://"+Configuration.dbUrl+":"+Configuration.port;
        else generalURL = "jdbc:mysql://"+Configuration.dbUrl+":"+Configuration.port+"/"+Configuration.dbName;
        con = DriverManager.getConnection(generalURL, Configuration.user, Configuration.password);
        System.out.println(" Connected!");
	}
	
	/**
	 * Cierra la base de datos. 
	 * 
	 * @throws RunnsteinDataBaseException
	 * @throws SQLException
	 */
	public void closeDataBase() throws RunnsteinDataBaseException, SQLException {
		if (con != null) {
			System.out.print("Disconnecting from MySQL...");
			con.close();
	        System.out.println(" Disconnected!");
		}
	}
	
	/**
	 * Selecciona una base de datos a usar dentro del servidor.
	 * 
	 * @param String dbName
	 * @throws RunnsteinDataBaseException
	 * @throws SQLException
	 */
	public void useDataBase(String dbName) throws RunnsteinDataBaseException, SQLException {
		if (con != null) {
			Statement stmt = con.createStatement();
			stmt.execute("USE "+dbName);
		}
	}
	
	/**
	 * Devuelve el número de columnas de una tabla.
	 * 
	 * @param String tbName
	 * @return int columnNumber
	 * @throws RunnsteinDataBaseException
	 * @throws SQLException
	 */
	public int getNumberColumns(String tbName) throws RunnsteinDataBaseException, SQLException {
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
	 * @param String tbName
	 * @return ArrayList<String> columnNames
	 * @throws RunnsteinDataBaseException
	 * @throws SQLException
	 */
	public ArrayList<String> getColumnsNames(String tbName) throws RunnsteinDataBaseException, SQLException {
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
	 * @param ResultSet rs
	 * @return int rowNumber
	 * @throws RunnsteinDataBaseException
	 * @throws SQLException
	 */
	public int getNumberRows(ResultSet rs) throws RunnsteinDataBaseException, SQLException {
		if (con != null) {
			rs.last();
			return rs.getRow();
		} else return -1;
	}
	
	/**
	 * Devuelve un array con los nombres de las tablas existentes en la base de datos.
	 * 
	 * @return ArrayList<String> tableNames
	 * @throws RunnsteinDataBaseException
	 * @throws SQLException 
	 */
	public ArrayList<String> getTableNamesArrayList() throws RunnsteinDataBaseException, SQLException {
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
	 * @throws RunnsteinDataBaseException
	 * @throws SQLException 
	 */
	public int getTableNumber() throws RunnsteinDataBaseException, SQLException {
		if (tableNumber == -1) {
			@SuppressWarnings("unused")
			ArrayList<String> tablasTemp = getTableNamesArrayList();
		}
		return tableNumber;
	}
	
	/**
	 * Devuelve los nombres de las tablas en un array de String.
	 * 
	 * @return String [] tableNames
	 * @throws SQLException
	 * @throws RunnsteinDataBaseException
	 * @throws ClassNotFoundException 
	 */
	public String [] getTableNamesArray() throws ClassNotFoundException, RunnsteinDataBaseException, SQLException {
		openDataBase();
		ArrayList<String> listaTablas = getTableNamesArrayList();
		String tableNames [] = listaTablas.toArray(new String [getTableNumber()]);
		closeDataBase();
		return tableNames;
	}
	
	/**
	 * Devuelve la conexión existente.
	 * 
	 * @return Connection con
	 */
	public Connection getDataBaseConnection() {
		return con;
	}
}
