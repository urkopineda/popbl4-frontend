package model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Esta es una clase de un objetivo genérico que será "Dato".
 * 
 * @author Runnstein Team
 */
public class Data {
	ArrayList<String> data = null;
	ArrayList<String> columns = null;
	int rowNumber = -1;
	int columnNumber = -1;
	
	/**
	 * En este constructor interpretamos un ResultSet para convertirlo a un objeto de tipo 'Data'
	 * 
	 * @param rs
	 * @param rowNumber
	 * @param columnNumber
	 * @throws SQLException
	 */
	public Data(ResultSet rs, int rowNumber, int columnNumber) throws SQLException {
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
		data = new ArrayList<>();
		columns = new ArrayList<>();
		ResultSetMetaData rsmd = null;
		rsmd = rs.getMetaData();
		for (int i = 1; i <= columnNumber; i++) {
			columns.add(rsmd.getColumnName(i));
		}
		while (rs.next()) {
			for (int i = 1; i <= columnNumber; i++) {
				data.add(rs.getString(i));
			}
		}
	}
	
	/**
	 * Conseguir el número de filas de un objeto 'Data' generado con un 'ResultSet' particular.
	 * 
	 * @return int rowNumber
	 */
	public int getRowNumber() {
		return rowNumber;
	}
	
	/**
	 * Conseguir el número de columnas de un objeto 'Data' generado con un 'ResultSet' particular.
	 * 
	 * @return int columnNumber
	 */
	public int getColumnNumber() {
		return columnNumber;
	}
	
	/**
	 * Devuelve los nombres de las columnas de un objeto 'Data'.
	 * 
	 * @return ArrayList<String> columns
	 */
	public ArrayList<String> getColumnNames() {
		return columns;
	}
	
	/**
	 * Devuelve todos los datos de las columnas de un objeto 'Data'.
	 * 
	 * @return ArrayList<String> data
	 */
	public ArrayList<String> getData() {
		return data;
	}
	
	/**
	 * Devuelve un dato concreto.
	 * 
	 * @param index
	 * @return String
	 */
	public String getSpecificData(int index) {
		return data.get(index);
	}
	
	/**
	 * Añade un/unos datos nuevos a la lista.
	 * 
	 * @param newData
	 */
	public void addData(ArrayList<String> newData) {
		data.addAll(newData);
	}
	
	/**
	 * Borra un dato de la lista de datos.
	 * 
	 * @param index
	 */
	public void deleteData(int index) {
		data.remove(index);
	}
}
