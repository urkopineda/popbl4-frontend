package model;

public class TableData {
	public int trainingNumber = 0;
	public String dateTime = null;
	public String duration = null;
	public double rateMean = 0;
	public double rateMax = 0;
	public int stability = 0;
	
	public TableData(int trainingNumber, String dateTime, String duration, double rateMean,	double rateMax,	int stability) {
		this.trainingNumber = trainingNumber;
		this.dateTime = dateTime;
		this.duration = duration;
		this.rateMean = rateMean;
		this.rateMax = rateMax;
		this.stability = stability;
	}
	
	public String toString() {
		return "Entrenamiento N�"+trainingNumber+" | Inicio "+dateTime+" | Duraci�n "+duration+" | Media Pulsaciones "+rateMean+" | M�ximo Pulsaciones "+rateMax+" | Estabilidad "+stability;
	}
}
