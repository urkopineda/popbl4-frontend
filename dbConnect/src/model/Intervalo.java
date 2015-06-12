package model;

public class Intervalo {
	int entrenamientoID = 0;
	double bpmCancion = 0;
	String periodo = null;
	
	public Intervalo(double bpmCancion, String periodo) {
		this.bpmCancion = bpmCancion;
		this.periodo = periodo;
	}
	
	private String getTables() {
		return "(EntrenamientoID, BPMCancion, Periodo)";
	}
	
	private String getValues() {
		return "("+entrenamientoID+", "+bpmCancion+", '"+periodo+"')";
	}
	
	public String getInsert() {
		return "INSERT INTO INTERVALO"+getTables()+" VALUES"+getValues();
	}
}
