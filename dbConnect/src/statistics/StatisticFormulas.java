package statistics;

import java.util.ArrayList;

/**
 * Formulas estadísticas que se usan en la creación de los gráficos de 'StatisticsUI' y de la tabla de 'TrainingDataUI'.
 * 
 * @author Runnstein Team
 */
public class StatisticFormulas {
	ArrayList<Integer> data = new ArrayList<>();
	
	/**
	 * Crea un ArrayList de Double mediante el ArrayList<String> de datos que se le pasa.
	 * 
	 * @param ArrayList<String> data
	 */
	public StatisticFormulas(ArrayList<Integer> data) {
		this.data = new ArrayList<>();
		for (int i = 0; i != data.size(); i++) this.data.add(data.get(i));
	}
	
	/**
	 * Devuelve la media de los datos.
	 * 
	 * @return double mean
	 */
	public double getMean() {
		double sum = 0.0;
        for (double a: data) sum += a;
        return sum / data.size();
	}
	
	/**
	 * Devuelve el valor máximo.
	 * 
	 * @return double max
	 */
	public double getMax() {
		double min = data.get(0);
		for (int i = 1; i != data.size(); i++) if (data.get(i) > min) min = data.get(i);
		return min;
	}
	
	/**
	 * Devuelve el valor mínimo.
	 * 
	 * @return double min
	 */
	public double getMin() {
		double min = data.get(0);
		for (int i = 1; i != data.size(); i++) if (data.get(i) < min) min = data.get(i);
		return min;
	}
	
	/**
	 * Devuelve la varianza.
	 * 
	 * @return double var
	 */
	public double getVar() {
		double mean = getMean();
        double temp = 0;
        for (double a: data) temp += (mean - a) * (mean - a);
        return temp / data.size();
	}
	
	/**
	 * Devuelve la desviación estandar.
	 * 
	 * @return double stdDev
	 */
	public double getStdDev() {
        return Math.sqrt(getVar());
    }
	
	/**
	 * Devuelve el nivel de estabilidad.
	 * 
	 * @return in stability
	 */
	public int getStability() {
		return 0;
	}
}
