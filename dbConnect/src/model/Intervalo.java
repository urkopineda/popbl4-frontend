package model;

import main.Configuration;

public class Intervalo {
	int entrenamientoID = 0;
	int intervaloID = 0;
	double bpmCancion = 0;
	String periodo = null;
	
	public Intervalo(int intervaloID, double bpmCancion, String periodo) {
		entrenamientoID = Configuration.actualTraining;
		this.intervaloID = intervaloID;
		this.bpmCancion = bpmCancion;
		this.periodo = periodo;
	}
	
	private String getTables() {
		return "(IntervaloID, EntrenamientoID, BPMCancion, Periodo)";
	}
	
	private String getValues() {
		return "("+intervaloID+", "+entrenamientoID+", "+bpmCancion+", '"+periodo+"')";
	}
	
	public String getInsert() {
		return "INSERT INTO INTERVALO"+getTables()+" VALUES"+getValues();
	}
}
