package statistics;

import java.util.ArrayList;

public class StatisticsFormulas {
	ArrayList<Integer> data = new ArrayList<>();
	
	public StatisticsFormulas(ArrayList<Integer> data) {
		this.data = new ArrayList<>();
		for (int i = 0; i != data.size(); i++) this.data.add(data.get(i));
	}
	
	public double getMean() {
		double sum = 0.0;
        for (double a: data) sum += a;
        return sum / data.size();
	}

	public double getMax() {
		double min = data.get(0);
		for (int i = 1; i != data.size(); i++) if (data.get(i) > min) min = data.get(i);
		return min;
	}
	
	public double getMin() {
		double min = data.get(0);
		for (int i = 1; i != data.size(); i++) if (data.get(i) < min) min = data.get(i);
		return min;
	}
	
	public double getVar() {
		double mean = getMean();
        double temp = 0;
        for (double a: data) temp += (mean - a) * (mean - a);
        return temp / data.size();
	}

	public double getStdDev() {
        return Math.sqrt(getVar());
    }
	
	public int getStability() {
		return 0;
	}
}
