package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.Configuration;
import model.TableData;
import statistics.StatisticFormulas;
import exception.LogInException;
import exception.RunnsteinDataBaseException;

/**
 * Se encarga de construir los Strings de Query, ejecutar y gestionar los ResultSet generados por Statements.
 * 
 * @author Runnstein Team
 */
public class StatementBasics {
	DataBaseBasics db = null;
	Connection con = null;
	
	/**
	 * Constructor de StatementConstructor, guarda la conexión generada por la clase SQL.
	 * 
	 * @param Connection con
	 */
	public StatementBasics(DataBaseBasics db) {
		this.db = db;
		this.con = db.getDataBaseConnection();
	}
	
	/**
	 * Devuelve el ResultSet generado al ejecutar un Statement.
	 * 
	 * @param String query
	 * @return ResultSet rs
	 * @throws RunnsteinDataBaseException
	 * @throws SQLException
	 */
	public ResultSet exeQuery(String query) throws SQLException, RunnsteinDataBaseException {
		Statement stmt = con.createStatement();
		System.out.print("Executing Query '"+query+"'...");
		ResultSet rs = stmt.executeQuery(query);
		System.out.println(" Executed!");
		return rs;
	}
	
	/**
	 * Ejecuta un String INSERT, UPDATE o DELETE en un Statement.
	 * 
	 * @param String query
	 * @throws RunnsteinDataBaseException
	 * @throws SQLException
	 */
	public void exeStmt(String query) throws SQLException, RunnsteinDataBaseException {
		Statement stmt = con.createStatement();
		System.out.print("Executing Statement '"+query+"'...");
		stmt.executeUpdate(query);
		System.out.println(" Executed!");
		stmt.close();
	}
	
	/**
	 * Genera un String para hacer un SELECT muy simple.
	 * 
	 * @param String [] columnNames
	 * @param String [] columnAlias
	 * @param String table
	 * @return String query
	 */
	public String stringSelect(String [] columnNames, String [] columnAlias, String table) {
		String query = "SELECT ";
		if ((columnAlias == null) && (columnNames != null)) {
			for (int i = 0; i < columnNames.length; i++) {
				if (i == (columnNames.length - 1)) {
					query += columnNames[i]+" ";
				} else query += columnNames[i]+", ";
			}
		} else if (columnNames.length == columnAlias.length) {
			for (int i = 0; i < columnNames.length; i++) {
				if (i == (columnNames.length - 1)) {
					query += columnNames[i]+" AS '"+columnAlias[i]+"' ";
				} else query += columnNames[i]+" AS '"+columnAlias[i]+"', ";
			}
		} else {
			query += "* ";
		}
		query += "FROM "+table;
		return query;
	}
	
	/**
	 * Genera un String para hacer un INSERT muy simple.
	 * 
	 * @param String [] columnNames
	 * @param String [] columnValues
	 * @param String table
	 * @return String query
	 */
	public String stringInsert(String [] columnNames, String [] columnValues, String table) {
		String query = "INSERT INTO "+table+"(";
		if (columnNames != null) {
			for (int i = 0; i < columnNames.length; i++) {
				if (i == (columnNames.length - 1)) {
					query += columnNames[i]+") ";
				} else query += columnNames[i]+", ";
			}
		}
		query += "VALUES (";
		if (columnNames.length == columnValues.length) {
			for (int i = 0; i < columnValues.length; i++) {
				if ((columnValues[i].matches("[0-9]+")) || ((columnValues[i].equals("true")) || (columnValues[i].equals("false")))) {
					query += columnValues[i];
				} else if (columnValues[i].matches("^\\d{4}-\\d{2}-\\d{2}.*$")) {
					int ano = Integer.parseInt(columnValues[i].substring(0, 4));
					int mes = Integer.parseInt(columnValues[i].substring(5, 7));
					int dia = Integer.parseInt(columnValues[i].substring(8, 10));
					int horas = Integer.parseInt(columnValues[i].substring(11, 13));
					int minutos = Integer.parseInt(columnValues[i].substring(14, 16));
					int segundos = Integer.parseInt(columnValues[i].substring(17, 19));
					String nuevaFecha = ano+"-"+mes+"-"+dia+"T"+horas+":"+minutos+":"+segundos+"Z";
					query += "STR_TO_DATE('"+nuevaFecha+"','%Y-%m-%dT%H:%i:%sZ')";
				} else {
					columnValues[i].replaceAll("'", "''");
					query += "'"+columnValues[i]+"'";
				}
				if (i == (columnNames.length - 1)) {
					query += ") ";
				} else query += ", ";
			}
		}
		return query;
	}
	
