package Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Esta clase que crea una nueva conexi�n a la base de datos.
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
	
	/**
	 * Constructor de SQL - Guarda todos los par�metros necesarios para la conexi�n en un nuevo objeto.
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
	 * Devuelve el estado de la conexi�n a la base de datos.
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
	 * Selecciona una base de datos en la conexi�n.
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
	 * Devuelve el n�mero de columnas de una tabla.
	 * 
	 * @param tbName
	 * @return N�mero de columnas (int)
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
	 * Devuelve el n�mero de datos obtenidos de un ResultSet.
	 * 
	 * @param tbName
	 * @param rs
	 * @return N�mero de filas (int)
	 * @throws SQLException
	 */
	public int getNumberRows(String tbName, ResultSet rs) throws SQLException {
		if (con != null) {
			rs.last();
			return rs.getRow();
		} else return -1;
	}
}
