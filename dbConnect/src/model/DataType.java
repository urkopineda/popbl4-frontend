package model;

import java.util.Properties;

import database.SQLiteUtils;

/**
 * Clase abstracta que sirve como base a toda la informaci�n que se guarda en la base de datos SQLite. Facilita su manejo y el de su informaci�n.
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
	 * Funci�n que deber� extraer la informaci�n de las propiedades para el objeto que proceda.
	 * @param pr: Objeto Properties en el que deber� estar la informaci�n.
	 */
	public abstract void parseProperties(Properties pr);
	
	/**
	 * Funci�n para buscar la ID del DataType en la base de datos.
	 * @param conn: Conexi�n a SQLite.
	 */
	public abstract void searchID(SQLiteUtils conn);
	
	/**
	 * M�todo que devuelve un String con formato adecuado a query de las columnas que tiene el objeto en la base de datos.
	 * @return String con las columnas
	 */
	public abstract String getTableColumns();
	
	/**
	 * M�todo que devuelve un String con formato adecuado a query de los valores de cada columna.
	 * @return String con los valores de cada columna.
	 */
	public abstract String getColumnValues();
	
	@Override
	public abstract int compareTo(DataType d);
	
}
