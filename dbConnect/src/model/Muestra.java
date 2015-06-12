package model;

public class Muestra {
	int muestraID = 0;
	int intervaloID = 0;
	int valor = 0;
	
	public Muestra(int muestraID, int intervaloID, int valor) {
		this.muestraID = muestraID;
		this.intervaloID = intervaloID;
		this.valor = valor;
	}
	
	private String getTables() {
		return "(MuestraID, IntervaloID, VALOR)";
	}
	
	private String getValues() {
		return "("+muestraID+", "+intervaloID+", "+intervaloID+")";
	}
	
	public String getInsert() {
		return "INSERT INTO MUESTRA"+getTables()+" VALUES"+getValues();
	}
}
