package model;

public class Muestra {
	int intervaloID = 0;
	int valor = 0;
	
	public Muestra(int intervaloID, int valor) {
		this.intervaloID = intervaloID;
		this.valor = valor;
	}
	
	private String getTables() {
		return "(IntervaloID, VALOR)";
	}
	
	private String getValues() {
		return "("+intervaloID+", "+intervaloID+")";
	}
	
	public String getInsert() {
		return "INSERT INTO MUESTRA"+getTables()+" VALUES"+getValues();
	}
}
