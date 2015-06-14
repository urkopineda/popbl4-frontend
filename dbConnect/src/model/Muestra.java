package model;

/**
 * Objeto de la tabla de SQLite Muestra.
 * 
 * @author Urko
 *
 */
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
		return "("+muestraID+", "+intervaloID+", "+valor+")";
	}
	
	public String getInsert() {
		return "INSERT INTO MUESTRA"+getTables()+" VALUES"+getValues();
	}
}