	/**
	 * Genera un String para hacer un DELETE muy simple.
	 * 
	 * @param String wColumnName
	 * @param String wColumnValue
	 * @param String table
	 * @return String query
	 */
	public String stringDelete(String wColumnName, String wColumnValue, String table) {
		String query = "DELETE FROM "+table+" WHERE ";
		if ((wColumnName != null) || (wColumnValue != null)) {
			query += wColumnName+" ";
			if (wColumnValue.contains("%")) {
				query += "LIKE ";
			} else query += "= ";
			if ((wColumnValue.matches("[0-9]+")) || ((wColumnValue.equals("true")) || (wColumnValue.equals("false")))) {
				query += wColumnValue;
			} else if (wColumnValue.matches("^\\d{4}-\\d{2}-\\d{2}.*$")) {
				int ano = Integer.parseInt(wColumnValue.substring(0, 4));
				int mes = Integer.parseInt(wColumnValue.substring(5, 7));
				int dia = Integer.parseInt(wColumnValue.substring(8, 10));
				int horas = Integer.parseInt(wColumnValue.substring(11, 13));
				int minutos = Integer.parseInt(wColumnValue.substring(14, 16));
				int segundos = Integer.parseInt(wColumnValue.substring(17, 19));
				String nuevaFecha = ano+"-"+mes+"-"+dia+"T"+horas+":"+minutos+":"+segundos+"Z";
				query += "STR_TO_DATE('"+nuevaFecha+"','%Y-%m-%dT%H:%i:%sZ')";
			} else {
				wColumnValue.replaceAll("'", "''");
				query += "'"+wColumnValue+"'";
			}
			return query;
		} else return null;
	}
	
	/**
	 * Genera un String para hacer un UPDATE muy simple.
	 * 
	 * @param String columnName
	 * @param String columnValue
	 * @param String wColumnName
	 * @param String wColumnValue
	 * @param String table
	 * @return String query
	 */
	public String stringUpdate(String columnName, String columnValue, String wColumnName, String wColumnValue, String table) {
		String query = "UPDATE "+table+" SET ";
		if ((columnName != null) || (columnValue != null)) {
			query += columnName+" = ";
			if ((columnValue.matches("[0-9]+")) || ((columnValue.equals("true")) || (columnValue.equals("false")))) {
				query += columnValue;
			} else if (columnValue.matches("^\\d{4}-\\d{2}-\\d{2}.*$")) {
				int ano = Integer.parseInt(columnValue.substring(0, 4));
				int mes = Integer.parseInt(columnValue.substring(5, 7));
				int dia = Integer.parseInt(columnValue.substring(8, 10));
				int horas = Integer.parseInt(columnValue.substring(11, 13));
				int minutos = Integer.parseInt(columnValue.substring(14, 16));
				int segundos = Integer.parseInt(columnValue.substring(17, 19));
				String nuevaFecha = ano+"-"+mes+"-"+dia+"T"+horas+":"+minutos+":"+segundos+"Z";
				query += "STR_TO_DATE('"+nuevaFecha+"','%Y-%m-%dT%H:%i:%sZ')";
			} else {
				columnValue.replaceAll("'", "''");
				query += "'"+columnValue+"'";
			}
		} else return null;
		query += " WHERE ";
		if ((wColumnName != null) && (wColumnValue != null)) {
			query += wColumnName+" ";
			if (wColumnValue.contains("%")) {
				query += "LIKE ";
			} else query += "= ";
			if ((wColumnValue.matches("[0-9]+")) || ((wColumnValue.equals("true")) || (wColumnValue.equals("false")))) {
				query += wColumnValue;
			} else if (wColumnValue.matches("^\\d{4}-\\d{2}-\\d{2}.*$")) {
				int ano = Integer.parseInt(wColumnValue.substring(0, 4));
				int mes = Integer.parseInt(wColumnValue.substring(5, 7));
				int dia = Integer.parseInt(wColumnValue.substring(8, 10));
				int horas = Integer.parseInt(wColumnValue.substring(11, 13));
				int minutos = Integer.parseInt(wColumnValue.substring(14, 16));
				int segundos = Integer.parseInt(wColumnValue.substring(17, 19));
				String nuevaFecha = ano+"-"+mes+"-"+dia+"T"+horas+":"+minutos+":"+segundos+"Z";
				query += "STR_TO_DATE('"+nuevaFecha+"','%Y-%m-%dT%H:%i:%sZ')";
			} else {
				wColumnValue.replaceAll("'", "''");
				query += "'"+wColumnValue+"'";
			}
			return query;
		} else return null;
	}
	
