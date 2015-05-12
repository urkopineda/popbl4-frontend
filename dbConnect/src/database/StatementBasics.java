package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Esta clase se encarga de construir los Strings de Query, ejecutar y gestionar los ResultSet generados por Statements.
 * 
 * @author Runnstein Team
 */
public class StatementBasics {
	Connection con;
	
	/**
	 * Constructor de StatementConstructor, guarda la conexión generada por la clase SQL.
	 * 
	 * @param con
	 */
	public StatementBasics(Connection con) {
		this.con = con;
	}
	
	/**
	 * Devuelve el ResultSet generado al ejecutar un Statement.
	 * 
	 * @param query
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet exeQuery(String query) throws SQLException {
		Statement stmt = con.createStatement();
		System.out.print("Executing Query '"+query+"'...");
		ResultSet rs = stmt.executeQuery(query);
		System.out.println(" Executed!");
		stmt.close();
		return rs;
	}
	
	/**
	 * Ejecuta un String INSERT, UPDATE o DELETE en un Statement.
	 * 
	 * @param query
	 * @throws SQLException
	 */
	public void exeStmt(String query) throws SQLException {
		Statement stmt = con.createStatement();
		System.out.print("Executing Statement '"+query+"'...");
		stmt.executeUpdate(query);
		System.out.println(" Executed!");
		stmt.close();
	}
	
	/**
	 * Genera un String para hacer un SELECT muy simple.
	 * 
	 * @param columnNames
	 * @param columnAlias
	 * @param table
	 * @return String de Query
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
	 * @param columnNames
	 * @param columnValues
	 * @param table
	 * @return String de Query
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
	 * @param wColumnName
	 * @param wColumnValue
	 * @param table
	 * @return String de Query
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
	 * @param columnName
	 * @param columnValue
	 * @param wColumnName
	 * @param wColumnValue
	 * @param table
	 * @return String de Query
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
}
