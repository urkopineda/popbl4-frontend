package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import main.Configuration;

public class Entrenamiento {
	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	int entrenamientoID = 0;
	int userID = 0;
	String fecha = null;
	public String duracion = null;
	
	public Entrenamiento() {
		entrenamientoID = Configuration.actualTraining;
		userID = Configuration.userID;
		this.fecha = sdf.format(new Date());
		this.duracion = "00:00:00";
	}
	
	public void updateDuration(String newDuration) {
		duracion = newDuration;
	}
	
	private String getTables() {
		return "(EntrenamientoID, UsuarioID, Fecha, Duracion)";
	}
	
	private String getValues() {
		return "("+entrenamientoID+", "+userID+", '"+fecha+"', '"+duracion+"')";
	}
	
	public String getInsert() {
		return "INSERT INTO ENTRENAMIENTO"+getTables()+" VALUES"+getValues();
	}
}
