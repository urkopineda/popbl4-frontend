package model;

import java.util.Properties;

import database.SQLiteUtils;

/**
 * Clase abstracta que sirve como base a toda la información que se guarda en la base de datos SQLite. Facilita su manejo y el de su información.
 * @author unaipme
 *
 */
public abstract class DataType implements Comparable<DataType> {
	private Properties pr;
	Integer id;
	
	public DataType(Properties pr) {
		this.pr = pr;
	}
	
	public Properties getProperties() {
		return this.pr;
	}
	
	public Object getProperty(String key) {
		return this.pr.getProperty(key);
	}
	
	public void setProperty(String key, String value) {
		this.pr.setProperty(key, value);
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public Integer getID() {
		return this.id;
	}
	
	@Override
	public abstract String toString();
	
	/**
	 * Función que deberá extraer la información de las propiedades para el objeto que proceda.
	 * @param pr: Objeto Properties en el que deberá estar la información.
	 */
	public abstract void parseProperties(Properties pr);
	
	/**
	 * Función para buscar la ID del DataType en la base de datos.
	 * @param conn: Conexión a SQLite.
	 */
	public abstract void searchID(SQLiteUtils conn);
	
	/**
	 * Método que devuelve un String con formato adecuado a query de las columnas que tiene el objeto en la base de datos.
	 * @return String con las columnas
	 */
	public abstract String getTableColumns();
	
	/**
	 * Método que devuelve un String con formato adecuado a query de los valores de cada columna.
	 * @return String con los valores de cada columna.
	 */
	public abstract String getColumnValues();
	
	@Override
	public abstract int compareTo(DataType d);
	
}
