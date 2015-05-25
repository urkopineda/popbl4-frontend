package model;

import java.util.Properties;

import database.SQLiteUtils;

public abstract class Dato implements Comparable<Dato> {
	private Properties pr;
	Integer id;
	
	public Dato(Properties pr) {
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
	public abstract void parseProperties(Properties pr);
	public abstract void searchID(SQLiteUtils conn); 
	public abstract String getTableColumns();
	public abstract String getColumnValues();
	@Override
	public abstract int compareTo(Dato d);
	
}
