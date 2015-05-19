package model;

/**
 * Clase que genera un objeto 'TableData' que se utilzará en el JTable de la clase 'TrainingDataUI'.
 * 
 * @author Runnstein Team
 */
public class TableData {
	int trainingNumber = 0;
	String dateTime = null;
	String duration = null;
	double rateMean = 0;
	double rateMax = 0;
	int stability = 0;

	/**
	 * Constructor que guarda todos los datos.
	 * 
	 * @param int trainingNumber
	 * @param String dateTime
	 * @param String duration
	 * @param double rateMean
	 * @param double rateMax
	 * @param int stability
	 */
	public TableData(int trainingNumber, String dateTime, String duration, double rateMean,	double rateMax,	int stability) {
		this.trainingNumber = trainingNumber;
		this.dateTime = dateTime;
		this.duration = duration;
		this.rateMean = rateMean;
		this.rateMax = rateMax;
		this.stability = stability;
	}
	
	public int getTrainingNumber() {
		return trainingNumber;
	}
	
	public String getDateTime() {
		return dateTime;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public double getRateMean() {
		return rateMean;
	}
	
	public double getRateMax() {
		return rateMax;
	}
	
	public int getStability() {
		return stability;
	}
	
	public String toString() {
		return "Entrenamiento Nº"+trainingNumber+" | Inicio "+dateTime+" | Duración "+duration+" | Media Pulsaciones "+rateMean+" | Máximo Pulsaciones "+rateMax+" | Estabilidad "+stability;
	}
}
