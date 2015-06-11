package model;

import main.Configuration;

public class Entrenamiento {
	int userID = 0;
	String fecha = null;
	String duracion = null;
	
	public Entrenamiento(String fecha, String duracion) {
		userID = Configuration.userID;
		this.fecha = fecha;
		this.duracion = duracion;
	}
	
	private String getTables() {
		return "(UsuarioID, Fecha, Duracion)";
	}
	
	private String getValues() {
		return "("+userID+", '"+fecha+"', '"+duracion+"')";
	}
	
	public String getInsert() {
		return "INSERT INTO ENTRENAMIENTO"+getTables()+" VALUES"+getValues();
	}
}