	/**
	 * Comprueba que el usuario y la contraseña existen en la base de datos.
	 */
	public void checkUser(JTextField userField, JPasswordField passField, JFrame window) throws LogInException {
		try {
			char[] input = passField.getPassword();
			String pass = new String(input);
			ResultSet rs = exeQuery("SELECT NombreUsuario, Password FROM USUARIO WHERE NombreUsuario = '"+userField.getText()+"' AND Password = '"+pass+"'");
			if (db.getNumberRows(rs) > 0) {
				ResultSet rsNameSurname = exeQuery("SELECT UsuarioID, Nombre, PrimerApellido, SegundoApellido FROM USUARIO WHERE NombreUsuario = '"+userField.getText()+"'");
				while (rsNameSurname.next()) {
					Configuration.userID = rsNameSurname.getInt(1);
					Configuration.name = rsNameSurname.getString(2);
					Configuration.surname1 = rsNameSurname.getString(3);
					Configuration.surname2 = rsNameSurname.getString(4);
				}
				db.closeDataBase();
			} else {
				db.closeDataBase();
				throw new LogInException(window);
			}
		} catch (SQLException e) {
			System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
		} finally {
			try {
				db.closeDataBase();
			} catch (SQLException e) {
				System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
			}
		}
	}
	
	/**
	 * Genera los datos que se utilizan en la JTable de la clase 'TrainingDataUI'.
	 */
	public ArrayList<TableData> generateTableData() {
		ArrayList<TableData> allData = new ArrayList<>();
		try {
			ResultSet rsEntrenamiento = exeQuery("SELECT * FROM ENTRENAMIENTO WHERE UsuarioID = "+Configuration.userID);
			if (db.getNumberRows(rsEntrenamiento) > 0) {
				int i = 0;
				while (rsEntrenamiento.next()) {
					int trainingNumber = i + 1;
					String dateTime = rsEntrenamiento.getString(3);
					String duration = rsEntrenamiento.getString(4);
					ResultSet rsIntervalo = exeQuery("SELECT * FROM INTERVALO WHERE EntrenamientoID = "+rsEntrenamiento.getInt(1));
					ArrayList<Integer> ppm = new ArrayList<>();
					while (rsIntervalo.next()) {
						ResultSet rsMuestra = exeQuery("SELECT * FROM MUESTRA WHERE IntervaloID = "+rsIntervalo.getInt(1));
						while (rsMuestra.next()) {
							ppm.add(rsMuestra.getInt(3));
						}
					}
					StatisticFormulas formulas = new StatisticFormulas(ppm);
					double rateMean = formulas.getMean();
					double rateMax = formulas.getMax();
					int stability = formulas.getStability();
					allData.add(new TableData(trainingNumber, dateTime, duration, rateMean, rateMax, stability));
				}
				return allData;
			} else return null;
		} catch (RunnsteinDataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
